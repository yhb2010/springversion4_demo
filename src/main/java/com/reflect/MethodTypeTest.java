package com.reflect;

import java.lang.invoke.MethodType;

/**
 * 对于一个方法句柄来说，它的类型完全由它的参数类型和返回值类型来确定，而与它所引用的底层方法的名称和所在的类没有关系。比如引用String类的length方法和Integer类的intValue方法的方法句柄的类型就是一样的，因为这两个方法都没有参数，而且返回值类型都是int。
在得到一个方法句柄，即MethodHandle类的对象之后，可以通过其type方法来查看其类型。该方法的返回值是一个java.lang.invoke.MethodType类的对象。MethodType类的所有对象实例都是不可变的，类似于String类。所有对MethodType类对象的修改，都会产生一个新的MethodType类对象。两个MethodType类对象是否相等，只取决于它们所包含的参数类型和返回值类型是否完全一致。
MethodType类的对象实例只能通过MethodType类中的静态工厂方法来创建。这样的工厂方法有三类。第一类是通过指定参数和返回值的类型来创建MethodType，这主要是使用methodType方法的多种重载形式。使用这些方法的时候，至少需要指定返回值类型，而参数类型则可以是0到多个。返回值类型总是出现在methodType方法参数列表的第一个，后面紧接着的是0到多个参数的类型。类型都是由Class类的对象来指定的。如果返回值类型是void，可以用void.class或java.lang.Void.class来声明。
 * @author dell
 *
 */
public class MethodTypeTest {

	//代码清单2-31中给出了使用methodType方法的几个示例。每个MethodType声明上以注释的方式给出了与之相匹配的String类中的一个方法。这里值得一提的是，最后一个methodType方法调用中使用了另外一个MethodType的参数类型作为当前MethodType类对象的参数类型。
	public void generateMethodTypes() {
		//String.length()
		MethodType mt1 = MethodType.methodType(int.class);
		//String.concat(String str)
		MethodType mt2 = MethodType.methodType(String.class, String.class);
		//String.getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin)
		MethodType mt3 = MethodType.methodType(void.class, int.class, int.class, char[].class, int.class);
		//String.startsWith(String prefix)
		MethodType mt4 = MethodType.methodType(boolean.class, mt2);
	}

	//除了显式地指定返回值和参数的类型之外，还可以生成通用的MethodType类型，即返回值和所有参数的类型都是Object类。这是通过静态工厂方法genericMethodType来创建的。方法genericMethodType有两种重载形式：第一种形式只需要指明方法类型中包含的Object类型的参数个数即可，而第二种形式可以提供一个额外的参数来说明是否在参数列表的后面添加一个Object[]类型的参数。
	//在代码清单2-32中，mt1有3个类型为Object的参数，而mt2有2个类型为Object的参数和后面的Object[]类型参数。
	public void generateGenericMethodTypes() {
		MethodType mt1 = MethodType.genericMethodType(3);
		MethodType mt2 = MethodType.genericMethodType(2, true);
	}

	/**
	 * 在通过工厂方法创建出MethodType类的对象实例之后，可以对其进行进一步修改。这些修改都围绕返回值和参数类型展开。所有这些修改方法都返回另外一个新的MethodType对象。由于每个对MethodType对象进行修改的方法的返回值都是一个新的MethodType对象，可以很容易地通过方法级联来简化代码。
	 * 代码清单2-34给出了对MethodType中的返回值和参数类型进行修改的示例代码。基本的修改操作包括改变返回值类型、添加和插入新参数、删除已有参数和修改已有参数的类型等。在每个修改方法上以注释形式给出修改之后的类型，括号里面是参数类型列表，外面是返回值类型。
	代码清单2-34　对MethodType中的返回值和参数类型进行修改的示例
	 */
	public void changeMethodType() {
		//(int,int)String
		MethodType mt = MethodType.methodType(String.class, int.class, int.class);
		//(int,int,float)String
		mt = mt.appendParameterTypes(float.class);
		//(int,double,long,int,float)String
		mt = mt.insertParameterTypes(1, double.class, long.class);
		//(int,double,int,float)String
		mt = mt.dropParameterTypes(2, 3);
		//(int,double,String,float)String
		mt = mt.changeParameterType(2, String.class);
		//(int,double,String,float)void
		mt = mt.changeReturnType(void.class);
	}

	/**
	 * 除了上面这几个精确修改返回值和参数的类型的方法之外，MethodType还有几个可以一次性对返回值和所有参数的类型进行处理的方法。代码清单2-35给出了这几个方法的使用示例，其中wrap和unwrap用来在基本类型及其包装类型之间进行转换，generic方法把所有返回值和参数类型都变成Object类型，而erase只把引用类型变成Object，并不处理基本类型。修改之后的方法类型同样以注释的形式给出。
	代码清单2-35　一次性修改MethodType中的返回值和所有参数的类型的示例
	 */
	public void wrapAndGeneric() {
		//(int,double)Integer
		MethodType mt = MethodType.methodType(Integer.class, int.class, double.class);
		//(Integer,Double)Integer
		MethodType wrapped = mt.wrap();
		//(int,double)int
		MethodType unwrapped = mt.unwrap();
		//(Object,Object)Object
		MethodType generic = mt.generic();
		//(int,double)Object
		MethodType erased = mt.erase();
	}

}
