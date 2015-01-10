package alstudio.alstudiolib.module.xmpp.core.parser.iq;

import alstudio.alstudiolib.common.xml.parser.CommonXmlParser;
import alstudio.alstudiolib.common.xml.parser.XmlParseCompleteCallback;
import alstudio.alstudiolib.module.xmpp.core.packact.ALIQ;
import alstudio.alstudiolib.module.xmpp.core.packact.ALIQType;

/**
 * 基础IQ解析器。必须传入ALIQ对象。解析器从该对象获取到IQ类型，根据IQ类型分发到不同的回调。请注意，如果是ERROR类型的iq，错误码code已经缺省获取
 * Created by Alonso Lee on 2014/12/19.
 */
public abstract class BaseIQParser extends CommonXmlParser implements IQXmlParseEventCallback, IQPackageCallback {

    private ALIQType mType;
    public int code = 0;
    protected final String ERROR_CODE_TAG = "error";


    @Override
    public void parseIQPackage(ALIQ packact, String stream, XmlParseCompleteCallback listener) throws Exception {
        mType = packact.getType();
        code = 0;
        create(stream);
        start();
    }

    @Override
    public void onTagStart(String tag) {
        switch (mType) {
            case ERROR:
                if (ERROR_CODE_TAG.equals(tag)) {
                    code = Integer.parseInt(getAttValue("code"));
                }
                processErrorStartTag(tag);
                break;
            case GET:
                processGetStartTag(tag);
                break;
            case SET:
                processSetStartTag(tag);
                break;
            case RESULT:
                processResultStartTag(tag);
                break;
        }
    }

    @Override
    public void onTagEnd(String tag) {
        processEndTag(tag);
    }

    @Override
    public void onDocumentStart() {
        processStartDocument();
    }

    @Override
    public void onDocumentEnd() {
        processEndDocument();
    }
}
