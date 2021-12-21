package md2html;

import markup.*;
import util.Pair;
import util.Scanner;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

public class Md2Html {
    private static List<List<String>> splitIntoParagraphs(final Scanner in) throws IOException {
        final Predicate<String> isBlankString = new Predicate<>() {
            @Override
            public boolean test(final String s) {
                for (final char c : s.toCharArray()) {
                    if (!Character.isWhitespace(c)) {
                        return false;
                    }
                }
                return true;
            }
        };

        final Predicate<Character> isLineSeparator = new Predicate<>() {
            // https://en.wikipedia.org/wiki/Newline#Unicode
            @Override
            public boolean test(final Character c) {
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

        final Predicate<Character> isNotLineSeparator = isLineSeparator.negate();

        final List<List<String>> paras = new ArrayList<>();
        List<String> curParagraph = new ArrayList<>();
        while (in.hasNextToken(isNotLineSeparator)) {
            final String line = in.nextToken(isNotLineSeparator);
            final boolean isBlank = isBlankString.test(line);
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

    private static String mergeLines(final List<String> lines) {
        return String.join(System.lineSeparator(), lines);
    }

    private static String escape(final char c) {
        if (c == '<') {
            return "&lt;";
        } else if (c == '>') {
            return "&gt;";
        } else if (c == '&') {
            return "&amp;";
        } else {
            return "" + c;
        }
    }

    private static String getInlineTag(final String s, final int offset) {
        final Predicate<Character> isTokenChar = new Predicate<>() {
            @Override
            public boolean test(final Character c) {
                return
                        c == '_'
                                || c == '*'
                                || c == '-'
                                || c == '{'
                                || c == '}'
                                || c == '<'
                                || c == '>'
                                || c == '`';
            }
        };

        final char tag0 = s.charAt(offset);
        if (isTokenChar.test(tag0)) {
            String tag = "" + tag0;
            if (s.length() > offset + 1 && s.charAt(offset + 1) == tag0 && isTokenChar.test(tag0)) {
                tag += tag0;
            }
            return tag;
        }
        return "";
    }

    public static ParagraphOrHeading parseParagraph(final String s, final int offset) {
        int level = 0;
        while (level < 6 && s.length() > level && s.charAt(level) == '#') {
            level++;
        }

        if (s.length() == level || !Character.isWhitespace(s.charAt(level))) {
            level = 0;
        }

        final Pair<TextWithInlines, Integer> result = parseInline(
                s,
                offset + level + (level > 0 ? 1 : 0),
                new SkipSettings(),
                null,
                new HashSet<>()
        );
        return new ParagraphOrHeading(result.first, level);
    }

    public static Pair<TextWithInlines, Integer> parseInline(final String s, final int offset, final SkipSettings skip, final String closeOn,
                                                             final Collection<String> abortOn) {
        final List<InlineMarkup> children = new ArrayList<>();
        StringBuilder currentText = new StringBuilder();
        int i = 0;
        boolean prevIsWhitespace = false;
        while (s.length() > offset + i) {
            final char c = s.charAt(offset + i);

            // If next char is escaped
            if (c == '\\' && s.length() > offset + ++i) {
                currentText.append(escape(s.charAt(offset + i++)));
                prevIsWhitespace = false;
                continue;
            }

            // If character is a prefix of a tag
            final String tag = getInlineTag(s, offset + i);
            if (!tag.isEmpty()) {
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
                    Pair<TextWithInlines, Integer> res = new Pair<>(null, 0);

                    abortOn.add(closeOn);

                    if ((tag.equals("_") || tag.equals("*")) && !skip.emphasis) {
                        res = parseInline(s, offset + i + tag.length(), skip.setKey("emphasis"), tag, abortOn);
                    } else if ((tag.equals("__") || tag.equals("**")) && !skip.strong) {
                        res = parseInline(s, offset + i + tag.length(), skip.setKey("strong"), tag, abortOn);
                    } else if (tag.equals("--") && !skip.strikeout) {
                        res = parseInline(s, offset + i + tag.length(), skip.setKey("strikeout"), tag, abortOn);
                    } else if (tag.equals("`") && !skip.code) {
                        res = parseInline(s, offset + i + tag.length(), skip.setKey("code"), tag, abortOn);
                    } else if (tag.equals("<<") && !skip.ins) {
                        res = parseInline(s, offset + i + tag.length(), skip.setKey("ins"), ">>", abortOn);
                    } else if (tag.equals("}}") && !skip.del) {
                        res = parseInline(s, offset + i + tag.length(), skip.setKey("del"), "{{", abortOn);
                    }

                    abortOn.remove(closeOn);

                    if (res.first != null) {
                        i += res.second + tag.length();
                        children.add(new Text(currentText.toString()));
                        currentText = new StringBuilder();
                        prevIsWhitespace = false;

                        if (tag.equals("_") || tag.equals("*")) {
                            children.add(new Emphasis(List.of(res.first)));
                        } else if (tag.equals("__") || tag.equals("**")) {
                            children.add(new Strong(List.of(res.first)));
                        } else if (tag.equals("--")) {
                            children.add(new Strikeout(List.of(res.first)));
                        } else if (tag.equals("`")) {
                            children.add(new InlineCode(List.of(res.first)));
                        } else if (tag.equals("<<")) {
                            children.add(new Insertion(List.of(res.first)));
                        } else if (tag.equals("}}")) {
                            children.add(new Deletion(List.of(res.first)));
                        }
                        continue;
                    }
                }
            }

            // If character must be escaped in HTML
            currentText.append(escape(c));
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

    public static void main(final String[] args) {
        List<List<String>> paragraphsAsStrings = new ArrayList<>();
        try (final Scanner in = new Scanner(new File(args[0]))) {
            paragraphsAsStrings = splitIntoParagraphs(in);
        } catch (final FileNotFoundException e) {
            System.err.println("Input file not found: " + e);
        } catch (final IOException e) {
            System.err.println("IO Exception happened while reading data: " + e);
        }

        final ArrayList<ParagraphOrHeading> paragraphs = new ArrayList<>();
        for (final List<String> paragraph : paragraphsAsStrings) {
            paragraphs.add(parseParagraph(mergeLines(paragraph), 0));
        }
        final SimpleParent document = new SimpleParent(paragraphs);

        try (
                final BufferedWriter out = new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(args[1]),
                                StandardCharsets.UTF_8
                        )
                )
        ) {
            final StringBuilder sb = new StringBuilder();
            document.toHtml(sb);
            out.write(sb.toString());
        } catch (final FileNotFoundException e) {
            System.err.println("Output file not found: " + e);
        } catch (final IOException e) {
            System.err.println("IO Exception happened while writing data: " + e);
        }
    }
}
