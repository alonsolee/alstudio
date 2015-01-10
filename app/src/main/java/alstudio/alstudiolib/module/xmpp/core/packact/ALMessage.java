package alstudio.alstudiolib.module.xmpp.core.packact;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;



/**
 * Message现有6种类型:文本消息、共享语音、共享地理位置、共享联系人、共享图片、语音表情。请根据不同 的消息类型设定相应的属性。
 * 关于消息的id号，其产生的方式是：msgId初始值为null,可以通过setMsgId方法来设置id号。也可以由getMsgId方法
 * 主动生成id号。在底层发送此条消息时将会调用getMsgId获取id号。getMsgId方法中首先查询msgId是否为null，如不是
 * 则直接返回msgId
 * ,否则调用randomString方法生成一个随机id号。randomString生成的随机id号由"random"和"msgNum"变量
 * 以及numbersAndLetters数组拼凑而成
 * 。random以Message实例化时取得的当前时间为基准。msgNum则是一个静态变量，随着Message 的实例化而递增。
 * 
 * @author alonso lee
 * 
 */
public class ALMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	/* 此条消息的发送者。请注意，是jid */
	private String from = "";
	/* 此条消息的接受者。请注意，是jid */
	private String to = "";

	/* 此条消息的发送者的昵称 */
	private String fromNick = "";
	/* 此条消息接受者的昵称 */
	private String toNick = "";

	/* 本条消息的id号。用来区分不同的消息 */
	private String msgId;
	/* 消息主题 */
	private String subject = "";
	/* 消息内容 */
	private String body = "";
	/* 此条留言收到或者发出的时间 */
	private Date time = new Date();
	/* 本条消息的类型。取值范围请参考消息类型常量 */
	private int type;

	/* 此值用于共享图片和共享语音，代表此条多媒体附件在媒体服务器上的id号 */
	private String fileId = "";
	/* 此值用于共享图片和共享语音,代表此多媒体附件的文件名 */
	private String fileName = "";
	/* 此值用于共享图片和共享语音,代表此多媒体附件的大小 */
	private String fileSize = "";
	/* 此值用于共享图片和共享语音，代表此多媒体附件在SD卡中的位置 */
	private String filePath = "";
	/* 此值仅用于共享语音，代表此条语音消息的时长 */
	private String recLen = "";
	/* 此值仅用于共享图片，代表此张图片的缩略图数据 */
	private String bobData = "";
	private boolean isSent = false;
	private boolean isDelivered = false;
	private boolean isDisplayed = false;

	/* 此列表用于群发。当此值不为null时代表此条消息需要群发 */
	private LinkedList<String> cc;

	/* 随机数发送器。用于产生消息的本条id号 */
	private Random random;
	/*  */
	private static long msgNum;

	/* 用于标识哪种通话类型 */
	public static final int COST_TYPE_NORMAL = 0;
	public static final int COST_TYPE_BAOMIHUA = 1;

	/* 消息类型 */
	public static final int TEXT_MSG = 0; // 文本消息
	public static final int AUDIO_MSG = 1; // 共享语音
	public static final int POSITION_MSG = 2; // 共享地理位置
	public static final int IMAGE_MSG = 3; // 共享图片
	public static final int CONTACT_MSG = 4; // 共享联系人
	public static final int AUDIO_FACE_MSG = 5; // 语音表情消息
	public static final int SYSTEM = 6; // 系统消息
	public static final int RECHARGE = 7; // 充值消息
	public static final int LEAVE = 8; // 离线留言
	public static final int NEW_INCOMMING_CALL = 9; // 新来电
	public static final int USER_ACCEPT_CALL = 10; // 对方已接听
	public static final int USER_REJECT_CALL = 11; // 拒绝接听
	public static final int USER_HANDUP_CALL = 12; // 对方已挂断
	public static final int MAKE_CALL = 13; // 撥打電話
	public static final int CANCEL_CALL = 14; // 取消電話
	public static final int ACCEPT_CALL = 15; // 接聽電話
	public static final int REJECT_CALL = 16; // 拒絕電話
	public static final int HANDUP_CALL = 17; // 掛斷電話
	public static final int USER_VOIP_ADDR_SET = 18; // 对方ip变化
	public static final int FAVORITE_MSG = 19; // 被收藏消息
	public static final int FAVORITE_DELETED_MSG = 20; // 被取消收藏消息
	public static final int FANS_DELETED_MSG = 21; // 粉丝被删除
	public static final int AUDIO_LEAVE = 22; // 语音留言
	public static final int CALL_IMAGE_MSG = 23; // 通话中发送的消息
	public static final int REMIND_CALL = 24; // 来电提醒
	public static final int RECEV_FLOWER = 25;	// 送花消息
	public static final int MARQUEE = 26;	//跑马灯消息
	public static final int SWITCHSTATUS = 27;	//跑马灯开关消息

	public static final int STATE_FAILED = 2;
	public static final int STATE_DELIVERED = 3;
	public static final int STATE_SENDING = 4;
	public static final int STATE_IDLE = 5;

	private int msgState = STATE_IDLE;

	private String msgType;

	/* 此条消息的发送者的头像 */
	private String fromAvatar = "";


	private Object data;

	private boolean isOfflineMessage = false;

	// iq，message随机id号得取值范围
	private char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
			+ "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();


	/**
	 * 构造函数。初始化随机数发生器
	 * 
	 * @author alonso lee
	 */
	public ALMessage() {
		random = new Random(System.currentTimeMillis());
	}

	public ALMessage(String from) {
		this.from = from;
		random = new Random(System.currentTimeMillis());
	}

	/**
	 * 构造函数。设定发送者和接受者，并初始化随机数发生器
	 * 
	 * @param from
	 * @param to
	 * @author alonso lee
	 */
	public ALMessage(String from, String to) {
		this.from = from;
		this.to = to;
		random = new Random(System.currentTimeMillis());
	}

	/**
	 * 获取随机的id号
	 * 
	 * @param length
	 * @return 随机的id号
	 * @author alonso lee
	 */
	private String randomString(int length) {
		if (length < 1) {
			return null;
		}
		// Create a char buffer to put random letters and numbers in.
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[random.nextInt(71)];
		}
		return new String(randBuffer);
	}

	/**
	 * 设定本条消息状态为已发
	 * 
	 * @param sent
	 *            true为已发
	 * @author alonso lee
	 */
	public void setSent(boolean sent) {
		this.isSent = sent;
	}

	/**
	 * 获取本条消息状态是否为已发
	 * 
	 * @return true为已发
	 * @author alonso lee
	 */
	public boolean getSent() {
		return this.isSent;
	}

	/**
	 * 设定本条消息为已收
	 * 
	 * @param delivered
	 *            true为已收
	 * @author alonso lee
	 */
	public void setDelivered(boolean delivered) {
		this.isDelivered = delivered;
	}

	/**
	 * 获取本条消息状态是否为已收
	 * 
	 * @return true为已收
	 * @author alonso lee
	 */
	public boolean getDelivered() {
		return this.isDelivered;
	}

	/**
	 * 设定本条消息为已读
	 * 
	 * @param displayed
	 *            true为已读
	 * @author alonso lee
	 */
	public void setDisplayed(boolean displayed) {
		this.isDisplayed = displayed;
	}

	/**
	 * 获取本条消息状态是否为已读
	 * 
	 * @return true为已读
	 * @author alonso lee
	 */
	public boolean getDisplayed() {
		return this.isDisplayed;
	}

	/**
	 * 设置本条消息的发送者
	 * 
	 * @param from
	 *            本条消息的发送者。请注意，是jid。如"8618676722583@mk"
	 * @author alonso lee
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * 获取本条消息的发送者
	 * 
	 * @return 本条消息的发送者。请注意，是jid。如"8618676722583@mk"
	 * @author alonso lee
	 */
	public String getFrom() {
		return this.from;
	}

	/**
	 * 设置本条消息的接收者。
	 * 
	 * @param to
	 *            本条消息的接收者。请注意，是jid。如"8618676722583@mk"
	 * @author alonso lee
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * 获取本条消息的接收者。
	 * 
	 * @return 本条消息的接收者。请注意，是jid。如"8618676722583@mk"
	 * @author alonso lee
	 */
	public String getTo() {
		return this.to;
	}

	/**
	 * 设置本条消息发送者的昵称
	 * 
	 * @param fromNick
	 *            本条消息接受者的昵称
	 * @author alonso lee
	 */
	public void setFromNick(String fromNick) {
		this.fromNick = fromNick;
	}

	/**
	 * 获取本条消息发送者的昵称
	 * 
	 * @return
	 * @author alonso lee
	 */
	public String getFromNick() {
		return this.fromNick;
	}

	/**
	 * 设置本条消息接收者的昵称
	 * 
	 * @param toNick
	 *            本条消息接受者的昵称
	 * @author alonso lee
	 */
	public void setToNick(String toNick) {
		this.toNick = toNick;
	}

	/**
	 * 获取本条消息接收者的昵称
	 * 
	 * @author alonso lee
	 */
	public String getToNick() {
		return this.toNick;
	}

	/**
	 * 设置本条消息的id号
	 * 
	 * @param id
	 *            本条消息的id号
	 * @author alonso lee
	 */
	public void setMsgId(String id) {
		this.msgId = id;
	}

	/**
	 * 获取本条消息的id号。如果id号为null，那么主动拼凑一个随机的id号
	 * 
	 * @return 本条消息的id号
	 * @author alonso lee
	 */
	public String getMsgId() {
		if (msgId != null) {
			return this.msgId;
		} else {
			this.msgId = randomString(7) + "-" + (msgNum++);
		}
		return this.msgId;
	}

	/**
	 * 设置本条消息的主题
	 * 
	 * @param subject
	 *            本条消息的主题
	 * @author alonso lee
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * 获取本条消息的主题
	 * 
	 * @return 本条消息的主题
	 * @author alonso lee
	 */
	public String getSubject() {
		return this.subject;
	}

	/**
	 * 设置本条消息的内容
	 * 
	 * @param body
	 *            本条消息的内容
	 * @author alonso lee
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * 获取本条消息的内容
	 * 
	 * @return 本条消息的内容
	 * @author alonso lee
	 */
	public String getBody() {
		return this.body;
	}


	/**
	 * 设定本条留言生成的时间
	 * 
	 * @param time
	 *            本条留言生成的时间
	 * @author ccx
	 */
	public void setTime(Date time) {
		this.time = time;
	}

	/**
	 * 获取本条留言生成的时间
	 * 
	 * @return 本条留言生成的时间
	 * @author ccx
	 */
	public Date getTime() {
		return this.time;
	}

	/**
	 * 设置本条消息的类型。
	 * 
	 * @param type
	 *            取值范围: TEXT_MSG: 文本消息 AUDIO_MSG: 共享语音 POSITION_MSG: 共享地理位置
	 *            IMAGE_MSG: 共享图片 CONTACT_MSG: 共享联系人 AUDIO_FACE_MSG: 语音表情
	 * @author alonso lee
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * 获取本条消息的类型。
	 * 
	 * @return TEXT_MSG: 文本消息 AUDIO_MSG: 共享语音 POSITION_MSG: 共享地理位置 IMAGE_MSG:
	 *         共享图片 CONTACT_MSG: 共享联系人 AUDIO_FACE_MSG: 语音表情
	 * @author alonso lee
	 */
	public int getType() {
		return this.type;
	}

	/**
	 * 设置多媒体附件在媒体服务器的id号
	 * 
	 * @param id
	 *            多媒体附件在媒体服务器的id号
	 * @author alonso lee
	 */
	public void setFileId(String id) {
		this.fileId = id;
	}

	/**
	 * 获取多媒体附件在媒体服务器的id号
	 * 
	 * @return 多媒体附件在媒体服务器的id号
	 * @author alonso lee
	 */
	public String getFIleId() {
		return this.fileId;
	}

	/**
	 * 设置多媒体附件的文件名
	 * 
	 * @param name
	 *            多媒体附件的文件名
	 * @author alonso lee
	 */
	public void setFileName(String name) {
		this.fileName = name;
	}

	/**
	 * 获取多媒体附件的文件名
	 * 
	 * @return 多媒体附件的文件名
	 * @author alonso lee
	 */
	public String getFileName() {
		return this.fileName;
	}

	/**
	 * 设置多媒体附件的文件大小
	 * 
	 * @param size
	 *            多媒体附件的文件大小
	 * @author alonso lee
	 */
	public void setFileSize(String size) {
		this.fileSize = size;
	}

	/**
	 * 获取多媒体附件的文件大小
	 * 
	 * @return 多媒体附件的文件大小
	 * @author alonso lee
	 */
	public String getFileSize() {
		return this.fileSize;
	}

	/**
	 * 设定多媒体附件的存放位置
	 * 
	 * @param path
	 *            多媒体附件的存放位置
	 * @author alonso lee
	 */
	public void setFilePath(String path) {
		this.filePath = path;
	}

	/**
	 * 获取多媒体附件的存放位置
	 * 
	 * @return 多媒体附件的存放位置
	 * @author alonso lee
	 */
	public String getFilePath() {
		return this.filePath;
	}

	/**
	 * 设置共享语音的时长
	 * 
	 * @param len
	 *            共享语音的时长
	 * @author alonso lee
	 */
	public void setRecLen(String len) {
		this.recLen = len;
	}

	/**
	 * 获取共享语音的时长
	 * 
	 * @return 共享语音的时长
	 * @author alonso lee
	 */
	public String getRecLen() {
		return this.recLen;
	}

	/**
	 * 设置共享图片的缩略图
	 * 
	 * @param bob
	 *            共享图片的缩略图
	 * @author alonso lee
	 */
	public void setBob(String bob) {
		this.bobData = bob;
	}

	/**
	 * 获取共享图片的缩略图
	 * 
	 * @return 共享图片的缩略图
	 * @author alonso lee
	 */
	public String getBob() {
		return this.bobData;
	}


	/**
	 * 设置群发联系人列表
	 * 
	 * @param cc
	 *            群发联系人列表
	 * @author alonso lee
	 */
	public void setCc(LinkedList<String> cc) {
		if (cc != null) {
			this.cc = cc;
		}
	}

	/**
	 * 获取群发联系人列表
	 * 
	 * @return 群发联系人列表
	 * @author alonso lee
	 */
	public LinkedList<String> getCc() {
		return this.cc;
	}


	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getMsgType() {
		return msgType;
	}


	/**
	 * @param fromAvatar
	 *            the fromAvatar to set
	 */
	public void setFromAvatar(String fromAvatar) {
		this.fromAvatar = fromAvatar;
	}

	/**
	 * @return the fromAvatar
	 */
	public String getFromAvatar() {
		return fromAvatar;
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

	public void setIsOffilneMessage(boolean b) {
		this.isOfflineMessage = b;
	}

	public boolean isOfflineMessage() {
		return this.isOfflineMessage;
	}

	@Override
	public int hashCode() {

		if (this.msgId == null) {
			this.msgId = getMsgId();
		}
		return this.msgId.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		// 如果是统一对象，那么直接返回true
		if (this == o) {
			return true;
		}
		if (o instanceof ALMessage) {
			if (this.hashCode() == o.hashCode()) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

}
