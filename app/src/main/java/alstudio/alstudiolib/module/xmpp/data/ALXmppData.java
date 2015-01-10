package alstudio.alstudiolib.module.xmpp.data;

/**
 * 
 * @author alonso lee
 * 
 */
public class ALXmppData {

	// 本次连接创建类型为登陆服务器
	public static final int MODE_LOGIN = 1000;
	// 本次连接创建类型为注册
	public static final int MODE_REGISTER = 1001;
	// 第三方登陆
	public static final int MODE_THIRD_PART_LOGIN = 1002;
	// 檢測email
	public static final int MODE_CHECK_EMAIL = 1003;

	// 登录类型
	public static final int LOGIN_TYPE_SYSTEM = 1;
	public static final int LOGIN_TYPE_FACEBOOK = 2;
	public static final int LOGIN_TYPE_TWITTER = 3;
	public static final int LOGIN_TYPE_SINA = 4;
	public static final int LOGIN_TYPE_TENCENT = 5;
	public static final int LOGIN_TYPE_WEIXIN = 6;
	public static final int LOGIN_TYPE_PHONE = 7;

	// 服务器iq地址
	private String host;
	// 服务器端口
	private String port;
	// 用户名
	private String username;
	// 密码
	private String password;
	// 域名
	private String domain = "mk";
	// 验证类型
	private String authType = "system";
	// 网络类型
	private String netType = "WIFI";

	// 更改密码后的新密码
	private String newPwd = "";
	// 资源名
	private String resource;
	//
	private int priority = 0;
	// 密钥。强烈建议密钥用jni获得
	public final String DES_KEY = "";
	// 客户端设备信息
	private ALDeviceInfo mDevInfo;
	// 客户端版本号
	private String appVersion;
	// 渠道
	private String downloadUrl = "";

	// 第三方登陆类型
	private int third_part_type;
	// 第三方uid
	private String third_part_uid;

	private String imei;

	private String email;

	private String uuid;

	private Object data;
	
	private String verifyCode;
	// 微信unionid
	private String third_part_weixin_union;

	/*
	 * 此变量标示设定本次连接的目的 取值范围为0-2 0---------登录(缺省) 1---------注册
	 * 2---------检测本机是否已经注册
	 */
	private int conMode = MODE_LOGIN;
	
	public String jid;

	/**
	 * 
	 */
	public ALXmppData() {
		// TODO Auto-generated constructor stub
	}

	public ALXmppData(String host, String port, String domain, int priority) {
		this.host = host;
		this.port = port;
		this.domain = domain;
	}

	/**
	 * 传递账号密码，构造XMPP data
	 * 
	 * @param user
	 *            用户名
	 * @param pwd
	 *            密码
	 */
	public ALXmppData(String user, String pwd) {
		this.username = user;
		this.password = pwd;
	}

	public ALXmppData(String host, String port, String domain, int priority,
			String user, String pwd) {
		this.host = host;
		this.port = port;
		this.domain = domain;
		this.username = user;
		this.password = pwd;
	}

	/**
	 * 设定IM服务器地址
	 * 
	 * @param host
	 *            im服务器地址
	 * @author alonso lee
	 */
	public void setHost(String host) {
		if (isStringInvalid(host)) {
			// 传入的参数不合法
			return;
		}
		this.host = host;
	}

	/**
	 * 获取IM服务器地址
	 * 
	 * @return im服务器地址
	 * @author alonso lee
	 */
	public String getHost() {
		return this.host;
	}

	/**
	 * 设定IM服务器端口
	 * 
	 * @param port
	 *            im服务器端口
	 * @author alonso lee
	 */
	public void setPort(String port) {
		if (isStringInvalid(port)) {
			// 传入的参数不合法
			return;
		}
		this.port = port;
	}

	/**
	 * 获取IM服务器端口
	 * 
	 * @return im服务器端口
	 * @author alonso lee
	 */
	public String getPort() {
		return this.port;
	}

	/**
	 * 设定域名
	 * 
	 * @param domain
	 *            域名
	 * @author alonso lee
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * 获取域名
	 * 
	 * @return 域名
	 * @author alonso lee
	 */
	public String getDomain() {
		return this.domain;
	}

	/**
	 * 设定用户名
	 * 
	 * @param name
	 *            用户名
	 * @author alonso lee
	 */
	public void setUsername(String name) {
		this.username = name;
	}

	/**
	 * 获取用户名
	 * 
	 * @return 用户名
	 * @author alonso lee
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * 设定密码
	 * 
	 * @param pwd
	 *            密码
	 * @author alonso lee
	 */
	public void setPassword(String pwd) {
		this.password = pwd;
	}

	/**
	 * 获取密码
	 * 
	 * @return 密码
	 * @author alonso lee
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * 设定新密码
	 * 
	 * @param newPwd
	 *            新密码
	 * @author alonso lee
	 */
	public void setNewPassword(String newPwd) {
		this.newPwd = newPwd;
	}

	/**
	 * 获取新密码
	 * 
	 * @return 新密码
	 * @author alonso lee
	 */
	public String getNewPassword() {
		return this.newPwd;
	}

	/**
	 * 设定本次连接模式
	 * 
	 * @param mode
	 *            取值范围为0-2。对应的模式为 0---------登录(缺省) 1---------注册
	 *            2---------检测本机是否已经注册 如设定的值超出范围，将自动设为缺省模式
	 * @author alonso lee
	 */
	public void setConnectionMode(int mode) {
		if (mode > 4) {
			return;
		}
		conMode = mode;
	}

	/**
	 * 获取本次连接模式
	 * 
	 * @return 本次连接模式
	 * @author alonso lee
	 */
	public int getConnectionMode() {
		return this.conMode;
	}

	/**
	 * 设定资源名
	 * 
	 * @param resource
	 *            资源名
	 * @author alonso lee
	 */
	public void setResource(String resource) {
		this.resource = resource;
	}

	/**
	 * 获取资源名
	 * 
	 * @return 资源名
	 * @author alonso lee
	 */
	public String getResource() {
		return this.resource;
	}

	/**
	 * 设定优先级
	 * 
	 * @param priority
	 *            优先级
	 * @author alonso lee
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	/**
	 * 获取优先级
	 * 
	 * @return 优先级
	 * @author alonso lee
	 */
	public int getPriority() {
		return this.priority;
	}

	/**
	 * 设定账号验证类型
	 * 
	 * @param authType
	 * @author alonso lee
	 */
	public void setAuthType(String authType) {
		this.authType = authType;
	}

	/**
	 * 获取账号验证类型
	 * 
	 * @return
	 * @author alonso lee
	 */
	public String getAuthType() {
		return this.authType;
	}

	/**
	 * 设定客户端手机系统信息
	 * 
	 * @param info
	 *            客户端手机系统信息
	 * @author alonso lee
	 */
	public void setDevInfo(ALDeviceInfo info) {
		this.mDevInfo = info;
	}

	/**
	 * 获取客户端手机系统信息
	 * 
	 * @return 客户端手机系统信息
	 * @author alonso lee
	 */
	public ALDeviceInfo getDevInfo() {
		return this.mDevInfo == null ? new ALDeviceInfo() : this.mDevInfo;
	}

    /**
     * 设置客户端当前网络类型
     * @param type
     */
	public void setNetType(String type) {
		this.netType = type;
	}

    /**
     * 获取客户端当前网络类型
     * @return
     */
	public String getNetType() {
		return this.netType;
	}

	/**
	 * @param appVersion
	 *            the appVersion to set
	 */
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	/**
	 * @return the appVersion
	 */
	public String getAppVersion() {
		return appVersion;
	}

	/**
	 * 测试传入的字符串是否无效。如果传入的字符串 为null，或者长度为0，则视为无效
	 * 
	 * @param str
	 *            待测试的字符串数据
	 * @return true表示输入的字符串无效
	 * @author alonso lee
	 */
	private boolean isStringInvalid(String str) {
		if (str == null) {
			return true;
		}

		if (str.length() == 0) {
			return true;
		}

		return false;
	}

	/**
	 * @param third_part_type
	 *            the third_part_type to set
	 */
	public void setThirdPartType(int third_part_type) {
		this.third_part_type = third_part_type;
	}

	/**
	 * @return the third_part_type
	 */
	public int getThirdPartType() {
		return third_part_type;
	}

	/**
	 * @param third_part_uid
	 *            the third_part_uid to set
	 */
	public void setThirdPartUid(String third_part_uid) {
		this.third_part_uid = third_part_uid;
	}

	/**
	 * @return the third_part_uid
	 */
	public String getThirdPartUid() {
		return third_part_uid;
	}

	/**
	 * @param downloadUrl
	 *            the downloadUrl to set
	 */
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	/**
	 * @return the downloadUrl
	 */
	public String getDownloadUrl() {
		return downloadUrl;
	}

	/**
	 * @param imei
	 *            the imei to set
	 */
	public void setImei(String imei) {
		this.imei = imei;
	}

	/**
	 * @return the imei
	 */
	public String getImei() {
		return imei;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param uuid
	 *            the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}
	
	/**
	 * @return the verifyCode
	 */
	public String getVerifyCode() {
		return verifyCode;
	}
	
	/**
	 * @param verifyCode the verifyCode to set
	 */
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	
	/**
	 * @return the third_part_weixin_union
	 */
	public String getThird_part_weixin_union() {
		return third_part_weixin_union;
	}
	
	/**
	 * @param third_part_weixin_union the third_part_weixin_union to set
	 */
	public void setThird_part_weixin_union(String third_part_weixin_union) {
		this.third_part_weixin_union = third_part_weixin_union;
	}
}
