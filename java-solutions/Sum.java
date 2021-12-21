public class Sum {
    static int toIntSafe(final String s) {
        if (s.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(s);
    }

    public static void main(final String[] args) {
        int result = 0;
        for (int i = 0; i < args.length; i++) {
            final String arg = args[i];
            int substringBegin = -1;

            for (int j = 0; j < arg.length(); j++) {
                final char curChar = arg.charAt(j);
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
                result += toIntSafe(arg.substring(substringBegin));
            }
        }
        System.out.println(result);
    }
}