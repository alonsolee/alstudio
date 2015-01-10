package alstudio.alstudiolib.common.xml.generator;

import java.io.IOException;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * XML拼装类
 * @author Alonso Lee
 *
 */
public class XMLGenerator {
	//java当中的堆栈类（后进先出）
	private Stack tags;

	private boolean inside_tag;
	
	private StringBuffer buf = new StringBuffer();
	
	private final String TAG = "XMLGenerator";
	
	
	public XMLGenerator() {
		// TODO Auto-generated constructor stub
		this.tags = new Stack();
		this.inside_tag = false;
	}
	
	synchronized public void startTag(final String tag) throws IOException {//这里的方法当然要加锁啦
		if (this.inside_tag) {
			buf.append('>');
		}


		buf.append('<');
		buf.append(tag);
		
		this.tags.push(tag);
		this.inside_tag = true;
	}

	synchronized public void attribute(final String atr, final String value) throws IOException {
		if (value == null) { return; }
		
		buf.append(' ');
		buf.append(atr);
		buf.append("=\'");
		writeEscaped(value);
		buf.append('\'');
		
	}

	synchronized public void endTag() throws IOException {
		try {
			final String tagname = (String) this.tags.pop();
			if (this.inside_tag) {
				buf.append("/>");
				this.inside_tag = false;
			} else {

				buf.append("</");
				buf.append(tagname);
				buf.append('>');
			
			}
		} catch (final EmptyStackException e) {
		}
	}

	synchronized public void text(final String str) throws IOException {
		if (this.inside_tag) {
			buf.append('>');
			this.inside_tag = false;
		}
		this.writeEscaped(str);
	}

	synchronized private void writeEscaped(final String str) throws IOException {
		
		for (int i = 0; i < str.length(); i++) {
			final char c = str.charAt(i);
			switch (c) {
				case '<':
					buf.append("&lt;");
					continue;
				case '>':
					buf.append("&gt;");
					continue;
				case '&':
					buf.append("&amp;");
					continue;
				case '\'':
					buf.append("&apos;");
					continue;
				case '"':
					buf.append("&quot;");
					continue;
				default:
				{
					buf.append(c);
					continue;
				}
			}
		}
	}
	
	/**
	 * 得到拼凑的XML
	 * @return XML
	 */
	public String getXml()
	{
		return buf.toString();
	}

    public void release(){
        buf.setLength(0);
        buf = null;
        if(tags != null){
            tags.clear();
        }
        tags = null;
    }

    public void clearString(){
        buf.setLength(0);
    }
}
