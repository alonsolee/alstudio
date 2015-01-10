package alstudio.alstudiolib.module.xmpp.core.parser.iq;

import alstudio.alstudiolib.common.xml.parser.XmlParseCompleteCallback;
import alstudio.alstudiolib.module.xmpp.core.packact.ALIQ;

/**
 * Created by Alonso Lee on 2014/12/19.
 */
public interface IQPackageCallback {
    void parseIQPackage(ALIQ packact, String stream, XmlParseCompleteCallback listener)
            throws Exception;
}
