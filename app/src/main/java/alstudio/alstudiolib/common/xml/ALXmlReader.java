package alstudio.alstudiolib.common.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Stack;

/**
 * XML流读取
 * 
 * @author alonso lee
 */
public class ALXmlReader {
	private InputStream is;

	public final static int START_DOCUMENT = 0;

	public final static int END_DOCUMENT = 1;

	public final static int START_TAG = 2;

	public final static int END_TAG = 3;

	public final static int TEXT = 4;

	private Stack<String> tags;

	private boolean inside_tag;

	private String tagName;

	private String text;

	private final Hashtable<String, String> attributes = new Hashtable<String, String>();

	private int c;

	private int type = START_DOCUMENT;

	public StringBuffer buf = new StringBuffer();

	private final String TAG = "XmlReader";

	public ALXmlReader(final InputStream in) throws IOException {
		// reader = new InputStreamReader(in, "UTF-8");
		this.is = in;
		this.tags = new Stack<String>();
		this.inside_tag = false;
	}

	public int getNextCharacter() throws IOException {
		int a = is.read();
		int t = a;

		if ((t | 0xC0) == t) {
			int b = is.read();
			if (b == 0xFF) {
				t = -1;
			} else if (b < 0x80) {
				if (c == -1) {
					throw new IOException("Bad UTF-8 Encoding encountered");
				}
			} else if ((t | 0xE0) == t) {
				int c = is.read();
				if (c == 0xFF) {
					t = -1;
				} else if (c < 0x80) {
					if (c == -1) {
						throw new IOException("Bad UTF-8 Encoding encountered");
					}
				} else
					t = ((a & 0x0F) << 12) | ((b & 0x3F) << 6) | (c & 0x3F);
			} else
				t = ((a & 0x1F) << 6) | (b & 0x3F);
		}

		buf.append((char) t);

		return t;
	}

	public void close() {
		try {
			is.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * try { reader.close(); } catch (IOException e) {}
		 */
	}

	public int next() throws IOException {

		/* alonso 添加此条语句。t用来保存c的前一个值 */
		int t = this.c;
		this.c = getNextCharacter();

		if (this.c < ' ') {
			while (((this.c = getNextCharacter()) < ' ') && (this.c != -1)) {
				;
			}
		}
		if (this.c == -1) {
			this.type = END_DOCUMENT;

			return this.type;
		}

		// alonso 2011-5-12 修改了入口条件。必须前一个字符为'<'且当前字符为'/'的时候才表示当前
		// 语句是节点结束符</>！！此条件修正了文本消息或者多媒体消息的消息内容以'/'开头会导致解析器
		// 误以为此消息内容描述的节点要结束！解析器开始循环寻找下一个以此消息内容为结束符的操作，因为
		// 此消息内容不是有效的节点名，所以解析器肯定无法寻找到另外一个同内容的节点，因此，解析器将一直
		// 处于死循环状态，无法对其他消息进行解析！！
		if ((this.c == '<')
				|| ((t == '<') && (this.c == '/') && !this.inside_tag)) {
			this.inside_tag = true;
			// reset all
			this.tagName = null;
			this.text = null;
			this.attributes.clear();

			if (this.c == '<') {
				this.c = getNextCharacter();
			}

			if (this.c == '/') {

				this.type = END_TAG;
				this.c = getNextCharacter();

				this.tagName = this.readName('>');
			} else if ((this.c == '?') || (this.c == '!')) {// ignore xml
															// heading & //
															// comments
				while ((this.c = getNextCharacter()) != '>') {
					;
				}
				this.next();
			} else {
				this.type = START_TAG;
				this.tagName = this.readName(' ');

				String attribute = "";
				String value = "";
				while (this.c == ' ') {
					this.c = getNextCharacter();

					attribute = this.readName('=');

					int quote = getNextCharacter();// this.c = this.read(); //
													// '''
					this.c = getNextCharacter();

					value = this.readText(quote); // change from value =
													// this.readText(''');
					this.c = getNextCharacter();

					this.attributes.put(attribute, value);
				}
				if (this.c != '/') {
					this.inside_tag = false;
				}
			}
		} else if ((this.c == '>') && this.inside_tag) // last tag ended
		{
			this.type = END_TAG;
			this.inside_tag = false;

		} else {
			this.tagName = null;
			this.attributes.clear();

			this.type = TEXT;
			this.text = this.readText('<');
		}

		return this.type;
	}

	public int getType() {
		return this.type;
	}

	public String getName() {
		return this.tagName;
	}

	public String getAttribute(final String name) {
		return (String) this.attributes.get(name);
	}

	public Enumeration<String> getAttributes() {
		return this.attributes.keys();
	}

	public String getText() {
		return this.text;
	}

	private String readText(final int end) throws IOException {
		final StringBuffer output = new StringBuffer("");
		while (this.c != end) {
			if (this.c == '&') {
				this.c = getNextCharacter();
				switch (this.c) {
				case 'l':
					output.append('<');
					break;
				case 'g':
					output.append('>');
					break;
				case 'a':
					if (getNextCharacter() == 'm') {
						output.append('&');
					} else {
						output.append('\'');
					}
					break;
				case 'q':
					output.append('"');
					break;
				case 'n':
					output.append(' ');
					break;
				default:
					output.append('?');
				}

				while ((this.c = getNextCharacter()) != ';') {
					;
				}
			} else if (this.c == '\\') {
				if ((this.c = getNextCharacter()) == '<') {
					break;
				} else {
					output.append((char) this.c);
				}
			} else {
				if (this.c == '\r') {
					output.append("\n");
				} else if (this.c == '\n') {
					output.append("\n");
				} else {
					output.append((char) this.c);
				}
			}
			this.c = getNextCharacter();
		}

		return output.toString();
	}

	private String readName(final int end) throws IOException {
		final StringBuffer output = new StringBuffer("");
		do {
			output.append((char) this.c);
		} while (((this.c = getNextCharacter()) != end) && (this.c != '>')
				&& (this.c != '/'));

		return output.toString();
	}

};
