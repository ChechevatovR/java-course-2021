package markup;

import java.util.List;

public class TextWithInlines extends SimpleParent implements InlineMarkup {

    public TextWithInlines(List<InlineMarkup> children) {
        super(children);
    }
}
