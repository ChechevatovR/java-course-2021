import java.io.*;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.nio.CharBuffer;

import static java.lang.Math.max;

public class M {
    private static int[] readArray(int length, Scanner in) throws IOException {
        int[] array = new int[length + 1];
        for (int i = 1; i <= length; i++) {
            array[i] = in.nextInt();
        }
        return array;
    }

    private static void solveTest(Scanner in) throws IOException {
        Map<Integer, Integer> C = new TreeMap<>();
        int n = in.nextInt();
        int[] problems = readArray(n, in);
        C.put(problems[n], 1);

        long ans = 0;
        for (int j = n - 1; j >= 0; j--) {
            for (int i = 1; i < j; i++) {
                ans += C.getOrDefault(2 * problems[j] - problems[i], 0);
            }
            C.put(problems[j], C.getOrDefault(problems[j], 0) + 1);
        }
        System.out.println(ans);
    }

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        while (t-- > 0) {
            solveTest(in);
        }
        in.close();
    }
}

// ================[ Scanner ]================

class isNotWhitespace implements Predicate<Character> {
    @Override
    public boolean test(Character c) {
        return !Character.isWhitespace(c);
    }
}

class Scanner {
    private final int BUFFER_SIZE = 512;

    private final Reader reader;
    private final CharBuffer buf = CharBuffer.allocate(this.BUFFER_SIZE).limit(0);

    private final Predicate<Character> isNotWhitespace = new isNotWhitespace();

    // =================================[ CONSTRUCTORS ]=================================

    public Scanner(InputStream is) {
        this.reader = new BufferedReader(new InputStreamReader(is));
    }

    // WORKAROUND FOR JAVA < 15

    private boolean isEmpty(CharBuffer buf) {
        return buf.position() == buf.limit();
    }

    // ==========================[ BASIC READER INTERACTIONS ]===========================

    public void close() {
        try {
            this.reader.close();
        } catch (IOException e) {
            System.err.println("There was an IOException when closing scanner");
        }
    }

    private void read() throws IOException {
        // Reads into the buffer with destroying data already stored
        this.buf.clear();
        this.buf.limit(max(0, this.reader.read(this.buf)));
        this.buf.rewind();
    }

    // ===============================[ TOKEN SPLITTING ]================================

    private String nextToken(Predicate<Character> isTokenChar) throws IOException {
        StringBuilder sb = new StringBuilder();
        boolean found = false;
        outer: while (true) {
            while (!isEmpty(this.buf)) {
                char cur = this.buf.get();
                if (isTokenChar.test(cur)) {
                    found = true;
                    sb.append(cur);
                } else if (found) {
                    this.buf.position(this.buf.position() - 1);
                    break outer;
                }
            }
            this.read();
            if (isEmpty(this.buf)) {
                break;
            }
        }
        return sb.toString();
    }

    public int nextInt() throws InputMismatchException, IOException {
        try {
            return Integer.parseInt(this.nextToken(this.isNotWhitespace));
        } catch (NumberFormatException e) {
            throw new InputMismatchException();
        }
    }
}
