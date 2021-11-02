package markup;

import java.util.List;

public class Strong extends AbstractWrapper {
    public Strong(List<TextSerializable> children) {
        super(children);
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        super.setWrappers("__", "__");
        super.toMarkdown(sb);
    }

    @Override
    public void toHtml(StringBuilder sb) {
        super.setWrappers("<strong>", "</strong>");
        super.toHtml(sb);
    }
}