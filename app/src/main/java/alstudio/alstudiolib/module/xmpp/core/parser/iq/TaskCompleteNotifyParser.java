package alstudio.alstudiolib.module.xmpp.core.parser.iq;

import com.alstudio.autils.log.ALLog;

import alstudio.alstudiolib.common.xml.parser.XmlParseCompleteCallback;
import alstudio.alstudiolib.module.xmpp.core.packact.ALIQ;

/**
 * Created by Alonso Lee on 2014/12/19.
 */
public class TaskCompleteNotifyParser extends BaseIQParser{


    boolean ida = false;
    @Override
    public void parseIQPackage(ALIQ packact, String stream, XmlParseCompleteCallback listener) throws Exception {
        ida = false;
        super.parseIQPackage(packact, stream, listener);
    }

    @Override
    public void processErrorStartTag(String tag) {

    }

    @Override
    public void processSetStartTag(String tag) {
        ALLog.getDefault().d("解析到set tag"+tag);

    }

    @Override
    public void processGetStartTag(String tag) {

    }

    @Override
    public void processResultStartTag(String tag) {

    }

    @Override
    public void processEndTag(String tag) {

    }

    @Override
    public void processStartDocument() {

    }

    @Override
    public void processEndDocument() {
        ALLog.getDefault().d("解析完毕");

    }

    @Override
    public void onParseCompleted() {
        ALLog.getDefault().d("解析完毕");
    }
}
