package markup;

import java.util.List;

public class ListItem extends AbstractWrapper implements HtmlSerializable {
    public ListItem(List<? extends HtmlSerializable> children) {
        super(new SimpleParent(children));
    }

    @Override
    public void toMarkdown(StringBuilder sb) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void toHtml(StringBuilder sb) {
        super.toHtml(sb, "<li>", "</li>");
    }
}
