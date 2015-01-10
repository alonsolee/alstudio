/**
 * 
 */
package alstudio.alstudiolib.module.xmpp.core.packact;

import java.util.HashMap;

/**
 * 
 * 配置XMPP用到的域名。通过命名空间来查找
 * 
 * @author alonso lee
 * 
 */
public class ALXmppDomainConfig {

	private HashMap<String, String> domains;
	private static ALXmppDomainConfig instance;

	public static ALXmppDomainConfig getALXmppDomainConfig() {
		if (instance == null) {
			instance = new ALXmppDomainConfig();
		}
		return instance;
	}

	/**
	 * 
	 */
	public ALXmppDomainConfig() {
		// TODO Auto-generated constructor stub
		initDomains();
	}

	/**
	 * 传入命名空间，获取域名
	 * 
	 * @param ns
	 * @return
	 */
	public String getDomainByNameSpace(String ns) {
		return domains.get(ns);
	}

	private void initDomains() {
		domains = new HashMap<String, String>();
		registerDomain();
	}

	private void registerDomain() {

	}
}
