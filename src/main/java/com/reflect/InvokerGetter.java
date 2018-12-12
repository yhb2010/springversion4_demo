package com.reflect;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

/**反射帮助类
 * @author dell
 *
 */
public class InvokerGetter {

	public static class Employee {

        public Employee() {
			super();
		}

		public Employee(String name) {
			super();
			this.name = name;
		}

		private String name = "Rick";

        public String getName() {
            return name + "##";
        }

        public void setName(String name) {
            this.name = name;
        }

        public String name() {
            return name;
        }

    }

	public <T> void unreflectGetter(T t) throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle methodHandle;
		Field fieldName = null;
        for (Field field : t.getClass().getDeclaredFields()) {
            if (field.getName().equals("name")) {
                fieldName = field;
                fieldName.setAccessible(true);
                break;
            }
        }
        methodHandle = lookup.unreflectGetter(fieldName);
        String name = (String) methodHandle.invoke(t);
        System.out.println(name);
	}

	public static void main(String[] args) throws Throwable {
		InvokerGetter u = new InvokerGetter();
		u.unreflectGetter(new Employee("张苏磊"));
	}

}
