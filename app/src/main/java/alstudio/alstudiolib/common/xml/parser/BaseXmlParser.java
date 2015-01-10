package alstudio.alstudiolib.common.xml.parser;

import android.text.TextUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * 最基本的封装的XML解析类（pull解析）
 * @author alonso lee
 *
 */
public class BaseXmlParser {

	private XmlPullParser mParser;

	private XmlParseCallback callback;

	/**
	 * 传入InputStream用于读取XML
	 * 
	 * @param in
	 * @throws org.xmlpull.v1.XmlPullParserException
	 */
	public void createParser(InputStream in) throws XmlPullParserException {
		if (in == null) {
			throw new IllegalArgumentException("inputstream should not null!");
		}
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		mParser = factory.newPullParser();
		mParser.setInput(in, null);
	}

	/**
	 * 传入XML字符串用于解析
	 *
	 * @param xml
	 * @throws org.xmlpull.v1.XmlPullParserException
	 */
    public void createParser(final String xml) throws XmlPullParserException {
		if (TextUtils.isEmpty(xml)) {
			throw new IllegalArgumentException("xml string should not null!");
		}

		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		mParser = factory.newPullParser();
		mParser.setInput(new StringReader(xml));
	}

	protected XmlPullParser getParser() {
		return mParser;
	}


	/**
	 * 设置解析回调
	 *
	 * @param callback
	 */
	protected void setXmlParseCallback(XmlParseCallback callback) {
		this.callback = callback;
	}

	/**
	 * 获取当前TAG名
	 *
	 * @return
	 */
	protected String getTagName() {
		return mParser.getName();
	}

	/**
	 * 忽略XML
	 *
	 * @throws org.xmlpull.v1.XmlPullParserException
	 * @throws java.io.IOException
	 */
	protected void skip() throws XmlPullParserException, IOException {
		if (mParser.getEventType() != XmlPullParser.START_TAG) {
			throw new IllegalStateException();
		}
		int depth = 1;
		while (depth != 0) {
			switch (next()) {
			case XmlPullParser.END_TAG:
				depth--;
				break;
			case XmlPullParser.START_TAG:
				depth++;
				break;
			}
		}
	}

	/**
	 * 读取节点内容
	 *
	 * @return
	 * @throws org.xmlpull.v1.XmlPullParserException
	 * @throws java.io.IOException
	 */
	public String readText() throws XmlPullParserException, IOException {
		String result = "";
		if (next() == XmlPullParser.TEXT) {
			result = mParser.getText();
			mParser.nextTag();
		}
		return result;
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
		ArrayList<String> keys = new ArrayList<String>();
		mParser.require(XmlPullParser.START_TAG, null, tag);
		int i = mParser.getAttributeCount();
		for (int j = 0; j < i; j++) {
			keys.add(j, mParser.getAttributeName(j));
		}
		return keys;
	}

	/**
	 * 获取属性内容
	 *
	 * @param att
	 * @return
	 */
	public String getAttValue(String att) {
		if (TextUtils.isEmpty(att)) {
			throw new IllegalArgumentException("att should not null");
		}
		String value = "";
		value = mParser.getAttributeValue(null, att);

		return value;
	}

	/**
	 * 开启解析器
	 *
	 * @throws org.xmlpull.v1.XmlPullParserException
	 * @throws java.io.IOException
	 */
	public void startParse() throws XmlPullParserException, IOException {
		String tag = "";

		int eventType = mParser.getEventType();

		while (true) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				if (callback != null) {
					callback.onDocumentStart();
				}
				break;
			case XmlPullParser.END_DOCUMENT:

				if (callback != null) {
					callback.onDocumentEnd();
				}
				return;
			case XmlPullParser.START_TAG:
				tag = getTagName();
				if (!TextUtils.isEmpty(tag)) {
					if (callback != null) {
						callback.onTagStart(tag);
					}
				}
				break;
			case XmlPullParser.END_TAG:
				tag = getTagName();
				if (callback != null) {
					callback.onTagEnd(tag);
				}
				break;
			}
			eventType = next();
		}

	}

	protected int next() throws XmlPullParserException, IOException {
		int ret = mParser.next();
		return ret;
	}

}
