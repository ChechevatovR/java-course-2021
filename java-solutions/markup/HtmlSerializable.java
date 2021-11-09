package markup;

public interface HtmlSerializable extends MaybeSerializable {
    void toHtml(StringBuilder sb);
}
