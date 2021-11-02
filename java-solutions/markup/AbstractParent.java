package markup;

import java.util.List;

public class AbstractParent implements TextSerializable {
    protected final List<? extends TextSerializable> children;

    public AbstractParent(List<? extends TextSerializable> children) {
        this.children = children;
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        for (TextSerializable child : children) {
            child.toMarkdown(sb);
        }
    }

    @Override
    public void toHtml(StringBuilder sb) {
        for (TextSerializable child : children) {
            child.toHtml(sb);
        }
    }
}
