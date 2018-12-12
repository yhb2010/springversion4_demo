package com.reflect;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.stream.IntStream;

//参数长度可变的方法句柄
/**在方法句柄中，所引用的底层方法中包含长度可变的参数是一种比较特殊的情况。虽然最后一个长度可变的参数实际上是一个数组，但是仍然可以简化方法调用时的语法。对于这种特殊的情况，方法句柄也提供了相关的处理能力，主要是一些转换的方法，允许在可变长度的参数和数组类型的参数之间互相转换，以方便开发人员根据需求选择最适合的调用语法。
MethodHandle中第一个与长度可变参数相关的方法是asVarargsCollector。它的作用是把原始的方法句柄中的最后一个数组类型的参数转换成对应类型的可变长度参数。
 * @author dell
 *
 */
public class MethodTypeArgsTest {

	public void normalMethod(String arg1, int arg2, int[] arg3) {
		System.out.print(arg1 + "," + arg2 + ",");
		IntStream.of(arg3).forEach(i -> System.out.print(i + ","));
		System.out.println();
	}

	public void toBeSpreaded(String arg1, int arg2, int arg3, int arg4) {
		System.out.println(arg1 + "," + arg2 + "," + arg3 + "," + arg4 + ",");
	}

	public void varargsMethod(String arg1, int... args) {
		System.out.print(arg1 + ",");
		IntStream.of(args).forEach(i -> System.out.print(i + ","));
		System.out.println();
	}

	//如代码清单2-37所示，方法normalMethod的最后一个参数是int类型的数组，引用它的方法句柄在通过asVarargsCollector方法转换之后，得到的新方法句柄在调用时就可以使用长度可变参数的语法格式，而不需要使用原始的数组形式。在实际的调用中，int类型的参数3、4和5组成的数组被传入到了normalMethod的参数args中。
	public <T> void asVarargsCollector(T t) throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mh = lookup.findVirtual(t.getClass(), "normalMethod", MethodType.methodType(void.class, String.class, int.class, int[].class));
		mh = mh.asVarargsCollector(int[].class);
		mh.invoke(this, "Hello", 2, 3, 4, 5);
	}

	//第二个方法asCollector的作用与asVarargsCollector类似，不同的是该方法只会把指定数量的参数收集到原始方法句柄所对应的底层方法的数组类型参数中，而不像asVarargsCollector那样可以收集任意数量的参数。
	//如代码清单2-38所示，还是以引用normalMethod的方法句柄为例，asCollector方法调用时的指定参数为2，即只有2个参数会被收集到整数类型数组中。在实际的调用中，int类型的参数3和4组成的数组被传入到了normalMethod的参数args中。
	public void asCollector() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mh = lookup.findVirtual(MethodTypeArgsTest.class, "normalMethod", MethodType.methodType(void.class, String.class, int.class, int[].class));
		mh = mh.asCollector(int[].class, 2);
		mh.invoke(this, "Hello", 2, 3, 4);
	}

	//上面的两个方法把数组类型参数转换为长度可变的参数，自然还有与之对应的执行反方向转换的方法。
	//代码清单2-39给出的asSpreader方法就把长度可变的参数转换成数组类型的参数。转换之后的新方法句柄在调用时使用数组作为参数，而数组中的元素会被按顺序分配给原始方法句柄中的各个参数。在实际的调用中，toBeSpreaded方法所接受到的参数arg2、arg3和arg4的值分别是3、4和5。
	public void asSpreader() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mh = lookup.findVirtual(MethodTypeArgsTest.class, "toBeSpreaded", MethodType.methodType(void.class, String.class, int.class, int.class, int.class));
		mh = mh.asSpreader(int[].class, 3);
		mh.invoke(this, "Hello", new int[]{3, 4, 5});
	}

	//最后一个方法asFixedArity是把参数长度可变的方法转换成参数长度不变的方法。经过这样的转换之后，最后一个长度可变的参数实际上就变成了对应的数组类型。在调用方法句柄的时候，就只能使用数组来进行参数传递。
	//如代码清单2-40所示，asFixedArity会把引用参数长度可变方法varargsMethod的原始方法句柄转换成固定长度参数的方法句柄。
	public void asFixedArity() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mh = lookup.findVirtual(MethodTypeArgsTest.class, "varargsMethod", MethodType.methodType(void.class, String.class, int[].class));
		mh = mh.asFixedArity();
		mh.invoke(this, "Hello", new int[]{2, 4});
	}

	public static void main(String[] args) throws Throwable {
		MethodTypeArgsTest t = new MethodTypeArgsTest();
		t.asVarargsCollector(t);
		t.asCollector();
		t.asSpreader();
		t.asFixedArity();
	}

}
