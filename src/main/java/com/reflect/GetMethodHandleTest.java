package com.reflect;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.version4.chapter10.domain.Member;

//获取方法句柄
/**获取方法句柄最直接的做法是从一个类中已有的方法中转换而来，得到的方法句柄直接引用这个底层方法。在之前的示例中都是通过这种方式来获取方法句柄的。方法句柄可以按照与反射API类似的做法，从已有的类中根据一定的条件进行查找。与反射API不同的是，方法句柄并不区分构造方法、方法和域，而是统一转换成MethodHandle对象。对于域来说，获取到的是用来获取和设置该域的值的方法句柄。
方法句柄的查找是通过java.lang.invoke.MethodHandles.Lookup类来完成的。在查找之前，需要通过调用MethodHandles.lookup方法获取到一个MethodHandles.Lookup类的对象。MethodHandles.Lookup类提供了一些方法以根据不同的条件进行查找。
 *
 */
public class GetMethodHandleTest {

	private void privateMethod(){
		System.out.println("私有方法");
	}

	public String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**代码清单2-44以String类为例说明了查找构造方法和一般方法的示例。方法findConstructor用来查找类中的构造方法，需要指定返回值和参数类型，即MethodType对象。而findVirtual和findStatic则用来查找一般方法和静态方法，除了表示方法的返回值和参数类型的MethodType对象之外，还需要指定方法的名称。
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 */
	public static void lookupMethod() throws NoSuchMethodException, IllegalAccessException {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		//构造方法
		lookup.findConstructor(String.class, MethodType.methodType(void.class, byte[].class));
		//String.substring
		lookup.findVirtual(String.class, "substring", MethodType.methodType(String.class, int.class, int.class));
		//String.format
		lookup.findStatic(String.class, "format", MethodType.methodType(String.class, String.class, Object[].class));
	}

	//除了上面3种类型的方法之外，还有一个findSpecial方法用来查找类中的特殊方法，主要是类中的私有方法。
	//代码清单2-45给出了findSpecial的使用示例，Method-HandleLookup是lookupSpecial方法所在的类，而privateMethod是该类中的一个私有方法。由于访问的是类的私有方法，从访问控制的角度出发，进行方法查找的类需要具备访问私有方法的权限。
	//从下面的代码中可以看到，findSpecial方法比之前的findVirtual和findStatic等方法多了一个参数。这个额外的参数用来指定私有方法被调用时所使用的类。提供这个类的原因是为了满足对私有方法的访问控制的要求。当方法句柄被调用时，指定的调用类必须具备访问私有方法的权限，否则会出现无法访问的错误。
	public static void lookupSpecial() throws NoSuchMethodException, IllegalAccessException, Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		lookup.findSpecial(GetMethodHandleTest.class, "privateMethod", MethodType.methodType(void.class), GetMethodHandleTest.class);
	}

	//除了类中本来就存在的方法之外，对域的处理也是通过相应的获取和设置域的值的方法句柄来完成的。
	//代码清单2-46说明了如何查找到类中的静态域和一般域所对应的获取和设置的方法句柄。在查找的时候只需要提供域所在的类的Class对象、域的名称和类型即可。
	public static void lookupFieldAccessor() throws NoSuchFieldException, IllegalAccessException{
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		lookup.findGetter(Member.class, "userName", String.class);
		lookup.findSetter(Member.class, "userName", String.class);
		//lookup.findStaticGetter(Member.class, "telPhone", String.class);
		//lookup.findStaticSetter(Member.class, "telPhone", String.class);
	}

	//除了直接在某个类中进行查找之外，还可以从通过反射API得到的Constructor、Field和Method等对象中获得方法句柄。
	//如代码清单2-47所示，首先通过反射API得到表示构造方法的Constructor对象，再通过unreflectConstructor方法就可以得到其对应的一个方法句柄；而通过unreflect方法可以将Method类对象转换成方法句柄。对于私有方法，则需要使用unreflectSpecial来进行转换，同样也需要提供一个作用与findSpecial中参数相同的额外参数；对于Field类的对象来说，通过unreflectGetter和unreflectSetter就可以得到获取和设置其值的方法句柄。
	public void unreflect() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		Constructor constructor = String.class.getConstructor(byte[].class);
		MethodHandle handle = lookup.unreflectConstructor(constructor);
		String s = (String)handle.invokeExact("abc".getBytes());
		System.out.println(s);
		Method method = String.class.getMethod("substring", int.class, int.class);
		MethodHandle handle2 = lookup.unreflect(method);
		String str = (String)handle2.invokeExact("Hello World", 1, 3);
		System.out.println(str);

		Method privateMethod = GetMethodHandleTest.class.getDeclaredMethod("privateMethod");
		MethodHandle handle3 = lookup.unreflectSpecial(privateMethod, GetMethodHandleTest.class);
		handle3.bindTo(this).invoke();

		Field field = GetMethodHandleTest.class.getField("name");
		lookup.unreflectGetter(field);
		lookup.unreflectSetter(field);
	}

	//除了通过在Java类中进行查找来获取方法句柄外，还可以通过java.lang.invoke.MethodHandles中提供的一些静态工厂方法来创建一些通用的方法句柄。
	//第一个方法是用来对数组进行操作的，即得到可以用来获取和设置数组中元素的值的方法句柄。这些工厂方法的作用等价于2.2.4节介绍的反射API中的java.lang.reflect.Array类中的静态方法。
	//如代码清单2-48所示，MethodHandles的arrayElementGetter和arrayElementSetter方法分别用来得到获取和设置数组元素的值的方法句柄。调用这些方法句柄就可以对数组进行操作。
	public static void arrayHandles() throws Throwable {
		int[] array = new int[] {1, 2, 3, 4, 5};
		MethodHandle setter = MethodHandles.arrayElementSetter(int[].class);
		setter.invoke(array, 3, 6);
		MethodHandle getter = MethodHandles.arrayElementGetter(int[].class);
		int value = (int) getter.invoke(array, 3); //值为6
		System.out.println(value);
	}

	//MethodHandles中的静态方法identity的作用是通过它所生成的方法句柄，在每次调用的时候，总是返回其输入参数的值。
	//如代码清单2-49所示，在使用identity方法的时候只需要传入方法句柄的唯一参数的类型即可，该方法句柄的返回值类型和参数类型是相同的。
	public void identity() throws Throwable {
		MethodHandle mh = MethodHandles.identity(String.class);
		String value = (String) mh.invoke("Hello"); //值为"Hello"
	}

	//而方法constant的作用则更加简单，在生成的时候指定一个常量值，以后这个方法句柄被调用的时候，总是返回这个常量值，在调用时也不需要提供任何参数。这个方法提供了一种把一个常量值转换成方法句柄的方式，
	//如下面的代码所示。在调用constant方法的时候，只需要提供常量的类型和值即可。
	//MethodHandles类中的identity方法和constant方法的作用类似于在开发中用到的“空对象（Null object）”模式的应用。在使用方法句柄的某些场合中，如果没有合适的方法句柄对象，可能不允许直接用null来替换，这个时候可以通过这两个方法来生成简单无害的方法句柄对象作为替代。
	public void constant() throws Throwable {
		MethodHandle mh = MethodHandles.constant(String.class, "Hello");
		String value = (String) mh.invoke(); //值为"Hello"
	}


	public static void main(String[] args) throws Throwable {
		GetMethodHandleTest t = new GetMethodHandleTest();
		GetMethodHandleTest.lookupMethod();
		GetMethodHandleTest.lookupSpecial();
		GetMethodHandleTest.lookupFieldAccessor();
		t.unreflect();

		GetMethodHandleTest.arrayHandles();
	}

}
