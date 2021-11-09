package markup;

import java.util.List;

public class Emphasis extends AbstractWrapper implements MarkElement {
    public Emphasis(List<MarkElement> children) {
        super(new SimpleParent(children));
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        super.toMarkdown(sb, "*", "*");
    }

    @Override
    public void toHtml(StringBuilder sb) {
        super.toHtml(sb, "<em>", "</em>");
    }
}
