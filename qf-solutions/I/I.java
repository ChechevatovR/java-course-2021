import java.io.*;
import java.util.function.Predicate;
import java.util.InputMismatchException;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class I {
    static int INF = 2_000_000_000;

    private static int divCeil(int numerator, int denominator) {
        return (numerator + denominator - 1) / denominator;
    }

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);

        int xMin = INF;
        int yMin = INF;
        int xMax = -INF;
        int yMax = -INF;

        int n = in.nextInt();
        for (int i = 0; i < n; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            int h = in.nextInt();

            xMin = min(xMin, x - h);
            yMin = min(yMin, y - h);
            xMax = max(xMax, x + h);
            yMax = max(yMax, y + h);
        }
        in.close();

        int xCenter = (xMax + xMin) / 2;
        int yCenter = (yMax + yMin) / 2;
        System.out.print(xCenter + " " + yCenter + " " + divCeil(max(xMax - xMin, yMax - yMin), 2));
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

    private static boolean isEmpty(CharBuffer buf) {
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
