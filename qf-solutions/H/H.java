import java.io.*;
import java.util.function.Predicate;
import java.util.InputMismatchException;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;

import static java.lang.Math.max;

public class H {
    static int[] transactions;
    static int[] answers;
    static int[] queryToTransaction;
    static int[] transactionsPrefixSum;

    private static void solveTest(int t) {
        int curTransaction = 0;
        while (curTransaction < transactions.length) {
            answers[t]++;
            int nextQuery = transactionsPrefixSum[curTransaction] + t;
            if (nextQuery >= queryToTransaction.length) {
                curTransaction  = transactions.length;
            } else {
                curTransaction = queryToTransaction[transactionsPrefixSum[curTransaction] + t];
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        transactions = new int[n];
        int totalQueries = 0;
        int maxTransaction = 0;
        for (int i = 0; i < n; i++) {
            transactions[i] = in.nextInt();
            totalQueries += transactions[i];
            maxTransaction = max(maxTransaction, transactions[i]);
        }

        answers = new int[totalQueries + 1];
        queryToTransaction = new int[totalQueries];
        transactionsPrefixSum = new int[n];
        transactionsPrefixSum[0] = 0;
        for (int i = 1; i < n; i++) {
            transactionsPrefixSum[i] = transactionsPrefixSum[i - 1] + transactions[i - 1];
        }

        for (int i = 0, query = 0; i < n; i++) {
            for (int j = 0; j < transactions[i]; j++, query++) {
                queryToTransaction[query] = i;
            }
        }

        int q = in.nextInt();
        while (q-- > 0) {
            int t = in.nextInt();
            if (t < maxTransaction) {
                System.out.println("Impossible");
            } else if (answers[t] != 0) {
                System.out.println(answers[t]);
            } else {
                solveTest(t);
                System.out.println(answers[t]);
            }
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
