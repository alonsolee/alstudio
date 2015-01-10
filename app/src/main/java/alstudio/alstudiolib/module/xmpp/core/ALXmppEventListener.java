package alstudio.alstudiolib.module.xmpp.core;


import alstudio.alstudiolib.common.xml.parser.XmlParseCompleteCallback;
import alstudio.alstudiolib.module.xmpp.data.ALXmppConnectionState;

/**
 * 此接口用于获取XMPP库相关的事件和数据
 * 
 * @author alonso lee
 * 
 */
public interface ALXmppEventListener extends XmlParseCompleteCallback {

	/**
	 * 连接丢失或者连接失败时，此回调将被调用
	 * 
	 * @param reason
	 *            连接丢失或者连接失败的原因
	 * @author alonso lee
	 */
	public void onConnectLostEvent(final String reason);

	/**
	 * 连接冲突或者连接失败时，此回调将被调用
	 * 
	 * @param reason
	 *            连接丢失原因
	 * @param conflictSid
	 *            流冲突ID
	 */
	public void onConnectLostEvent(final String reason, final String conflictSid);

	/**
	 * 登录成功后，此回调将被调用
	 * 
	 * @param jid
	 *            成功登录的jid
	 * @param pwd
	 *            jid对应的密码
	 * @author alonso lee
	 */
	public void onLoginedEvent(final String jid, final String pwd, String token, int version, boolean mustupdate);

	/**
	 * 成功登出后，此回调将被执行
	 * 
	 * @author alonso lee
	 */
	public void onLogoutEvent();

	/**
	 * 当账号验证失败时，此回调将被执行
	 * 
	 * @author alonso lee
	 */
	public void onAuthFailedEvent(final String msg);

	/**
	 * 当连接IM服务器状态发生变化时，此回调将被执行
	 * 
	 * @param newState
	 *            当前状态
	 * @param oldState
	 *            上一状态
	 * @author alonso lee
	 */
	public void onConnectStateChangedEvent(
            final ALXmppConnectionState newState,
            final ALXmppConnectionState oldState);

	/**
	 * 更新session id
	 * 
	 * @param sid
	 */
	public void onSessionIdUpdateEvent(String sid);


}
