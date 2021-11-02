package markup;

import java.util.List;

public class OrderedList extends AbstractWrapper {
    public OrderedList(List<ListItem> children) {
        super(children);
    }

    @Override
    public void toHtml(StringBuilder sb) {
        super.setWrappers("<ol>", "</ol>");
        super.toHtml(sb);
    }
}
