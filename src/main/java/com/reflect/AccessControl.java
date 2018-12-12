package com.reflect;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

//访问控制权限
/**在通过查找已有类中的方法得到方法句柄时，要受限于Java语言中已有的访问控制权限。方法句柄与反射API在访问控制权限上的一个重要区别在于，在每次调用反射API的Method类的invoke方法的时候都需要检查访问控制权限，而方法句柄只在查找的时候需要进行检查。只要在查找过程中不出现问题，方法句柄在使用中就不会出现与访问控制权限相关的问题。这种实现方式也使方法句柄在调用时的性能要优于Method类。
 * @author dell
 *
 */
public class AccessControl {

	//之前介绍过，通过MethodHandles.Lookup类的方法可以查找类中已有的方法以得到MethodHandle对象。而MethodHandles.Lookup类的对象本身则是通过MethodHandles类的静态方法lookup得到的。在Lookup对象被创建的时候，会记录下当前所在的类（称为查找类）。只要查找类能够访问某个方法或域，就可以通过Lookup的方法来查找到对应的方法句柄。
	//代码清单2-62给出了一个访问控制权限相关的示例。AccessControl类中的accessControl方法返回了引用其中私有方法privateMethod的方法句柄。由于当前查找类可以访问该私有方法，因此查找过程是成功的。其他类通过调用accessControl得到的方法句柄就可以调用这个私有方法。虽然其他类不能直接访问AccessControl类中的私有方法，但是在调用方法句柄的时候不会进行访问控制权限检查，因此对方法句柄的调用可以成功进行。
	private void privateMethod() {
		System.out.println("PRIVATE");
	}

	public MethodHandle accessControl() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mh = lookup.findSpecial(AccessControl.class, "privateMethod", MethodType.methodType(void.class), AccessControl.class);
		mh = mh.bindTo(this);
		return mh;
	}

}
