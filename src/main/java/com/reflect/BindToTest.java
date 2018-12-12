package com.reflect;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

//参数绑定
/**在前面介绍过，如果方法句柄在调用时引用的底层方法不是静态的，调用的第一个参数应该是该方法调用的接收者。这个参数的值一般在调用时指定，也可以事先进行绑定。通过MethodHandle的bindTo方法可以预先绑定底层方法的调用接收者，而在实际调用的时候，只需要传入实际参数即可，不需要再指定方法的接收者。
 * @author dell
 *
 */
public class BindToTest {

	//代码清单2-41给出了对引用String类的length方法的方法句柄的两种调用方式：第一种没有进行绑定，调用时需要传入length方法的接收者；第二种方法预先绑定了一个String类的对象，因此调用时不需要再指定。
	public static void bindTo() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mh = lookup.findVirtual(String.class, "length", MethodType.methodType(int.class));
		System.out.println((int) mh.invoke("Hello")); //值为5
		mh = mh.bindTo("Hello World");
		System.out.println((int) mh.invoke()); //值为11
	}

	//这种预先绑定参数的方式的灵活性在于它允许开发人员只公开某个方法，而不公开该方法所在的对象。开发人员只需要找到对应的方法句柄，并把适合的对象绑定到方法句柄上，客户代码就可以只获取到方法本身，而不会知道包含此方法的对象。绑定之后的方法句柄本身就可以在任何地方直接运行。
	//实际上，MethodHandle的bindTo方法只是绑定方法句柄的第一个参数而已，并不要求这个参数一定表示方法调用的接收者。对于一个MethodHandle，可以多次使用bindTo方法来为其中的多个参数绑定值。
	//代码清单2-42给出了多次绑定的一个示例。方法句柄所引用的底层方法是String类中的indexOf方法，同时为方法句柄的前两个参数分别绑定了具体的值。
	public static void multipleBindTo() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mh = lookup.findVirtual(String.class, "indexOf", MethodType.methodType(int.class, String.class, int.class));
		mh = mh.bindTo("HelloABC").bindTo("A");
		System.out.println(mh.invoke(2)); //值为5
	}

	//需要注意的是，在进行参数绑定的时候，只能对引用类型的参数进行绑定。无法为int和float这样的基本类型绑定值。对于包含基本类型参数的方法句柄，可以先使用wrap方法把方法类型中的基本类型转换成对应的包装类，再通过方法句柄的asType将其转换成新的句柄。转换之后的新句柄就可以通过bindTo来进行绑定，如代码清单2-43所示。
	public static void wrapBindTo() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mh = lookup.findVirtual(String.class, "substring", MethodType.methodType(String.class, int.class, int.class));
		mh = mh.asType(mh.type().wrap());
		mh = mh.bindTo("Hello World").bindTo(3);
		System.out.println(mh.invoke(5)); //值为“lo”
	}

	public static void main(String[] args) throws Throwable {
		BindToTest.bindTo();
		BindToTest.multipleBindTo();
		BindToTest.wrapBindTo();
	}

}
