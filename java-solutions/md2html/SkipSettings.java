package md2html;

public class SkipSettings {
    public boolean emphasis;
    public boolean strong;
    public boolean strikeout;
    public boolean code;
    public boolean ins;
    public boolean del;

    public SkipSettings() {
        emphasis = false;
        strong = false;
        strikeout = false;
        code = false;
        ins = false;
        del = false;
    }

    public SkipSettings(final SkipSettings copyFrom) {
        emphasis = copyFrom.emphasis;
        strong = copyFrom.strong;
        strikeout = copyFrom.strikeout;
        code = copyFrom.code;
        ins = copyFrom.ins;
        del = copyFrom.del;
    }

    SkipSettings setKey(final String keyToSetTrue) {
        final SkipSettings copy = new SkipSettings(this);
        switch (keyToSetTrue) {
            case "emphasis":
                copy.emphasis = true;
                break;
            case "strong":
                copy.strong = true;
                break;
            case "strikeout":
                copy.strikeout = true;
                break;
            case "code":
                copy.code = true;
                break;
            case "ins":
                copy.ins = true;
                break;
            case "del":
                copy.del = true;
                break;
        }
        return copy;
    }
}
