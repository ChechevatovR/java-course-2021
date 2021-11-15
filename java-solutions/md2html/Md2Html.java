package md2html;

import markup.*;
import pair.Pair;

import java.io.*;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.function.Predicate;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class Md2Html {
    private static void printErrorMessage(String message, Exception e) {
        System.err.println(message + e.getMessage());
    }

    private static List<List<String>> splitIntoParagraphs(Scanner in) throws IOException {
        Predicate<Character> isWhitespace = new Predicate<>() {
            @Override
            public boolean test(Character c) {
                return Character.isWhitespace(c);
            }
        };

        Predicate<String> isBlankString = new Predicate<>() {
            @Override
            public boolean test(String s) {
                for (char c : s.toCharArray()) {
                    if (!isWhitespace.test(c)) {
                        return false;
                    }
                }
                return true;
            }
        };

        Predicate<Character> isLineSeparator = new Predicate<>() {
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
        };

        Predicate<Character> isNotLineSeparator = isLineSeparator.negate();

        List<List<String>> paras = new ArrayList<>();
        List<String> curParagraph = new ArrayList<>();
        while (in.hasNextToken(isNotLineSeparator)) {
            String line = in.nextToken(isNotLineSeparator);
            boolean isBlank = isBlankString.test(line);
            if (in.linesSkipped > 1 || isBlank) {
                if (!curParagraph.isEmpty()) {
                    paras.add(curParagraph);
                    curParagraph = new ArrayList<>();
                }
            }
            if (!isBlank) {
                curParagraph.add(line);
            }
        }
        if (!curParagraph.isEmpty()) {
            paras.add(curParagraph);
        }
        return paras;
    }

    private static String mergeLines(List<String> lines) {
        return String.join(System.lineSeparator(), lines);
    }

    private static String getInlineTag(String s, int offset) {
        char tag0 = s.charAt(offset);
        String tag = "" + tag0;
        if (s.length() > offset + 1 && s.charAt(offset + 1) == tag0 && (tag0 == '_' || tag0 == '*' || tag0 == '-')) {
            tag += tag0;
        }
        return tag;
    }

    public static Paragraph parseParagraph(String s, int offset) {
        int level = 0;
        while (level < 6 && s.length() > level && s.charAt(level) == '#') {
            level++;
        }

        if (s.length() == level || !Character.isWhitespace(s.charAt(level))) {
            level = 0;
        }

        Pair<TextWithInlines, Integer> result = parseTextWithInlines(
                s,
                offset + level + (level > 0 ? 1 : 0),
                false,
                false,
                false,
                false,
                null,
                new HashSet<String>()
        );
        return new Paragraph(result.first, level);
    }

    public static Pair<TextWithInlines, Integer> parseTextWithInlines(
            String s,
            int offset,
            boolean skipEmphasis,
            boolean skipStrong,
            boolean skipStrikeout,
            boolean skipCode,
            String closeOn,
            Collection<String> abortOn
    ) {
        List<InlineMarkup> children = new ArrayList<>();
        StringBuilder currentText = new StringBuilder();
        int i = 0;
        boolean prevIsWhitespace = false;
        while (s.length() > offset + i) {
            char c = s.charAt(offset + i);

            // If next char is escaped
            if (c == '\\' && s.length() > offset + i + 1) {
                i++;
                currentText.append(s.charAt(offset + i++));
                prevIsWhitespace = false;
                continue;
            }

            // If character is a prefix of a tag
            if (c == '*' || c == '_' || c == '-' || c == '`') {
                String tag = getInlineTag(s, offset + i);

                if (abortOn.contains(tag)) {
                    return new Pair<>(null, 0);
                }

                // Tag after a whitespace is considered non-closing
                if (tag.equals(closeOn) && !prevIsWhitespace) {
                    children.add(new Text(currentText.toString()));
                    return new Pair<>(new TextWithInlines(children), i + tag.length());
                }

                // Tag, followed by a whitespace is considered non-opening
                if (s.length() > offset + i + tag.length() && !Character.isWhitespace(s.charAt(offset + i + tag.length()))) {
                    Pair<TextWithInlines, Integer> childRes = new Pair<>(null, 0);

                    abortOn.add(closeOn);

                    if ((tag.equals("_") || tag.equals("*")) && !skipEmphasis) {
                        childRes = parseTextWithInlines(s, offset + i + tag.length(), true, skipStrong, skipStrikeout, skipCode, tag, abortOn);
                    } else if ((tag.equals("__") || tag.equals("**")) && !skipStrong) {
                        childRes = parseTextWithInlines(s, offset + i + tag.length(), skipEmphasis, true, skipStrikeout, skipCode, tag, abortOn);
                    } else if (tag.equals("--") && !skipStrikeout) {
                        childRes = parseTextWithInlines(s, offset + i + tag.length(), skipEmphasis, skipStrong, true, skipCode, tag, abortOn);
                    } else if (tag.equals("`") && !skipCode) {
                        childRes = parseTextWithInlines(s, offset + i + tag.length(), skipEmphasis, skipStrong, skipStrikeout, true, tag, abortOn);
                    }

                    abortOn.remove(closeOn);

                    if (childRes.first != null) {
                        i += childRes.second + tag.length();
                        children.add(new Text(currentText.toString()));
                        currentText = new StringBuilder();
                        prevIsWhitespace = false;

                        if (tag.equals("_") || tag.equals("*")) {
                            children.add(new Emphasis(List.of(childRes.first)));
                        } else if (tag.equals("__") || tag.equals("**")) {
                            children.add(new Strong(List.of(childRes.first)));
                        } else if (tag.equals("--") && !skipStrikeout) {
                            children.add(new Strikeout(List.of(childRes.first)));
                        } else if (tag.equals("`") && !skipCode) {
                            children.add(new InlineCode(List.of(childRes.first)));
                        }
                        continue;
                    }
                }
            }

            // If character must be escaped in HTML
            if (c == '<') {
                currentText.append("&lt;");
            } else if (c == '>') {
                currentText.append("&gt;");
            } else if (c == '&') {
                currentText.append("&amp;");
            } else {
                currentText.append(c);
            }

            prevIsWhitespace = Character.isWhitespace(c);
            i++;
        }

        if (closeOn != null) {
            return new Pair<>(null, 0);
        }
        if (currentText.length() > 0) {
            children.add(new Text(currentText.toString()));
        }
        return new Pair<>(new TextWithInlines(children), i);
    }

    public static void main(String[] args) {
        List<List<String>> paragraphsAsStrings = new ArrayList<>();
        try (Scanner in = new Scanner(new File(args[0]))) {
            paragraphsAsStrings = splitIntoParagraphs(in);
        } catch (FileNotFoundException e) {
            printErrorMessage("Input file not found.", e);
        } catch (IOException e) {
            printErrorMessage("IO Exception happened while reading data.", e);
        }

        ArrayList<Paragraph> paragraphs = new ArrayList<>();
        for (List<String> paragraph : paragraphsAsStrings) {
            paragraphs.add(parseParagraph(mergeLines(paragraph), 0));
        }
        SimpleParent document = new SimpleParent(paragraphs);

        try (
            BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(
                    new FileOutputStream(args[1]),
                    StandardCharsets.UTF_8
                )
            )
        ) {
            StringBuilder sb = new StringBuilder();
            document.toHtml(sb);
            out.write(sb.toString());
        } catch (FileNotFoundException e) {
            printErrorMessage("Output file not found. It must not happen cause program must create one, but" +
                    "that did not happen for some reason", e);
        } catch (IOException e) {
            printErrorMessage("IO Exception happened while writing data.", e);
        }
    }
}

// =========== [Scanner] =============

class Scanner implements AutoCloseable {
    private final int BUFFER_SIZE = 512;
    private final int READ_AHEAD_LIMIT = 1 << 10;

    private final Reader reader;
    private final CharBuffer buf = CharBuffer.allocate(this.BUFFER_SIZE).limit(0);
    private final CharBuffer bufFrw = CharBuffer.allocate(this.BUFFER_SIZE).limit(0);

    static Predicate<Character> isLineSeparator = new Predicate<>() {
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
    };

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

    public String nextToken(Predicate<Character> isTokenChar) throws IOException {
        StringBuilder sb = new StringBuilder();
        boolean found = false;
        this.linesSkipped = 0;
        outer: do {
            while (!isEmpty(this.buf)) {
                char cur = this.buf.get();
                if (isTokenChar.test(cur)) {
                    found = true;
                    sb.append(cur);
                } else if (found) {
                    this.buf.position(this.buf.position() - 1);
                    break outer;
                }
                if (isLineSeparator.test(cur)) {
                    this.linesSkipped++;
                    if (cur == '\r') {
                        if (isEmpty(this.buf)) {
                            this.read(this.buf);
                        }
                        if (!isEmpty(this.buf)) {
                            if (this.buf.get() != '\n') {
                                this.buf.position(this.buf.position() - 1);
                            }
                        }
                    }
                }
            }
            this.read(this.buf);
        } while(!isEmpty(this.buf));
        return sb.toString();
    }

    public boolean hasNextToken(Predicate<Character> isTokenChar) throws IOException {
        this.mark();
        try {
            do {
                while (!isEmpty(this.bufFrw)) {
                    if (isTokenChar.test(this.bufFrw.get())) {
                        return true;
                    }
                }
                this.read(this.bufFrw);
            } while (!isEmpty(this.bufFrw));
            return false;
        } finally {
            this.reset();
        }
    }
}
