package alstudio.alstudiolib.module.xmpp.core.parser.iq;

/**
 * Created by Alonso Lee on 2014/12/19.
 */
public interface IQXmlParseEventCallback {

    public void processErrorStartTag(String tag);

    public void processSetStartTag(String tag);

    public void processGetStartTag(String tag);

    public void processResultStartTag(String tag);

    public void processEndTag(String tag);

    public void processStartDocument();

    public void processEndDocument();

}
