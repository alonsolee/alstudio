/**
 * 
 */
package alstudio.alstudiolib.module.xmpp;

/**
 * 
 * XMPP 模块异常类
 * 
 * @author alonso lee
 * 
 */
public class ALXmppException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6090788017070332420L;

	public ALXmppException(String detailMessage) {
		super(detailMessage);
	}

	public ALXmppException(Throwable throwable) {
		super(throwable);
	}

	public ALXmppException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

}
