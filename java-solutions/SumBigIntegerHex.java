import java.math.BigInteger;

public class SumBigIntegerHex {
    static BigInteger toNumberSafe(String s) {
        if (s.isEmpty()) {
            return BigInteger.ZERO;
        } else if (s.startsWith("0x") || s.startsWith("0X")) {
            return new BigInteger(s.substring(2), 16);
        } else {
            return new BigInteger(s);
        }
    }    
    
    public static void main(String[] args) {
        BigInteger result = BigInteger.ZERO;
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            int substringBegin = -1;
            
            for (int j = 0; j < arg.length(); j++) {
                if (Character.isWhitespace(arg.charAt(j))) {
                    if (substringBegin != -1) {
                        result = result.add(toNumberSafe(arg.substring(substringBegin, j)));
                        substringBegin = -1;
                    }
                } else if (substringBegin == -1) {
                    substringBegin = j;
                }                
            }
            
            if (substringBegin != -1) {
                result = result.add(toNumberSafe(arg.substring(substringBegin)));
            }
        }
        System.out.println(result);
    }
}