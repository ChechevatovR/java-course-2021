package markup;

import java.util.List;

public class ListItem extends AbstractWrapper {
    public ListItem(List<? extends TextSerializable> children) {
        super(children);
    }

//    public ListItem(Paragraph paragraph) {
//        super(List.of(paragraph));
//    }

//    public ListItem(Text text) {
//        super(List.of(text));
//    }

    @Override
    public void toHtml(StringBuilder sb) {
        super.setWrappers("<li>", "</li>");
        super.toHtml(sb);
    }
}
