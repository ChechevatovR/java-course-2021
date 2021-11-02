package markup;

import java.util.List;

public class AbstractParent implements markup.MarkdownSerializable {
    protected final List<MarkdownSerializable> children;

    public AbstractParent(List<MarkdownSerializable> children) {
        this.children = children;
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        for (MarkdownSerializable child : children) {
            child.toMarkdown(sb);
        }
    }
}
