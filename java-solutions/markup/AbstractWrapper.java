package markup;

import java.util.List;

public class AbstractWrapper extends AbstractParent {
    protected String prefix;
    protected String suffix;

    public AbstractWrapper(List<MarkdownSerializable> children) {
        super(children);
    }

    // public AbstractWrapper(String prefix, String suffix, List<MarkdownSerializable> children) {
        // this(children);
        // this.setWrappers(prefix, suffix);
    // }

    public void setWrappers(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        sb.append(this.prefix);
        super.toMarkdown(sb);
        sb.append(this.suffix);
    }
}
