package markup;

import java.util.List;

public class SimpleParent implements MaybeSerializable {
    protected final List<? extends MaybeSerializable> children;

    public SimpleParent(List<? extends MaybeSerializable> children) {
        this.children = List.copyOf(children);
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        for (MaybeSerializable child : children) {
            child.toMarkdown(sb);
        }
    }

    @Override
    public void toHtml(StringBuilder sb) {
        for (MaybeSerializable child : children) {
            child.toHtml(sb);
        }
    }
}
