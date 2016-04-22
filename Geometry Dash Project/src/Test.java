
public class Test {
	
	int bar; 
	int foo; 
	
	Test (){
		bar = 3; 
		foo = 2; 
	}

	public static void main (String args[]){
		
		Test t = new Test(); 
		System.out.println(t.foo);
		System.out.println(t.bar);
		System.out.println("Hello Colin, bientôt Weekend");
	}
}
