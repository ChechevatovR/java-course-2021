package markup;

public class Text implements MarkElement {
    protected final String text;

    public Text(String text) {
        this.text = text;
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        sb.append(this.text);
    }

    @Override
    public void toHtml(StringBuilder sb) {
        sb.append(this.text);
    }


}
