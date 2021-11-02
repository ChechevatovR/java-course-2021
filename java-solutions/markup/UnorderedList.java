package markup;

import java.util.List;

public class UnorderedList extends AbstractWrapper {
    public UnorderedList(List<ListItem> children) {
        super(children);
    }

    @Override
    public void toHtml(StringBuilder sb) {
        super.setWrappers("<ul>", "</ul>");
        super.toHtml(sb);
    }
}
