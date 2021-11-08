import java.io.*;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.InputMismatchException;
import java.util.function.Predicate;
//import java.util.Scanner;

import static java.lang.Math.max;

public class J {
    static int jogs[][];
    static boolean res[][];

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        jogs = new int[n][n];
        res = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                char t = in.nextDigit();
                jogs[i][j] = t - '0';
            }
        }
        in.close();

        for (int src = 0; src < n; src++) {
            for (int dst = src + 1; dst < n; dst++) {
                res[src][dst] = jogs[src][dst] > 0;
                if (!res[src][dst]) continue;
                for (int i = dst + 1; i < n; i++) {
                    jogs[src][i] = (jogs[src][i] - jogs[dst][i] + 10) % 10;
                }
                jogs[src][dst] = 0;
            }
        }

        for (int src = 0; src < n; src++) {
            for (int dst = 0; dst < n; dst++) {
                System.out.print(res[src][dst] ? "1" : "0");
            }
            System.out.println();
        }
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

    // WORKAROUND FOR JAVA 11

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

    public char nextDigit() throws IOException {
        while (true) {
            if (isEmpty(this.buf)) {
                this.read();
            }
            char t = this.buf.get();
            if ('0' <= t && t <= '9') {
                return t;
            }
        }
    }
}
