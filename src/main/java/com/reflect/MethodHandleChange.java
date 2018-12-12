package com.reflect;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

//方法句柄变换
//方法句柄的强大之处在于可以对它进行各种不同的变换操作。这些变换操作包括对方法句柄的返回值和参数的处理等，同时这些单个的变换操作可以组合起来，形成复杂的变换过程。所有的这些变换方法都是MethodHandles类中的静态方法。这些方法一般接受一个已有的方法句柄对象作为变换的来源，而方法的返回值则是变换操作之后得到的新的方法句柄。下面的内容中经常出现的“原始方法句柄”表示的是变换之前的方法句柄，而“新方法句柄”则表示变换之后的方法句柄。
public class MethodHandleChange {

	//首先介绍对参数进行处理的变换方法。在调用变换之后的新方法句柄时，调用时的参数值会经过一定的变换操作之后，再传递给原始的方法句柄来完成具体的执行。
	//第一个方法dropArguments可以在一个方法句柄的参数中添加一些无用的参数。这些参数虽然在实际调用时不会被使用，但是它们可以使变换之后的方法句柄的参数类型格式符合某些所需的特定模式。这也是这种变换方式的主要应用场景。
	//如代码清单2-51所示，原始的方法句柄mhOld引用的是String类中的substring方法，其类型是String类的返回值加上两个int类型的参数。在调用dropArguments方法的时候，第一个参数表示待变换的方法句柄，第二个参数指定的是要添加的新参数类型在原始参数列表中的起始位置，其后的多个参数类型将被添加到参数列表中。新的方法句柄mhNew的参数类型变为float、String、String、int和int，而在实际调用时，前面两个参数的值会被忽略掉。可以把这些多余的参数理解成特殊调用模式所需要的占位符。
	public static void dropArguments() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodType type = MethodType.methodType(String.class, int.class, int.class);
		MethodHandle mhOld = lookup.findVirtual(String.class, "substring", type);
		String value = (String) mhOld.invoke("Hello", 2, 3);
		System.out.println(value);
		MethodHandle mhNew = MethodHandles.dropArguments(mhOld, 0, float.class, String.class);
		value = (String) mhNew.invoke(0.5f, "Ignore", "Hello", 2, 3);
		System.out.println(value);
	}

	//第二个方法insertArguments的作用与本小节前面提到的MethodHandle的bindTo方法类似，但是此方法的功能更加强大。这个方法可以同时为方法句柄中的多个参数预先绑定具体的值。在得到的新方法句柄中，已经绑定了具体值的参数不再需要提供，也不会出现在参数列表中。
	//在代码清单2-52中，方法句柄mhOld所表示的底层方法是String类中的concat方法。在调用insertArguments方法的时候，与上面的dropArguments方法类似，从第二个参数所指定的参数列表中的位置开始，用其后的可变长度的参数的值作为预设值，分别绑定到对应的参数上。在这里把mhOld的第二个参数的值预设成了固定值“--”，其作用是在调用新方法句柄时，只需要传入一个参数即可，相当于总是与“--”进行字符串连接操作，即使用“--”作为后缀。由于有一个参数被预先设置了值，因此mhNew在调用时只需要一个参数即可。如果预先绑定的是方法句柄mhOld的第一个参数，那就相当于用字符串“--”来连接各种不同的字符串，即为字符串添加“--”作为前缀。如果insertArguments方法调用时指定了多个绑定值，会按照第二个参数指定的起始位置，依次进行绑定。
	public static void insertArguments() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodType type = MethodType.methodType(String.class, String.class);
		MethodHandle mhOld = lookup.findVirtual(String.class, "concat", type);
		String value = (String) mhOld.invoke("Hello", "World");
		System.out.println(value);
		MethodHandle mhNew = MethodHandles.insertArguments(mhOld, 1, " --");
		value = (String) mhNew.invoke("Hello"); //值为“Hello--”
		System.out.println(value);
	}

	//第三个方法filterArguments的作用是可以对方法句柄调用时的参数进行预处理，再把预处理的结果作为实际调用时的参数。预处理的过程是通过其他的方法句柄来完成的。可以对一个或多个参数指定用来进行处理的方法句柄。
	//代码清单2-53给出了filterArguments方法的使用示例。要执行的原始方法句柄所引用的是Math类中的max方法，而在实际调用时传入的却是两个字符串类型的参数。中间的参数预处理是通过方法句柄mhGetLength来完成的，该方法句柄的作用是获得字符串的长度。这样就可以把字符串类型的参数转换成原始方法句柄所需要的整数类型。完成预处理之后，将处理的结果交给原始方法句柄来完成调用。
	//在使用filterArguments的时候，第二个参数和后面的可变长度的方法句柄参数是配合起来使用的。第二个参数指定的是进行预处理的方法句柄需要处理的参数在参数列表中的起始位置。紧跟在后面的是一系列对应的完成参数预处理的方法句柄。方法句柄与它要处理的参数是一一对应的。如果希望跳过某些参数不进行处理，可以使用null作为方法句柄的值。在进行预处理的时候，要注意预处理方法句柄和原始方法句柄之间的类型匹配。如果预处理方法句柄用于对某个参数进行处理，那么该方法句柄只能有一个参数，而且参数的类型必须匹配所要处理的参数的类型；其返回值类型需要匹配原始方法句柄中对应的参数类型。只有类型匹配，才能用方法句柄对实际传入的参数进行预处理，再把预处理的结果作为原始方法句柄调用时的参数来使用。
	public static void filterArguments() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodType type = MethodType.methodType(int.class, int.class, int.class);
		MethodHandle mhGetLength = lookup.findVirtual(String.class, "length", MethodType.methodType(int.class));
		MethodHandle mhTarget = lookup.findStatic(Math.class, "max", type);
		MethodHandle mhNew = MethodHandles.filterArguments(mhTarget, 0, mhGetLength, mhGetLength);
		int value = (int) mhNew.invoke("Hello", "New World"); //值为9
		System.out.println(value);
	}

	//第四个方法foldArguments的作用与filterArguments很类似，都是用来对参数进行预处理的。不同之处在于，foldArguments对参数进行预处理之后的结果，不是替换掉原始的参数值，而是添加到原始参数列表的前面，作为一个新的参数。当然，如果参数预处理的返回值是void，则不会添加新的参数。另外，参数预处理是由一个方法句柄完成的，而不是像filterArguments那样可以由多个方法句柄来完成。这个方法句柄会负责处理根据它的类型确定的所有可用参数。下面先看一下具体的使用示例。
	//代码清单2-54中原始的方法句柄引用的是静态方法targetMethod，而用来对参数进行预处理的方法句柄mhCombiner引用的是Math类中的max方法。变换之后的新方法句柄mhResult在被调用时，两个参数3和4首先被传递给句柄mhCombiner所引用的Math.max方法，返回值是4。这个返回值被添加到原始调用参数列表的前面，即得到新的参数列表4、3、4。这个新的参数列表会在调用时被传递给原始方法句柄mhTarget所引用的targetMethod方法。
	//进行参数预处理的方法句柄会根据其类型中参数的个数N，从实际调用的参数列表中获取前面N个参数作为它需要处理的参数。如果预处理的方法句柄有返回值，返回值的类型需要与原始方法句柄的第一个参数的类型匹配。这是因为返回值会被作为调用原始方法句柄时的第一个参数来使用。
	public static int targetMethod(int arg1, int arg2, int arg3) {
		return arg1;
	}

	public static void foldArguments() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodType typeCombiner = MethodType.methodType(int.class, int.class, int.class);
		MethodHandle mhCombiner = lookup.findStatic(Math.class, "max", typeCombiner);
		MethodType typeTarget = MethodType.methodType(int.class, int.class, int.class, int.class);
		MethodHandle mhTarget = lookup.findStatic(MethodHandleChange.class, "targetMethod", typeTarget);
		MethodHandle mhResult = MethodHandles.foldArguments(mhTarget, mhCombiner);
		int value = (int) mhResult.invoke(3, 4); //输出为4
		System.out.println(value);
	}

	//第五个方法permuteArguments的作用是对调用时的参数顺序进行重新排列，再传递给原始的方法句柄来完成调用。这种排列既可以是真正意义上的全排列，即所有的参数都在重新排列之后的顺序中出现；也可以是仅出现部分参数，没有出现的参数将被忽略；还可以重复某些参数，让这些参数在实际调用中出现多次。
	//代码清单2-55给出了一个对参数进行完全排列的示例。代码中的原始方法句柄mhCompare所引用的是Integer类中的compare方法。当使用参数3和4进行调用的时候，返回值是–1。通过permuteArguments方法把参数的排列顺序进行颠倒，得到了新的方法句柄mhNew。再用同样的参数调用方法句柄mhNew时，返回结果就变成了1，因为传递给底层compare方法的实际调用参数变成了4和3。新方法句柄mhDuplicateArgs在通过permuteArguments方法进行变换的时候，重复了第二个参数，因此传递给底层compare方法的实际调用参数是4和4，返回的结果是0。
	//在这里还要着重介绍一下permuteArguments方法的参数。第二个参数表示的是重新排列完成之后的新方法句柄的类型。紧接着的是多个用来表示新的排列顺序的整数。这些整数的个数必须与原始句柄的参数个数相同。整数出现的位置及其值就表示了在排列顺序上的对应关系。比如在上面的代码中，创建方法句柄mhNew的第一个整数参数是1，这就表示调用原始方法句柄时的第一个参数的值实际上是调用新方法句柄时的第二个参数（编号从0开始，1表示第二个）。
	public void permuteArguments() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodType type = MethodType.methodType(int.class, int.class, int.class);
		MethodHandle mhCompare = lookup.findStatic(Integer.class, "compare", type);
		int value = (int) mhCompare.invoke(3, 4); //值为-1
		MethodHandle mhNew = MethodHandles.permuteArguments(mhCompare, type, 1, 0);
		value = (int) mhNew.invoke(3, 4); //值为1
		MethodHandle mhDuplicateArgs = MethodHandles.permuteArguments(mhCompare, type, 1, 1);
		value = (int) mhDuplicateArgs.invoke(3, 4); // 值为0
	}

	//最后一个在对方法句柄进行变换时与参数相关的方法是guardWithTest。这个方法可以实现在方法句柄这个层次上的条件判断的语义，相当于if-else语句。使用guardWithTest时需要提供3个不同的方法句柄：第一个方法句柄用来进行条件判断，而剩下的两个方法句柄则分别在条件成立和不成立的时候被调用。用来进行条件判断的方法句柄的返回值类型必须是布尔型，而另外两个方法句柄的类型则必须一致，同时也是生成的新方法句柄的类型。
	//如代码清单2-57所示，进行条件判断的方法句柄mhTest引用的是静态guardTest方法，在条件成立和不成立的时候调用的方法句柄则分别引用了Math类中的max方法和min方法。由于guardTest方法的返回值是随机为true或false的，所以两个方法句柄的调用也是随机选择的。
	public static boolean guardTest() {
	return Math.random() > 0.5;
	}

	public void guardWithTest() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mhTest = lookup.findStatic(MethodHandleChange.class, "guardTest", MethodType.methodType(boolean.class));
		MethodType type = MethodType.methodType(int.class, int.class, int.class);
		MethodHandle mhTarget = lookup.findStatic(Math.class, "max", type);
		MethodHandle mhFallback = lookup.findStatic(Math.class, "min", type);
		MethodHandle mh = MethodHandles.guardWithTest(mhTest, mhTarget, mhFallback);
		int value = (int) mh.invoke(3, 5); //值随机为3或5
	}

	//除了可以在变换的时候对方法句柄的参数进行处理之外，还可以对方法句柄被调用后的返回值进行修改。对返回值进行处理是通过filterReturnValue方法来实现的。原始的方法句柄被调用之后的结果会被传递给另外一个方法句柄进行再次处理，处理之后的结果被返回给调用者。
	//代码清单2-58展示了filterReturnValue的用法。原始的方法句柄mhSubstring所引用的是String类的substring方法，对返回值进行处理的方法句柄mhUpperCase所引用的是String类的toUpperCase方法。通过filterReturnValue方法得到的新方法句柄的运行效果是将调用substring得到的子字符串转换成大写的形式。
	public static void filterReturnValue() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mhSubstring = lookup.findVirtual(String.class, "substring", MethodType.methodType(String.class, int.class));
		MethodHandle mhUpperCase = lookup.findVirtual(String.class, "toUpperCase", MethodType.methodType(String.class));
		MethodHandle mh = MethodHandles.filterReturnValue(mhSubstring, mhUpperCase);
		String str = (String) mh.invoke("Hello World", 5); //输出 WORLD
		System.out.println(str);
	}

	public static void main(String[] args) throws Throwable {
		MethodHandleChange.dropArguments();
		MethodHandleChange.insertArguments();
		MethodHandleChange.filterArguments();
		MethodHandleChange.foldArguments();
		MethodHandleChange.filterReturnValue();
	}

}
