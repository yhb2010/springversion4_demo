package com.reflect;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandleProxies;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

//使用方法句柄实现接口
/**2.3节介绍的动态代理机制可以在运行时为多个接口动态创建实现类，并拦截通过接口进行的方法调用。方法句柄也具备动态实现一个接口的能力。这是通过java.lang.invoke.MethodHandleProxies类中的静态方法asInterfaceInstance来实现的。不过通过方法句柄来实现接口所受的限制比较多。首先该接口必须是公开的，其次该接口只能包含一个名称唯一的方法。这样限制是因为只有一个方法句柄用来处理方法调用。调用asInterfaceInstance方法时需要两个参数，第一个参数是要实现的接口类，第二个参数是处理方法调用逻辑的方法句柄对象。方法的返回值是一个实现了该接口的对象。当调用接口的方法时，这个调用会被代理给方法句柄来完成。方法句柄的返回值作为接口调用的返回值。接口的方法类型与方法句柄的类型必须是兼容的，否则会出现异常。
 * @author dell
 *
 */
public class UseMethodHandleProxies {

	public void doSomething() {
		System.out.println("WORK");
	}

	/**代码清单2-61是使用方法句柄实现接口的示例。被代理的接口是java.lang.Runnable，其中仅包含一个run方法。实现接口的方法句柄引用的是当前类中的doSomething方法。在调用asInterfaceInstance之后得到的Runnable接口的实现对象被用来创建一个新的线程。该线程运行之后发现doSomething方法会被调用。这是由于当Runnable接口的run方法被调用的时候，方法句柄mh也会被调用。
	 * 通过方法句柄来实现接口的优势在于不需要新建额外的Java类，只需要复用已有的方法即可。在上面的示例中，任何已有的不带参数和返回值的方法都可以用来实现Runnable接口。需要注意的是，要求接口所包含的方法的名称唯一，不考虑Object类中的方法。实际的方法个数可能不止一个，可能包含同一方法的不同重载形式。
	 * @throws Throwable
	 */
	public void useMethodHandleProxy() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mh = lookup.findVirtual(UseMethodHandleProxies.class, "doSomething", MethodType.methodType(void.class));
		mh = mh.bindTo(this);
		Runnable runnable = MethodHandleProxies.asInterfaceInstance(Runnable.class, mh);
		new Thread(runnable).start();
	}

	public static void main(String[] args) throws Throwable {
		UseMethodHandleProxies test = new UseMethodHandleProxies();
		test.useMethodHandleProxy();
	}

}
