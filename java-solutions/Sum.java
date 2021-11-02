public class Sum {
    static int toIntSafe(String s) {
        if (s.isEmpty()) {
            return 0;
        }
        return Integer.valueOf(s);
    }

    public static void main(String[] args) {
        int result = 0;
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            int substringBegin = -1;

            for (int j = 0; j < arg.length(); j++) {
                char curChar = arg.charAt(j);
                if (Character.isWhitespace(curChar)) {
                    if (substringBegin != -1) {
                        result += toIntSafe(arg.substring(substringBegin, j));
                        substringBegin = -1;
                    }
                } else if (substringBegin == -1) {
                        substringBegin = j;
                }
            }
            if (substringBegin != -1) {
                result += toIntSafe(arg.substring(substringBegin, arg.length()));
            }
        }
        System.out.println(result);
    }
}