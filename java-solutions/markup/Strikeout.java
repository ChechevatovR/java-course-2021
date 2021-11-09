package markup;

import java.util.List;

public class Strikeout extends AbstractWrapper implements MarkElement {
    public Strikeout(List<MarkElement> children) {
        super(new SimpleParent(children));
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        super.toMarkdown(sb, "~", "~");
    }

    @Override
    public void toHtml(StringBuilder sb) {
        super.toHtml(sb, "<s>", "</s>");
    }
}
