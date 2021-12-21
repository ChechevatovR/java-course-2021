package markup;

import java.util.List;

public class ListItem extends AbstractWrapper implements HtmlSerializable {
    public ListItem(final List<? extends HtmlSerializable> children) {
        super(new SimpleParent(children));
    }

    @Override
    public void toMarkdown(final StringBuilder sb) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void toHtml(final StringBuilder sb) {
        toHtml(sb, "<li>", "</li>");
    }
}
