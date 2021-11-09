package markup;

import java.util.List;

public class OrderedList extends AbstractWrapper implements HtmlSerializable {
    public OrderedList(List<ListItem> children) {
        super(new SimpleParent(children));
    }

    @Override
    public void toHtml(StringBuilder sb) {
        super.toHtml(sb, "<ol>", "</ol>");
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        throw new UnsupportedOperationException();
    }
}
