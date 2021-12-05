public class HelloWorld {
	static String firstUpper(String name) {
		if (name.isEmpty()) {
			return "";
        }
		char firstLetter = Character.toUpperCase(name.charAt(0));
		String rest = name.substring(1);
		return firstLetter + rest;
	}
	
	public static void main(String[] args) {
		System.out.println(args.length);
		for (int i = 0; i < args.length; i++) { 
			System.out.println("Hello, " + firstUpper(args[i]) + "!");
		}
	}
}