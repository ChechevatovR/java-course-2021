package markup;

import java.util.List;

public class Emphasis extends AbstractWrapper {
    public Emphasis(List<TextSerializable> children) {
        super(children);
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        super.setWrappers("*", "*");
        super.toMarkdown(sb);
    }

    @Override
    public void toHtml(StringBuilder sb) {
        super.setWrappers("<em>", "</em>");
        super.toHtml(sb);
    }
}
