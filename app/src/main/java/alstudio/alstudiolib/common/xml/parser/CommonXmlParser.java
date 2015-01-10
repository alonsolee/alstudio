package alstudio.alstudiolib.common.xml.parser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * 通用xml解析类。可通过此类派生具体业务的xml解析器
 * <p/>
 * Created by Alonso Lee on 2014/12/19.
 */
public abstract class CommonXmlParser implements XmlParseCallback,XmlParseCompleteCallback {

    private XmlParser mParser;

    /**
     * 创建解析器
     *
     * @param xml
     * @throws XmlPullParserException
     */
    public void create(String xml) throws XmlPullParserException {
        mParser = new XmlParser(xml, this);
    }


    /**
     * 创建解析器
     *
     * @param in
     * @throws XmlPullParserException
     */
    public void create(InputStream in) throws XmlPullParserException {
        mParser = new XmlParser(in, this);
    }


    public void start() throws XmlPullParserException, IOException {
        if (mParser == null) {
            throw new IllegalAccessError("please call create method first");
        }
        mParser.start();
    }

    /**
     * 读取节点内容
     *
     * @return
     * @throws IOException
     * @throws XmlPullParserException
     */
    public String readText() throws IOException, XmlPullParserException {
        return mParser.readText();
    }

    /**
     * 获取属性内容
     *
     * @param att
     * @return
     */
    public String getAttValue(String att) {
        return mParser.getAttValue(att);
    }

    /**
     * 获取节点所有属性
     *
     * @param tag
     * @return
     * @throws org.xmlpull.v1.XmlPullParserException
     * @throws java.io.IOException
     */
    public ArrayList<String> getAttributes(final String tag)
            throws XmlPullParserException, IOException {
        return mParser.getAttributes(tag);
    }

}
