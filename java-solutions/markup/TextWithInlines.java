package markup;

import java.util.ArrayList;
import java.util.List;

import pair.Pair;

public class TextWithInlines extends SimpleParent implements InlineMarkup {

    public TextWithInlines(List<InlineMarkup> children) {
        super(children);
    }

    private static String getInlineTag(String s, int offset) {
        char tag0 = s.charAt(offset);
        String tag = "" + tag0;
        if (s.length() > offset + 1 && s.charAt(offset + 1) == tag0 && (tag0 == '_' || tag0 == '*' || tag0 == '-')) {
            tag += tag0;
        }
        return tag;
    }

    public static Pair<TextWithInlines, Integer> fromMdString(
            String s,
            int offset,
            boolean skipEmphasis,
            boolean skipStrong,
            boolean skipStrikeout,
            boolean skipCode,
            String closeOn
    ) {
        List<InlineMarkup> children = new ArrayList<>();
        StringBuilder currentText = new StringBuilder();
        int i = 0;
        boolean prevIsWhitespace = false;
        while (s.length() > offset + i) {
            char c = s.charAt(offset + i);
            if (c == '\\' && s.length() > offset + i + 1) {
                i++;
                currentText.append(s.charAt(offset + i++));
                prevIsWhitespace = false;
                continue;
            }
            if (c == '*' || c == '_' || c == '-' || c == '`') {
                String tag = getInlineTag(s, offset + i);

                // Tag after a whitespace is considered non-closing
                if (tag.equals(closeOn) && !prevIsWhitespace) {
                    children.add(new Text(currentText.toString()));
                    return new Pair<>(new TextWithInlines(children), i + tag.length());
                }

                // Tag, followed by a whitespace is considered non-opening
                if (s.length() > offset + i + tag.length() && !Character.isWhitespace(s.charAt(offset + i + tag.length()))) {
                    Pair<TextWithInlines, Integer> childRes = new Pair<>(null, 0);

                    if ((tag.equals("_") || tag.equals("*")) && !skipEmphasis) {
                        childRes = fromMdString(s, offset + i + tag.length(), true, skipStrong, skipStrikeout, skipCode, tag);
                    } else if ((tag.equals("__") || tag.equals("**")) && !skipStrong) {
                        childRes = fromMdString(s, offset + i + tag.length(), skipEmphasis, true, skipStrikeout, skipCode, tag);
                    } else if (tag.equals("--") && !skipStrikeout) {
                        childRes = fromMdString(s, offset + i + tag.length(), skipEmphasis, skipStrong, true, skipCode, tag);
                    } else if (tag.equals("`") && !skipCode) {
                        childRes = fromMdString(s, offset + i + tag.length(), skipEmphasis, skipStrong, skipStrikeout, true, tag);
                    }
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

            switch (c){
                case '<':
                    currentText.append("&lt;");
                    break;
                case '>':
                    currentText.append("&gt;");
                    break;
                case '&':
                    currentText.append("&amp;");
                    break;
                default:
                    currentText.append(c);
            }
            i++;
            prevIsWhitespace = Character.isWhitespace(c);
        }
        if (currentText.length() > 0) {
            children.add(new Text(currentText.toString()));
        }
        return new Pair<>(new TextWithInlines(children), i);
    }
}
