import java.io.*;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.InputMismatchException;
import java.util.function.Predicate;

class isNotWhitespace implements Predicate<Character> {
    @Override
    public boolean test(Character c) {
        return !Character.isWhitespace(c);
    }
}

class isWordCharacter implements Predicate<Character> {
    @Override
    public boolean test(Character c) {
        return Character.isLetter(c) || Character.getType(c) == Character.DASH_PUNCTUATION || c == '\'';
    }
}

class isLineSeparator implements Predicate<Character> {
    // https://en.wikipedia.org/wiki/Newline#Unicode
    @Override
    public boolean test(Character c) {
        return
            c == 0x000A || // LF | Line feed
            c == 0x000B || // VT | Vertical tab
            c == 0x000C || // FF | Form feed
            c == 0x000D || // CR | Carriage return
            c == 0x0085 || // NL | Next line
            c == 0x2028 || // LS | Line separator
            c == 0x2029;   // PS | Paragraph separator
    }
}

public class Scanner {
    private final int BUFFER_SIZE = 512;
    private final int READ_AHEAD_LIMIT = 1 << 21;

    private final Reader reader;
    private final CharBuffer buf = CharBuffer.allocate(this.BUFFER_SIZE).limit(0);
    private final CharBuffer bufFrw = CharBuffer.allocate(this.BUFFER_SIZE).limit(0);

    private final Predicate<Character> isNotWhitespace = new isNotWhitespace();
    private final Predicate<Character> isWordCharacter = new isWordCharacter();
    private final Predicate<Character> isLineSeparator = new isLineSeparator();

    public int linesSkipped = 0;

    // =================================[ CONSTRUCTORS ]=================================

    public Scanner(File file) throws FileNotFoundException {
        this.reader = new BufferedReader(
            new InputStreamReader(
                new FileInputStream(file),
                StandardCharsets.UTF_8
            )
        );
    }

    public Scanner(InputStream is) {
        this.reader = new BufferedReader(new InputStreamReader(is));
    }

    // ==========================[ BASIC READER INTERACTIONS ]===========================

    public void close() {
        try {
            this.reader.close();
        } catch (IOException e) {
            System.err.println("There was an IOException when closing scanner");
        }
    }

    private void mark() throws IOException {
        try {
            this.reader.mark(this.READ_AHEAD_LIMIT);
        } catch (IOException e) {
            System.err.println("There was a problem when marking reader: " + e.getMessage());
            throw e;
        }
        int sz = this.buf.limit() - this.buf.position();
        int ps = this.buf.position();
        this.bufFrw.clear();
        this.bufFrw.put(this.buf);
        this.buf.position(ps);
        this.bufFrw.rewind();
        this.bufFrw.limit(sz);
    }

    private void reset() throws IOException {
        try {
            this.reader.reset();
        } catch (IOException e) {
            System.err.println("There was a problem when resetting reader: " + e.getMessage());
            throw e;
        }
    }

    private void read(CharBuffer dst) throws IOException {
        // Reads into the buffer with destroying data already stored
        dst.clear();
        dst.limit(Math.max(0, this.reader.read(dst)));
        dst.rewind();
    }

    private static boolean isEmpty(CharBuffer buf) {
        return buf.position() == buf.limit();
    }

    // ===============================[ TOKEN SPLITTING ]================================

    private String nextToken(CharBuffer buf, Predicate<Character> isTokenChar, boolean mustMark) throws IOException {
        if (mustMark) {
            this.mark();
        }
        try {
            StringBuilder sb = new StringBuilder();
            boolean found = false;
            this.linesSkipped = 0;
            while (true) {
                boolean mustExit = false;
                while (!isEmpty(buf)) {
                    char cur = buf.get();
                    if (isTokenChar.test(cur)) {
                        found = true;
                        sb.append(cur);
                    } else if (found) {
                        buf.position(buf.position() - 1);
                        mustExit = true;
                        break;
                    } else if (this.isLineSeparator.test(cur)) {
                        this.linesSkipped++;
                        if (cur == '\r') {
                            if (isEmpty(buf)) {
                                this.read(buf);
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
                this.read(buf);
                if (isEmpty(buf)) {
                    break;
                }
            }
            return sb.toString();
        } catch (IOException e) {
            throw e;
        } finally {
            if (mustMark) {
                this.reset();
            }
        }
        
    }

    // ==================================[ NUMERICS ]====================================

    public boolean hasNextInt() throws IOException {
        try {
            Integer.parseInt(this.nextToken(this.bufFrw, this.isNotWhitespace, true));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public int nextInt() throws InputMismatchException, IOException {
        try {
            return Integer.parseInt(this.nextToken(this.buf, this.isNotWhitespace, false));
        } catch (NumberFormatException e) {
            throw new InputMismatchException();
        }
    }

    public boolean hasNextHex() throws IOException {
        String token = this.nextToken(this.bufFrw, this.isNotWhitespace, true);
        if (token.startsWith("0x") || token.startsWith("0X")) {
            try {
                Integer.parseUnsignedInt(token.substring(2), 16);
                return true;
            } catch (NumberFormatException ignored) {}
        }
        return false;
    }

    public int nextHex() throws InputMismatchException, IOException {
        String token = this.nextToken(this.buf, this.isNotWhitespace, false);
        if (token.startsWith("0x") || token.startsWith("0X")) {
            try {
                return Integer.parseUnsignedInt(token.substring(2), 16);
            } catch (NumberFormatException ignored) {}
        }
        throw new InputMismatchException();
    }

    private String literalToDigital(String s) throws NumberFormatException {
        if (s.isEmpty()) {
            return s;
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        if (s.charAt(0) == '-') {
            sb.append('-');
            i++;
        }
        for (/* Using i defined before */; i < s.length(); i++) {
            char cur = s.charAt(i);
            if ('a' <= cur && cur < 'a' + 10) {
                sb.append((char) (cur - 'a' + '0'));
            } else {
                throw new NumberFormatException();
            }
        }
        return sb.toString();
    }

    public boolean hasNextLit() throws IOException {
        String token = this.nextToken(this.bufFrw, this.isNotWhitespace, true);
        try {
            Integer.parseInt(literalToDigital(token));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public int nextLit() throws InputMismatchException, IOException {
        String token = this.nextToken(this.buf, this.isNotWhitespace, false);
        try {
            return Integer.parseInt(literalToDigital(token));
        } catch (NumberFormatException e) {
            throw new InputMismatchException();
        } 
    }

    public boolean hasNextNumber() throws IOException {
        return this.hasNextInt() || this.hasNextHex() || this.hasNextLit();
    }

    public int nextNumber() throws IOException {
        if (this.hasNextInt()) {
            return this.nextInt();
        } else if (this.hasNextHex()) {
            return this.nextHex();
        } else if (this.hasNextLit()) {
            return this.nextLit();
        }
        throw new InputMismatchException();
    }

    // ===================================[ WORDS ]======================================

    public boolean hasNextWord() throws IOException {
        return !this.nextToken(this.bufFrw, this.isWordCharacter, true).isEmpty();
    }

    public String nextWord() throws InputMismatchException, IOException {
        String token = this.nextToken(this.buf, this.isWordCharacter, false);
        if (token.isEmpty()) {
            throw new InputMismatchException();
        }
        return token;
    }
}
