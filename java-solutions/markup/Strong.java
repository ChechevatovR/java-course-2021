package markup;

import java.util.List;

public class Strong extends AbstractWrapper {
    public Strong(List<MarkdownSerializable> children) {
        super(children);
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        super.setWrappers("__", "__");
        super.toMarkdown(sb);
    }
}