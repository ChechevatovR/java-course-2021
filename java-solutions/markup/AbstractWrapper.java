package markup;

import java.util.List;

public class AbstractWrapper extends AbstractParent {
    protected String prefix;
    protected String suffix;

    public AbstractWrapper(List<? extends TextSerializable> children) {
        super(children);
    }

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

    @Override
    public void toHtml(StringBuilder sb) {
        sb.append(this.prefix);
        super.toHtml(sb);
        sb.append(this.suffix);
    }
}
