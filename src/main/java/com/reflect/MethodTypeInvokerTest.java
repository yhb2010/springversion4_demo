package com.reflect;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

//方法句柄的调用
public class MethodTypeInvokerTest {

	/**在获取到了一个方法句柄之后，最直接的使用方法就是调用它所引用的底层方法。在这点上，方法句柄的使用类似于反射API中的Method类。但是方法句柄在调用时所提供的灵活性是Method类中的invoke方法所不能比的。
	最直接的调用一个方法句柄的做法是通过invokeExact方法实现的。这个方法与直接调用底层方法是完全一样的。invokeExact方法的参数依次是作为方法接收者的对象和调用时候的实际参数列表。
	比如在代码清单2-36中，先获取String类中substring的方法句柄，再通过invokeExact来进行调用。这种调用方式就相当于直接调用"Hello World".substring(1, 3)。关于方法句柄的获取，下一节会具体介绍。
	 * @throws Throwable
	 */
	public static void invokeExact() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodType type = MethodType.methodType(String.class, int.class, int.class);
		//findVirtual就是查找一般函数的(同invokeVirtual)
		MethodHandle mh = lookup.findVirtual(String.class, "substring", type);
		//注意　invokeExact方法在调用的时候要求严格的类型匹配，方法的返回值类型也是在考虑范围之内的。代码清单2-36中的方法句柄所引用的substring方法的返回值类型是String，因此在使用invokeExact方法进行调用时，需要在前面加上强制类型转换，以声明返回值的类型。如果去掉这个类型转换，而直接赋值给一个Object类型的变量，在调用的时候会抛出异常，因为invokeExact会认为方法的返回值类型是Object。去掉类型转换但是不进行赋值操作也是错误的，因为invokeExact会认为方法的返回值类型是void，也不同于方法句柄要求的String类型的返回值。
		//与invokeExact所要求的类型精确匹配不同的是，invoke方法允许更加松散的调用方式。它会尝试在调用的时候进行返回值和参数类型的转换工作。这是通过MethodHandle类的asType方法来完成的。asType方法的作用是把当前的方法句柄适配到新的MethodType上，并产生一个新的方法句柄。当方法句柄在调用时的类型与其声明的类型完全一致的时候，调用invoke等同于调用invokeExact；否则，invoke会先调用asType方法来尝试适配到调用时的类型。如果适配成功，调用可以继续；否则会抛出相关的异常。这种灵活的适配机制，使invoke方法成为在绝大多数情况下都应该使用的方法句柄调用方式。
		//进行类型适配的基本规则是比对返回值类型和每个参数的类型是否都可以相互匹配。只要返回值类型或某个参数的类型无法完成匹配，那么整个适配过程就是失败的。从待转换的源类型S到目标类型T匹配成功的基本原则如下：
		//1）可以通过Java的类型转换来完成，一般是从子类转换成父类，接口的实现类转换成接口，比如从String类转换到Object类。
		//2）可以通过基本类型的转换来完成，只能进行类型范围的扩大，比如从int类型转换到long类型。
		//3）可以通过基本类型的自动装箱和拆箱机制来完成，比如从int类型到Integer类型。
		//4）如果S有返回值类型，而T的返回值是void，S的返回值会被丢弃。
		//5）如果S的返回值是void，而T的返回值是引用类型，T的返回值会是null。
		//6）如果S的返回值是void，而T的返回值是基本类型，T的返回值会是0。

		//最后一种调用方式是使用invokeWithArguments。该方法在调用时可以指定任意多个Object类型的参数。完整的调用方式是首先根据传入的实际参数的个数，通过MethodType的genericMethodType方法得到一个返回值和参数类型都是Object的新方法类型。再把原始的方法句柄通过asType转换后得到一个新的方法句柄。最后通过新方法句柄的invokeExact方法来完成调用。这个方法相对于invokeExact和invoke的优势在于，它可以通过Java反射API被正常获取和调用，而invokeExact和invoke不可以这样。它可以作为反射API和方法句柄之间的桥梁。
		String str = (String) mh.invokeExact("Hello World", 1, 3);
		System.out.println(str);
	}

	/**在这里强调一下静态方法和一般方法之间的区别。静态方法在调用时是不需要指定方法的接收对象的，而一般的方法则是需要的。如果方法句柄mh所引用的是java.lang.Math类中的静态方法min，那么直接通过mh.invokeExact(3, 4)就可以调用该方法。
	 * @throws Throwable
	 */
	public static void invokeExact2() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodType type = MethodType.methodType(int.class, int.class, int.class);
		//findStatic 就是查找静态方法的(同invokeStatic)
		MethodHandle mh = lookup.findStatic(Math.class, "min", type);
		int i = (int) mh.invokeExact(1, 3);
		System.out.println(i);
	}

	public static void main(String[] args) throws Throwable {
		MethodTypeInvokerTest.invokeExact();
		MethodTypeInvokerTest.invokeExact2();
	}

}
