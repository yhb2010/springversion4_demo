package com.reflect;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

//使用方法句柄进行函数式编程
/**通过上面章节对方法句柄的详细介绍可以看出，方法句柄是一个非常灵活的对方法进行操作的轻量级结构。方法句柄的作用类似于在某些语言中出现的函数指针（function pointer）。在程序中，方法句柄可以在对象之间自由传递，不受访问控制权限的限制。方法句柄的这种特性，使得在Java语言中也可以进行函数式编程。下面通过几个具体的示例来进行说明。
 * @author dell
 *
 */
public class FunctionProgramming {

	public static void print(Object o, int index){
		System.out.println(o + ", " + index);
	}

	public static Object add(Object o, int index){
		return o.toString() + "aaa";
	}

	public static Object reduce(Object result, Object o){
		return Integer.parseInt(result.toString()) + Integer.parseInt(o.toString());
	}

	//第一个示例是对数组进行操作。数组作为一个常见的数据结构，有的编程语言提供了对它进行复杂操作的功能。这些功能中比较常见的是forEach、map和reduce操作等。这些操作的语义并不复杂，forEach是对数组中的每个元素都依次执行某个操作，而map则是把原始数组按照一定的转换过程变成一个新的数组，reduce是把一个数组按照某种规则变成单个元素。这些操作在其他语言中可能比较好实现，而在Java语言中，则需要引入一些接口，由此带来的是繁琐的实现和冗余的代码。有了方法句柄之后，这个实现就变得简单多了。
	//代码清单2-64给出了使用方法句柄的forEach、map和reduce方法的实现。对数组中元素的处理是由一个方法句柄来完成的。对这个方法句柄只有类型的要求，并不限制它所引用的底层方法所在的类或名称。
	private static final MethodType typeCallback = MethodType.methodType(void.class, Object.class, int.class);
	private static final MethodType typeCallback2 = MethodType.methodType(Object.class, Object.class, int.class);
	private static final MethodType typeCallback3 = MethodType.methodType(Object.class, Object.class, Object.class);

	public static void forEach(Object[] array, MethodHandle handle) throws Throwable {
		for (int i = 0, len = array.length; i < len; i++) {
			handle.invoke(array[i], i);
		}
	}

	public static Object[] map(Object[] array, MethodHandle handle) throws Throwable {
		Object[] result = new Object[array.length];
		for (int i = 0, len = array.length; i < len; i++) {
			result[i] = handle.invoke(array[i], i);
		}
		return result;
	}

	public static Object reduce(Object[] array, Object initalValue, MethodHandle handle) throws Throwable {
		Object result = initalValue;
		for (int i = 0, len = array.length; i < len; i++) {
			result = handle.invoke(result, array[i]);
		}
		return result;
	}

	//第二个例子是方法的柯里化（currying）。柯里化的含义是对一个方法的参数值进行预先设置之后，得到一个新的方法。比如一个做加法运算的方法，本来有两个参数，通过柯里化把其中一个参数的值设为5之后，得到的新方法就只有一个参数。新方法的运行结果是用5加上这个唯一的参数的值。通过MethodHandles类中的insertArguments方法可以很容易地实现方法句柄的柯里化。
	//代码清单2-65给出了相关的实现。方法curry负责把一个方法句柄的第一个参数的值设为指定值；add方法就是一般的加法操作；add5方法对引用add的方法句柄进行柯里化，得到新的方法句柄，再调用此方法句柄。
	public static MethodHandle curry(MethodHandle handle, int value) {
		return MethodHandles.insertArguments(handle, 0, value);
	}

	public static int add(int a, int b) {
		return a + b;
	}

	public static int add5(int a) throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodType type = MethodType.methodType(int.class, int.class, int.class);
		MethodHandle mhAdd = lookup.findStatic(FunctionProgramming.class, "add", type);
		MethodHandle mh = curry(mhAdd, 5);
		return (int) mh.invoke(a);
	}

	public static void main(String[] args) throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle handle = lookup.findStatic(FunctionProgramming.class, "print", typeCallback);
        forEach(new Object[]{1, 2, 4}, handle);

        MethodHandle handle2 = lookup.findStatic(FunctionProgramming.class, "add", typeCallback2);
        Object[] result = map(new Object[]{1, 2, 4}, handle2);
        forEach(result, handle);

        MethodHandle handle3 = lookup.findStatic(FunctionProgramming.class, "reduce", typeCallback3);
        System.out.println(reduce(new Object[]{1, 2, 4}, 0, handle3));

        System.out.println(FunctionProgramming.add5(8));
	}

}
