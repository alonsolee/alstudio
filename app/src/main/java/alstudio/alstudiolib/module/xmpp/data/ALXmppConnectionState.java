package alstudio.alstudiolib.module.xmpp.data;

/**
 * 用于描述当前XMPP连接状态
 * 
 * @author alonso lee
 * 
 */
public enum ALXmppConnectionState {

	// 缺省状态与服务器连接断开状态
	XMPP_STATE_IDLE,
	// 正在打开socket，
	XMPP_STATE_CONNECTING_SOCKET,
	// socket连接已经建立
	XMPP_STATE_SOCKET_CONNECTED,
	// 正在打开流
	XMPP_STATE_OPENNING_STREAM,
	// 正在与服务器做协议交互
	XMPP_STATE_CHECKING_LOGIN_PROTOCOL,
	// 已经登录服务器
	XMPP_STATE_CONNECTION_ESTABLISHED;

}
