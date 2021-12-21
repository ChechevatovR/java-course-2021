package util;

import java.io.*;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.InputMismatchException;
import java.util.function.Predicate;

import util.IsLineSeparator;
import util.IsNotWhitespace;
import util.IsWordCharacter;

public class Scanner implements AutoCloseable {
    private final int BUFFER_SIZE = 512;
    private static final int READ_AHEAD_LIMIT = 1 << 21;

    private final Reader reader;
    private final CharBuffer buf = CharBuffer.allocate(BUFFER_SIZE).limit(0);
    private final CharBuffer bufFrw = CharBuffer.allocate(BUFFER_SIZE).limit(0);

    public int linesSkipped;

    // =================================[ CONSTRUCTORS ]=================================

    public Scanner(final File file) throws FileNotFoundException {
        reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(file),
                        StandardCharsets.UTF_8
                )
        );
    }

    public Scanner(final InputStream is) {
        reader = new BufferedReader(new InputStreamReader(is));
    }

    public Scanner(final String s) {
        reader = new StringReader(s);
    }

    // ==========================[ BASIC READER INTERACTIONS ]===========================

    public void close() {
        try {
            reader.close();
        } catch (final IOException e) {
            System.err.println("There was an IOException when closing scanner");
        }
    }

    private void mark() throws IOException {
        try {
            reader.mark(READ_AHEAD_LIMIT);
        } catch (final IOException e) {
            System.err.println("There was a problem when marking reader: " + e.getMessage());
            throw e;
        }
        final int sz = buf.limit() - buf.position();
        final int ps = buf.position();
        bufFrw.clear();
        bufFrw.put(buf);
        buf.position(ps);
        bufFrw.rewind();
        bufFrw.limit(sz);
    }

    private void reset() throws IOException {
        try {
            reader.reset();
        } catch (final IOException e) {
            System.err.println("There was a problem when resetting reader: " + e.getMessage());
            throw e;
        }
    }

    private void read(final CharBuffer dst) throws IOException {
        // Reads into the buffer with destroying data already stored
        dst.clear();
        dst.limit(Math.max(0, reader.read(dst)));
        dst.rewind();
    }

    private static boolean isEmpty(final CharBuffer buf) {
        return buf.position() == buf.limit();
    }

    public boolean hasSomething() throws IOException {
        return hasNextToken(c -> true);
    }

    // ===============================[ TOKEN SPLITTING ]================================

    private String nextToken(final CharBuffer buf, final Predicate<Character> isTokenChar, final boolean mustMark) throws IOException {
        if (mustMark) {
            mark();
        }
        try {
            final StringBuilder sb = new StringBuilder();
            boolean found = false;
            linesSkipped = 0;
            while (true) {
                boolean mustExit = false;
                while (!isEmpty(buf)) {
                    final char cur = buf.get();
                    if (isTokenChar.test(cur)) {
                        found = true;
                        sb.append(cur);
                    } else if (found) {
                        buf.position(buf.position() - 1);
                        mustExit = true;
                        break;
                    } else if (new IsLineSeparator().test(cur)) {
                        linesSkipped++;
                        if (cur == '\r') {
                            if (isEmpty(buf)) {
                                read(buf);
                            }
                            if (!isEmpty(buf)) {
                                if (buf.get() != '\n') {
                                    buf.position(buf.position() - 1);
                                }
                            }
                        }
                    }
                }
                if (mustExit) {
                    break;
                }
                read(buf);
                if (isEmpty(buf)) {
                    break;
                }
            }
            return sb.toString();
        } catch (final IOException e) {
            throw e;
        } finally {
            if (mustMark) {
                reset();
            }
        }

    }

    public String nextToken(final Predicate<Character> isTokenChar) throws IOException {
        final StringBuilder sb = new StringBuilder();
        boolean found = false;
        linesSkipped = 0;
        outer: do {
            while (!isEmpty(buf)) {
                final char cur = buf.get();
                if (isTokenChar.test(cur)) {
                    found = true;
                    sb.append(cur);
                } else if (found) {
                    buf.position(buf.position() - 1);
                    break outer;
                }
                if (new IsLineSeparator().test(cur)) {
                    linesSkipped++;
                    if (cur == '\r') {
                        if (isEmpty(buf)) {
                            read(buf);
                        }
                        if (!isEmpty(buf)) {
                            if (buf.get() != '\n') {
                                buf.position(buf.position() - 1);
                            }
                        }
                    }
                }
            }
            read(buf);
        } while(!isEmpty(buf));
        return sb.toString();
    }

    public boolean hasNextToken(final Predicate<Character> isTokenChar) throws IOException {
        mark();
        try {
            do {
                while (!isEmpty(bufFrw)) {
                    if (isTokenChar.test(bufFrw.get())) {
                        return true;
                    }
                }
                read(bufFrw);
            } while (!isEmpty(bufFrw));
            return false;
        } finally {
            reset();
        }
    }

    // ==================================[ NUMERICS ]====================================

    public boolean hasNextInt() throws IOException {
        try {
            Integer.parseInt(nextToken(bufFrw, new IsNotWhitespace(), true));
            return true;
        } catch (final NumberFormatException e) {
            return false;
        }
    }

    public int nextInt() throws InputMismatchException, IOException {
        try {
            return Integer.parseInt(nextToken(buf, new IsNotWhitespace(), false));
        } catch (final NumberFormatException e) {
            throw new InputMismatchException();
        }
    }

    public boolean hasNextHex() throws IOException {
        final String token = nextToken(bufFrw, new IsNotWhitespace(), true);
        if (token.startsWith("0x") || token.startsWith("0X")) {
            try {
                Integer.parseUnsignedInt(token.substring(2), 16);
                return true;
            } catch (final NumberFormatException ignored) {}
        }
        return false;
    }

    public int nextHex() throws InputMismatchException, IOException {
        final String token = nextToken(buf, new IsNotWhitespace(), false);
        if (token.startsWith("0x") || token.startsWith("0X")) {
            try {
                return Integer.parseUnsignedInt(token.substring(2), 16);
            } catch (final NumberFormatException ignored) {}
        }
        throw new InputMismatchException();
    }

    private String literalToDigital(final String s) throws NumberFormatException {
        if (s.isEmpty()) {
            return s;
        }
        final StringBuilder sb = new StringBuilder();
        int i = 0;
        if (s.charAt(0) == '-') {
            sb.append('-');
            i++;
        }
        for (/* Using i defined before */; i < s.length(); i++) {
            final char cur = s.charAt(i);
            if ('a' <= cur && cur < 'a' + 10) {
                sb.append((char) (cur - 'a' + '0'));
            } else {
                throw new NumberFormatException();
            }
        }
        return sb.toString();
    }

    public boolean hasNextLit() throws IOException {
        final String token = nextToken(bufFrw, new IsNotWhitespace(), true);
        try {
            Integer.parseInt(literalToDigital(token));
            return true;
        } catch (final NumberFormatException e) {
            return false;
        }
    }

    public int nextLit() throws InputMismatchException, IOException {
        final String token = nextToken(buf, new IsNotWhitespace(), false);
        try {
            return Integer.parseInt(literalToDigital(token));
        } catch (final NumberFormatException e) {
            throw new InputMismatchException();
        }
    }

    public boolean hasNextNumber() throws IOException {
        return hasNextInt() || hasNextHex() || hasNextLit();
    }

    public int nextNumber() throws IOException {
        if (hasNextInt()) {
            return nextInt();
        } else if (hasNextHex()) {
            return nextHex();
        } else if (hasNextLit()) {
            return nextLit();
        }
        throw new InputMismatchException();
    }

    // ===================================[ WORDS ]======================================

    public boolean hasNextWord() throws IOException {
        return !nextToken(bufFrw, new IsWordCharacter(), true).isEmpty();
    }

    public String nextWord() throws InputMismatchException, IOException {
        final String token = nextToken(buf, new IsWordCharacter(), false);
        if (token.isEmpty()) {
            throw new InputMismatchException();
        }
        return token;
    }
}
