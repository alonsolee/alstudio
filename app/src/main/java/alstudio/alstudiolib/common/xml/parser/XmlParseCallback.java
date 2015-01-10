package alstudio.alstudiolib.common.xml.parser;

/**
 * Created by Alonso Lee on 2014/12/18.
 */
public interface XmlParseCallback {
    public void onTagStart(String tag);

    public void onTagEnd(String tag);

    public void onDocumentStart();

    public void onDocumentEnd();

}
