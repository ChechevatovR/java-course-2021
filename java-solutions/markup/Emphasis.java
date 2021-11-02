package markup;

import java.util.List;

public class Emphasis extends AbstractWrapper {
    public Emphasis(List<MarkdownSerializable> children) {
        super(children);
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        super.setWrappers("*", "*");
        super.toMarkdown(sb);
    }
}
