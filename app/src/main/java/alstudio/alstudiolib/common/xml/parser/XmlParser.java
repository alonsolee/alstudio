package alstudio.alstudiolib.common.xml.parser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Alonso Lee on 2014/12/19.
 */
public class XmlParser extends BaseXmlParser {

    private XmlParseCallback mCallback;

    public XmlParser(String xml, XmlParseCallback callback)
            throws XmlPullParserException {
        // TODO Auto-generated constructor stub
        mCallback = callback;
        createParser(xml);
    }

    public XmlParser(InputStream xml, XmlParseCallback callback)
            throws XmlPullParserException {
        // TODO Auto-generated constructor stub
        mCallback = callback;
        createParser(xml);
    }

    public void start() throws XmlPullParserException, IOException {
        setXmlParseCallback(mCallback);
        startParse();
    }


}
