package alstudio.alstudiolib.module.xmpp.data;

import android.text.TextUtils;

import alstudio.alstudiolib.module.xmpp.ALXmppException;

/**
 * 此类封装了分发器返回的IM服务器ip地址、端口，域名；OCR服务器ip地址、端口； cardboxing客户端当前最新版本、下载地址、更新说明。
 * 所有方法简要说明： ALServerInfo: 构造函数 setIMServerIP: 设置IM服务器IP地址 getIMServerIP:
 * 设置IM服务器IP地址 setSocketPort: 设置IM服务器Socket端口 getSocketPort: 获取IM服务器Socket端口
 * setServerName: 设置IM服务器服务域名。不需要带"@" getServerName: 获取IM服务器服务域名 setWhatsNew:
 * 设定最新版本的更新说明 getWhatsNew: 获取最新版本的更新说明 setLatestVer: 设定当前最新的发行版本 getLatestVer:
 * 获取当前最新的发型版本号 setNewVersionUrl: 设定最新版本下载地址 getNewVersionUrl: 返回最新版本下载地址
 * 
 * @author alonso lee
 */
public class ALServerInfo {

	/* IM服务器IP地址 */
	private String imServerIP = "";
	/* Socket端口 */
	private String socketPort = "";
	/* 服务器名 */
	private String serverName = "mk";
	/* 当前最新发行版本号 */
	private String latestVer = "";
	/* 版本更新内容 */
	private String whatsNew = "";
	/* 新版本下载地址 */
	private String newVerUrl = "";

	/* 媒体服务器IP */
	private String mediaServerIp = "";
	/* 媒体服务器端口 */
	private String mediaServerPort = "";
	// 充值回调地址
	private String chargeCallbackUrl = "";
	// 报纸URL
	private String newsUrl = "";
	// 积分商城URL
	private String pointMarketUrl = "";
	// 是否强制更新
	private boolean forceUpdate = false;
	// 经济系统url
	private String extendcharge = "";
	// LBS url
	private String extendlbs = "";
	// 约会记录、附近的人、主页用户url
	private String extendother = "";

	private final String TAG = "ALServerInfo";

	/**
	 * ALServerInfo类构造函数
	 * 
	 * @author alonso lee
	 */
	public ALServerInfo() {

	}

	/**
	 * 设置IM服务器IP地址
	 * 
	 * @param ip
	 *            IM服务器IP地址
	 * @author alonso lee
	 */
	public void setIMServerIP(String ip) {
		if (TextUtils.isEmpty(ip)) {
            throw new ALXmppException("you input null param,im server ip set to default");
		}
		this.imServerIP = ip;
	}

	/**
	 * 获取IM服务器IP地址
	 * 
	 * @return IM服务器IP地址
	 * @author alonso lee
	 */
	public String getIMServerIP() {
		return this.imServerIP;
	}

	/**
	 * 设定媒体服务器IP地址
	 * 
	 * @param ip
	 *            媒体服务器IP地址
	 * @author alonso lee
	 */
	public void setMediaServerIp(String ip) {
		if (TextUtils.isEmpty(ip)) {
            throw new ALXmppException("you input null param,media server ip set to default");
		}
		this.mediaServerIp = ip;
	}

	/**
	 * 获取媒体服务器IP地址
	 * 
	 * @return 媒体服务器IP地址
	 * @author alonso lee
	 */
	public String getMediaServerIp() {
		return this.mediaServerIp;
	}

	/**
	 * 设定媒体服务器端口号
	 * 
	 * @param port
	 *            媒体服务器端口号
	 * @author alonso lee
	 */
	public void setMediaServerPort(String port) {
		if (TextUtils.isEmpty(port)) {
            throw new ALXmppException("you input null param,media server port set to default");
		}
		this.mediaServerPort = port;
	}

	/**
	 * 获取媒体服务器端口号
	 * 
	 * @return 媒体服务器端口号
	 * @author alonso lee
	 */
	public String getMediaServerPort() {
		return this.mediaServerPort;
	}

	/**
	 * 设置IM服务器Socket端口
	 * 
	 * @param port
	 *            IM服务器Socket端口
	 * @author alonso lee
	 */
	public void setIMSocketPort(String port) {
		if (TextUtils.isEmpty(port)) {
            throw new ALXmppException("you input null param,im server port set to default");
		}
		this.socketPort = port;
	}

	/**
	 * 获取IM服务器Socket端口
	 * 
	 * @return IM服务器Socket端口
	 * @author alonso lee
	 */
	public String getIMSocketPort() {
		return this.socketPort;
	}

	/**
	 * 设置IM服务器服务域名。不需要带"@"
	 * 
	 * @param serverName
	 *            IM服务器服务域名
	 * @author alonso lee
	 */
	public void setServerName(String serverName) {
		if (TextUtils.isEmpty(serverName)) {
            throw new ALXmppException("you input null param,im server name set to default");
		}
		this.serverName = serverName;
	}

	/**
	 * 获取IM服务器服务域名
	 * 
	 * @return IM服务器服务域名。不带"@"
	 * @author alonso lee
	 */
	public String getServerName() {
		return this.serverName;
	}

	/**
	 * 设定最新版本的更新说明
	 * 
	 * @param tips
	 *            更新说明
	 * @author alonso lee
	 */
	public void setWhatsNew(String tips) {
		this.whatsNew = tips;
	}

	/**
	 * 获取最新版本的更新说明
	 * 
	 * @return 更新说明
	 * @author alonso lee
	 */
	public String getWhatsNew() {
		return this.whatsNew;
	}

	/**
	 * 设定当前最新的发行版本
	 * 
	 * @param version
	 * @author alonso lee
	 */
	public void setLatestVer(String version) {
		this.latestVer = version;
	}

	/**
	 * 获取当前最新的发型版本号
	 * 
	 * @return 最新发行版本号
	 * @author alonso lee
	 */
	public String getLatestVer() {
		return this.latestVer;
	}

	/**
	 * 设定最新版本下载地址
	 * 
	 * @param url
	 *            最新版本下载地址
	 * @author alonso lee
	 */
	public void setNewVersionUrl(String url) {
		this.newVerUrl = url;
	}

	/**
	 * 返回最新版本下载地址
	 * 
	 * @return 最新版本下载地址
	 * @author alonso lee
	 */
	public String getNewVersionUrl() {
		return this.newVerUrl;
	}

	/**
	 * 设置新闻地址URL
	 * 
	 * @param u
	 *            新闻地址URL
	 * @author alonso lee
	 */
	public void setNewsUrl(String u) {
		if (TextUtils.isEmpty(u)) {
            throw new ALXmppException("you input null param,news url set to default");
		}
		this.newsUrl = u;
	}

	/**
	 * 获取新闻地址URL
	 * 
	 * @return 新闻地址URL
	 * @author alonso lee
	 */
	public String getNewsUrl() {
		return this.newsUrl;
	}

	/**
	 * 设置积分商城地址URL
	 * 
	 * @param u
	 *            积分商城地址URL
	 * @author ccx
	 */
	public void setPointMarketUrl(String u) {
		if (TextUtils.isEmpty(u)) {
            throw new ALXmppException("you input null param,point market set to default");
		}
		this.pointMarketUrl = u;
	}

	/**
	 * 获取积分商城地址URL
	 * 
	 * @return 积分商城地址URL
	 * @author ccx
	 */
	public String getPointMarketUrl() {
		return this.pointMarketUrl;
	}

	/**
	 * 设置充值地址URL
	 * 
	 * @param u
	 *            充值地址URL
	 * @author alonso lee
	 */
	public void setChargeCallbackUrl(String u) {
		if (TextUtils.isEmpty(u)) {
            throw new ALXmppException("you input null param,charge callback set to default");
		}
		this.chargeCallbackUrl = u;
	}

	/**
	 * 获取充值地址URL
	 * 
	 * @return 充值地址URL
	 * @author alonso lee
	 */
	public String getChargeCallbackUrl() {
		return this.chargeCallbackUrl;
	}

	/**
	 * 甚至是否需要强制更新
	 * 
	 * @param forceUpdate
	 *            true 需要强制更新
	 */
	public void setForceUpdateState(boolean forceUpdate) {
		this.forceUpdate = forceUpdate;
	}

	/**
	 * 检查是否需要强制更新
	 * 
	 * @return
	 */
	public boolean isForceUpdate() {
		return forceUpdate;
	}

	public void setExtendChargeUrl(String url) {
		if (TextUtils.isEmpty(url)) {
            throw new ALXmppException("you input null param,extend charger url set to default");
		}
		this.extendcharge = url;
	}

	public String getExtendChargeUrl() {
		return this.extendcharge;
	}

	public void setExtendLbs(String url) {
		if (TextUtils.isEmpty(url)) {
            throw new ALXmppException("you input null param,extend lbs url set to default");
		}
		this.extendlbs = url;
	}

	public String getExtendLbs() {
		return this.extendlbs;
	}

	public void setExtendOther(String url) {
		if (TextUtils.isEmpty(url)) {
            throw new ALXmppException("you input null param,extend other url set to default");
		}
		this.extendother = url;
	}

	public String getExtendOther() {
		return this.extendother;
	}

}
