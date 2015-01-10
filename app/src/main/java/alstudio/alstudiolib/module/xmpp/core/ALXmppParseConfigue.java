package alstudio.alstudiolib.module.xmpp.core;

import java.util.HashMap;


import alstudio.alstudiolib.common.log.ALLog;
import alstudio.alstudiolib.module.xmpp.core.packact.ALIQ;
import alstudio.alstudiolib.module.xmpp.core.parser.iq.IQPackageCallback;

/**
 * 本类用于注册IQ,ALMessage、Presence解析器。
 * 
 * @author alonso lee
 * 
 */
public class ALXmppParseConfigue {

	/**
	 * 用于注册IQ流解析器
	 */
	private HashMap<String, IQPackageCallback> map = new HashMap<String, IQPackageCallback>();

	public ALXmppParseConfigue() {
		registerIQParseMethod();
	}

	/**
	 * 注册指定命名空间的IQ解析器
	 */
	public void registerIQParseMethod() {

	}

	public void registerMessageParseMethod() {

	}

	public void registerPresenceParseMethod() {

	}

	public synchronized void getIQParser(ALIQ packact, String stream,
			ALXmppEventListener listener) {
		if (map.get(packact.getNameSpace()) != null) {
            IQPackageCallback callback = map.get(packact.getNameSpace());
			try {
				callback.parseIQPackage(packact, stream, listener);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

//	public synchronized void getMessageParser(ALMessage msg, String stream,
//			ALXmppEventListener listener) {
//		ALMessageParser parser = new ALMessageParser(msg, stream, listener);
//		parser.startParseALMessage();
//	}
//
//	public synchronized void getALPresenceParser(Presence presence,
//			String stream, ALXmppEventListener listener) {
//		ALPresenceParser parser = new ALPresenceParser(presence, stream,
//				listener);
//		parser.startParsePresence();
//	}

}
