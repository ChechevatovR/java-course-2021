package markup;

import java.util.List;

public class Strong extends AbstractWrapper implements MarkElement {
    public Strong(List<MarkElement> children) {
        super(new SimpleParent(children));
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        super.toMarkdown(sb, "__", "__");
    }

    @Override
    public void toHtml(StringBuilder sb) {
        super.toHtml(sb, "<strong>", "</strong>");
    }
}