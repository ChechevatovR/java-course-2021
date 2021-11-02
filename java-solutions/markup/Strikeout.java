package markup;

import java.util.List;

public class Strikeout extends AbstractWrapper {
    public Strikeout(List<TextSerializable> children) {
        super(children);
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        super.setWrappers("~", "~");
        super.toMarkdown(sb);
    }

    @Override
    public void toHtml(StringBuilder sb) {
        super.setWrappers("<s>", "</s>");
        super.toHtml(sb);
    }
}
