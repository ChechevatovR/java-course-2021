package markup;

import java.util.List;

public class Strikeout extends AbstractWrapper {
    public Strikeout(List<MarkdownSerializable> children) {
        super(children);
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        super.setWrappers("~", "~");
        super.toMarkdown(sb);
    }
}
