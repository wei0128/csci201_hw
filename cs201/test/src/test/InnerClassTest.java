package test;

public class InnerClassTest {
	public static void foo(C1 c) {
		c.foo();
		c.bar();
	}
	public static void main(String [] args) {
		InnerClassTest.foo(new C1("check") {
			void bar() {
				System.out.println("b");
			}
		});
	}
}

class C1{
	private String s;
	public C1(String s) {
		this.s = s;
	}
	void foo() {
		System.out.println(s);
	}
	void bar() {
		System.out.println("d");
	}
}
