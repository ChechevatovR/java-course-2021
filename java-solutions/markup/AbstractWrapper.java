package markup;

public abstract class AbstractWrapper implements MaybeSerializable {
    protected MaybeSerializable content;

    public AbstractWrapper(MaybeSerializable content) {
        this.content = content;
    }

    protected void toMarkdown(StringBuilder sb, String prefix, String suffix) {
        sb.append(prefix);
        content.toMarkdown(sb);
        sb.append(suffix);
    }

    protected void toHtml(StringBuilder sb, String prefix, String suffix) {
        sb.append(prefix);
        content.toHtml(sb);
        sb.append(suffix);
    }
}
