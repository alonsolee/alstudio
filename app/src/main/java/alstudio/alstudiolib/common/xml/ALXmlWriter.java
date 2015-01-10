package alstudio.alstudiolib.common.xml;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;


/**
 * XML写出
 * 
 * @author alonso lee
 * 
 */
public class ALXmlWriter {
	private OutputStreamWriter writer;

	public ALXmlWriter(final OutputStream out)
			throws UnsupportedEncodingException {
		writer = new OutputStreamWriter(out, "UTF-8");
	}

	synchronized public void close() {
		try {
			writer.close();
		} catch (IOException e) {
		}
	}

	synchronized public void write(String str) throws IOException {
		writer.write(str);
		writer.flush();
		str = null;
	}

};
