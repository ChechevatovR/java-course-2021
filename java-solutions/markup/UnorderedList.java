package markup;

import java.util.List;

public class UnorderedList extends AbstractWrapper implements HtmlSerializable {
    public UnorderedList(List<ListItem> children) {
        super(new SimpleParent(children));
    }

    @Override
    public void toHtml(StringBuilder sb) {
        super.toHtml(sb, "<ul>", "</ul>");
    }

    @Override
    public void toMarkdown(StringBuilder sb) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
