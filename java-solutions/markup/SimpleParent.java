package markup;

import java.util.List;

public class SimpleParent implements MaybeSerializable {
    protected final List<? extends MaybeSerializable> children;

    public SimpleParent(final List<? extends MaybeSerializable> children) {
        this.children = List.copyOf(children);
    }

    @Override
    public void toMarkdown(final StringBuilder sb) {
        for (final MaybeSerializable child : children) {
            child.toMarkdown(sb);
        }
    }

    @Override
    public void toHtml(final StringBuilder sb) {
        for (final MaybeSerializable child : children) {
            child.toHtml(sb);
        }
    }
}
