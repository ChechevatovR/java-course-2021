package markup;

public abstract class AbstractWrapper implements MaybeSerializable {
    protected final MaybeSerializable content;

    public AbstractWrapper(final MaybeSerializable content) {
        this.content = content;
    }

    protected void toMarkdown(final StringBuilder sb, final String prefix, final String suffix) {
        sb.append(prefix);
        content.toMarkdown(sb);
        sb.append(suffix);
    }

    protected void toHtml(final StringBuilder sb, final String prefix, final String suffix) {
        sb.append(prefix);
        content.toHtml(sb);
        sb.append(suffix);
    }
}
