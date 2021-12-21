public class HelloWorld {
    static String firstUpper(final String name) {
        if (name.isEmpty()) {
            return "";
        }
        final char firstLetter = Character.toUpperCase(name.charAt(0));
        final String rest = name.substring(1);
        return firstLetter + rest;
    }

    public static void main(final String[] args) {
        System.out.println(args.length);
        for (int i = 0; i < args.length; i++) {
            System.out.println("Hello, " + firstUpper(args[i]) + "!");
        }
    }
}