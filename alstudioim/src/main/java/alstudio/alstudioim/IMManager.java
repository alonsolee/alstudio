package alstudio.alstudioim;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.easemob.EMCallBack;
import com.easemob.EMConnectionListener;
import com.easemob.EMError;
import com.easemob.chat.ConnectionListener;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMChatOptions;
import com.easemob.chat.EMContactListener;
import com.easemob.chat.EMContactManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMGroupManager;
import com.easemob.chat.EMMessage;
import com.easemob.chat.ImageMessageBody;
import com.easemob.chat.TextMessageBody;
import com.easemob.chat.VoiceMessageBody;
import com.easemob.exceptions.EaseMobException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Alonso Lee on 2014/12/29.
 */
public class IMManager {

    private final String TAG = getClass().getSimpleName();

    private boolean isInited = false;

    private IMConfig imConfig;

    private boolean isLogined;

    private IMBaseAccountManager accountManager;
    private IMBaseContactManager contactManager;

    private static IMManager ourInstance = new IMManager();

    public static IMManager getInstance() {
        return ourInstance;
    }

    private IMManager() {
    }

    public synchronized boolean init(Context context, IMBaseAccountManager accountManager,IMBaseContactManager contactManager) {
        if (isInited)
            return true;

        this.contactManager = contactManager;
        this.accountManager = accountManager;
        imConfig = IMConfig.createDefaultConfig();

        //初始化IM
        EMChat.getInstance().init(context);
        //是否输出日志
        EMChat.getInstance().setDebugMode(imConfig.isShowIMLog());
        //监听IM登陆状态
        EMChatManager.getInstance().addConnectionListener(imConnectionListener);
        //监听IM连接状态
        EMChatManager.getInstance().addConnectionListener(imNetworkConnectionListener);
        //监听联系人变化
        EMContactManager.getInstance().setContactListener(new ContactListener());

        //监听新消息
        IntentFilter inf = new IntentFilter(EMChatManager.getInstance().getNewMessageBroadcastAction());
        inf.setPriority(3);
        context.registerReceiver(newMessageBroadcastReceiver, inf);

        //监听消息已读回执
        inf = new IntentFilter(EMChatManager.getInstance().getAckMessageBroadcastAction());
        inf.setPriority(3);
        context.registerReceiver(messageAckBroadcastReceiver, inf);


        //监听消息送达回执
        inf = new IntentFilter(EMChatManager.getInstance().getDeliveryAckMessageBroadcastAction());
        inf.setPriority(3);
        context.registerReceiver(deliveryAckMessageReceiver, inf);


        EMChat.getInstance().setAppInited();

        //初始化聊天设置
        initChatConfig();

        isInited = true;
        return true;
    }

    public void loadLocalData(){
        EMGroupManager.getInstance().loadAllGroups();
        EMChatManager.getInstance().loadAllConversations();
    }

    public void reset(){
        //清空内存中联系人的数据
        EMContactManager.getInstance().reset();
    }

    public void setIMContactManager(IMBaseContactManager manager){
        this.contactManager = manager;
    }

    /**
     * 设置IM账号管理器实例
     *
     * @param accountManager
     */
    public void setIMAccountManager(IMBaseAccountManager accountManager) {
        this.accountManager = accountManager;
    }

    /**
     * 获取IM账号管理器实例
     *
     * @return
     */
    public IMBaseAccountManager getIMAccountManager() {
        return this.accountManager;
    }


    /**
     * 通知IM模块，界面已经从后台回到前台
     */
    public void onActivityResume() {
        EMChatManager.getInstance().activityResumed();
    }

    /**
     * 切换IM配置
     *
     * @param config
     */
    public void setChatConfig(IMConfig config) {
        imConfig = config;
        initChatConfig();
    }

    public void setPlayAudioViaSpeaker(boolean speaker) {
        imConfig.setAudioPlayForSpeaker(speaker);
    }

    /**
     * 设置新消息通知方式
     *
     * @param config
     */
    public void setNotifyConfig(IMConfig.IMNoticeConfig config) {
        imConfig.setNoticeConfig(config);
        initChatConfig();
    }

    /**
     * 获取新消息通知方式
     *
     * @return
     */
    public IMConfig.IMNoticeConfig getNotifyConfig() {
        return imConfig.getNoticeConfig();
    }

    /**
     * 设置新消息通知铃声uri
     *
     * @param uri
     */
    public void setNotifycationUri(Uri uri) {
        imConfig.setRingUri(uri);
        initChatConfig();
    }

    /**
     * 设置是否由IM管理好友关系
     *
     * @param value
     */
    public void setIMMaintainRoster(boolean value) {
        imConfig.setIMMaintainRoster(value);
        EMChatManager.getInstance().getChatOptions().setUseRoster(value);
    }

    /**
     * 检测是否由IM服务器管理好友关系
     *
     * @return
     */
    public boolean isIMMaintainRoster() {
        return imConfig.isIMMaintainRoster();
    }

    public void createAccount(String userName, String passWord) {
        try {
            EMChatManager.getInstance().createAccountOnServer(userName, passWord);
            IMEvent event = new IMEvent(IMEvent.IMEventType.IMEVENT_REGISTER_SUCCESS);
            postIMEvent(event);
        } catch (EaseMobException e) {
            e.printStackTrace();
        }
    }

    public boolean isUsed() {
        return accountManager.isLogined();
    }

    /**
     * 用内置账号密码登陆
     *
     * @return
     */
    public synchronized boolean login() {
        String userName = accountManager.getIMUserName();
        String passWord = accountManager.getIMPassWord();
        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(passWord)) {
            login(userName, passWord);
            return true;
        }
        return false;
    }

    /**
     * 登陆IM服务器
     *
     * @param username
     * @param password
     */
    public synchronized void login(String username, String password) {
        imConfig.setUsername(username);
        imConfig.setPassword(password);
        EMChatManager.getInstance().login(username, password, loginCallback);
    }

    /**
     * 注销登陆IM服务器
     */
    public synchronized void logout() {
        imConfig.setUsername("");
        imConfig.setPassword("");
        accountManager.saveIMAccount("", "");
        EMChatManager.getInstance().logout(logoutCallback);
    }

    public synchronized boolean isIMLogined() {
        return EMChatManager.getInstance().isConnected();
    }


    private void onIMLogined() {
        synchronized (IMManager.class) {
            isLogined = true;
            if (imConfig.isShowIMLog()) {
                Log.d(TAG, "invoke im login success");
            }
            accountManager.saveIMAccount(EMChatManager.getInstance().getCurrentUser(), imConfig.getPassword());
            IMEvent event = new IMEvent(IMEvent.IMEventType.IMEVENT_LOGINED);
            event.setData(EMChatManager.getInstance().getCurrentUser());
            postIMEvent(event);
        }
    }

    private void onIMLogout(boolean isConflict) {
        synchronized (IMManager.class) {
            isLogined = false;
            IMEvent event = null;
            if (isConflict) {
                event = new IMEvent(IMEvent.IMEventType.IMEVENT_CONFLICT);
            } else {
                event = new IMEvent(IMEvent.IMEventType.IMEVENT_DISCONNECTED);
            }
            postIMEvent(event);
        }
    }

    private void onIMLoginFail(String errorMsg, int code) {
        synchronized (IMManager.class) {
            isLogined = false;
            IMEvent event = new IMEvent(IMEvent.IMEventType.IMEVENT_LOGIN_FAILED);
            event.setErrorMsg(errorMsg);
            event.setCode(code);
            postIMEvent(event);
        }
    }

    /********************************************* 消息发送 **************************************************************/
    /**
     * 生成基本的聊天消息,缺省为文本消息
     *
     * @param to      消息接收者
     * @param isGroup true标示群聊
     * @return
     */
    private EMMessage createMessageBase(String to, boolean isGroup) {
        if (!isIMLogined() || TextUtils.isEmpty(to))
            return null;
        EMMessage msg = EMMessage.createSendMessage(EMMessage.Type.TXT);
        if (isGroup) {
            //群聊
            msg.setChatType(EMMessage.ChatType.GroupChat);
        }
        msg.setReceipt(to);

        return msg;
    }

    private void sendMessage(final EMMessage msg) {
        EMChatManager.getInstance().getConversation(msg.getTo()).addMessage(msg);
        EMChatManager.getInstance().sendMessage(msg, new EMCallBack() {
            @Override
            public void onSuccess() {
                //发送成功
                IMEvent event = new IMEvent(IMEvent.IMEventType.IMEVENT_MESSAGE_SEND_SUCCESS);
                event.setData(msg);
                postIMEvent(event);

            }

            @Override
            public void onError(int i, String s) {
                IMEvent event = new IMEvent(IMEvent.IMEventType.IMEVENT_MESSAGE_SEND_FAILED);
                event.setData(msg);
                event.setCode(i);
                event.setErrorMsg(s);
                postIMEvent(event);

            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    /**
     * 发送文字消息
     *
     * @param txt     待发送文案
     * @param to      消息接收者
     * @param isGroup true标示发送群聊
     * @return
     */
    public synchronized boolean sendTxtMessage(String txt, String to, boolean isGroup) {
        if (!isIMLogined() || TextUtils.isEmpty(txt) || TextUtils.isEmpty(to))
            return false;
        EMMessage msg = createMessageBase(to, isGroup);

        TextMessageBody body = new TextMessageBody(txt);
        msg.addBody(body);
        sendMessage(msg);
        return true;
    }

    /**
     * 发送单聊文本消息
     *
     * @param txt
     * @param to
     * @return
     */
    public synchronized boolean sendTxtMessage(String txt, String to) {
        return sendTxtMessage(txt, to, false);
    }

    /**
     * 发送图片消息
     *
     * @param file    图片文件地址
     * @param to      图片消息接收者
     * @param isGroup true表示群聊
     * @return
     */
    public synchronized boolean sendPicMessage(String file, String to, boolean isGroup) {
        if (!isIMLogined() || TextUtils.isEmpty(to))
            return false;
        EMMessage msg = createMessageBase(to, isGroup);
        msg.setType(EMMessage.Type.IMAGE);
        ImageMessageBody body = new ImageMessageBody(new File(file));
        msg.addBody(body);

        sendMessage(msg);

        return true;
    }

    /**
     * 发送单聊图片消息
     *
     * @param file 图片文件地址
     * @param to   图片消息接收者
     * @return
     */
    public synchronized boolean sendPicMessage(String file, String to) {
        return sendPicMessage(file, to, false);
    }

    /**
     * 发送语音消息
     *
     * @param file    语音文件地址
     * @param len     语音长度
     * @param to      语音消息接收者
     * @param isGroup true表示群聊
     * @return
     */
    public synchronized boolean sendVoiceMessage(String file, int len, String to, boolean isGroup) {
        if (TextUtils.isEmpty(to))
            return false;
        EMMessage msg = createMessageBase(to, isGroup);
        msg.setType(EMMessage.Type.VOICE);
        VoiceMessageBody body = new VoiceMessageBody(new File(file), len);
        msg.addBody(body);
        sendMessage(msg);
        return true;
    }

    /**
     * 发送单聊语音消息
     *
     * @param file 语音文件地址
     * @param len  语音文件长度
     * @param to   语音消息接收者
     * @return
     */
    public synchronized boolean sendVoiceMessage(String file, int len, String to) {
        return sendVoiceMessage(file, len, to, false);
    }


    /**
     * 发送已读回执
     *
     * @param to
     * @param msgId
     * @return
     */
    public synchronized boolean sendMsgReadAck(String to, String msgId) {
        try {
            EMChatManager.getInstance().ackMessageRead(to, msgId);
        } catch (EaseMobException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * ****************************************** 会话管理 *************************************************************
     */
    public EMConversation getConversation(String userId) {
        return EMChatManager.getInstance().getConversation(userId);
    }

    /**
     * 设置对应会话的消息为全部已读
     *
     * @param userId
     */
    public void setAllMsgsReaded(String userId) {
        getConversation(userId).resetUnreadMsgCount();
    }

    /**
     * 删除指定会话的所有消息
     *
     * @param userId
     */
    public void deleteAllMsgs(String userId) {
        EMChatManager.getInstance().deleteConversation(userId);
    }

    /**
     * 删除指定讨论组的所有消息
     *
     * @param userId
     */
    public void deleteAllGroupMsgs(String userId) {
        EMChatManager.getInstance().deleteConversation(userId, true);
    }

    /**
     * 删除所有消息记录
     */
    public void deletAllMsgs() {
        EMChatManager.getInstance().deleteAllConversation();
    }

    /**
     * 获取指定userid的消息列表
     *
     * @param userId
     * @param count
     * @return
     */
    public List<EMMessage> getMsgsFromConversation(String userId, int count) {
        return getMsgsFromConversation(userId, null, count);
    }

    /**
     * 获取指定userid的消息列表
     *
     * @param userId
     * @param startId
     * @param count
     * @return
     */
    public List<EMMessage> getMsgsFromConversation(String userId, String startId, int count) {
        List<EMMessage> list = new ArrayList<EMMessage>();
        EMConversation conversation = getConversation(userId);
        EMMessage msg = null;
        if (!TextUtils.isEmpty(startId)) {
            msg = conversation.getMessage(startId);
        } else {
            msg = conversation.getMessage(0);
        }
        if (msg != null) {
            list = conversation.loadMoreMsgFromDB(msg.getMsgId(), count);
        }
        return list;

    }

    /**
     * 获取指定userid的消息条目数
     *
     * @param userId
     * @return
     */
    public int getConversationSum(String userId) {
        return getConversation(userId).getMsgCount();
    }

    /**
     * 获取指定userid的未读消息数
     *
     * @param userId
     * @return
     */
    public int getConversationUnReadMsgCount(String userId) {
        return getConversation(userId).getUnreadMsgCount();
    }

    /**
     * 删除指定userid中的指定id的消息
     *
     * @param userId
     * @param msgId
     */
    public void removeConversationMessage(String userId, String msgId) {
        getConversation(userId).removeMessage(msgId);
    }

    /*********************************** 好友管理 ***************************************************/
    /**
     * 请求联系人的账号名列表
     *
     * @return
     */
    public List<String> getContactUserNames() {
        try {
            return EMContactManager.getInstance().getContactUserNames();
        } catch (EaseMobException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发送添加好友请求
     *
     * @param userName 待添加的好友账号
     * @param reason   添加原因
     * @return
     */
    public boolean addContact(String userName, String reason) {
        if (!EMChatManager.getInstance().isConnected()) {
            return false;
        }
        try {
            EMContactManager.getInstance().addContact(userName, reason);
            return true;
        } catch (EaseMobException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除指定联系人
     *
     * @param userName 待删除的联系人账号
     * @return
     */
    public boolean deleteContact(String userName) {
        if (!EMChatManager.getInstance().isConnected()) {
            return false;
        }
        try {
            EMContactManager.getInstance().deleteContact(userName);
            return true;
        } catch (EaseMobException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 接收指定联系人的加好友请求
     *
     * @param userName 发送加好友请求的联系人
     * @return
     */
    public boolean acceptInvitation(String userName) {
        if (!EMChatManager.getInstance().isConnected()) {
            return false;
        }
        try {
            EMChatManager.getInstance().acceptInvitation(userName);
            return true;
        } catch (EaseMobException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 拒绝指定联系人发送的加好友请求
     *
     * @param userName 发送加好友请求的联系人
     * @return
     */
    public boolean refuseInvitation(String userName) {
        if (!EMChatManager.getInstance().isConnected()) {
            return false;
        }
        try {
            EMChatManager.getInstance().refuseInvitation(userName);
            return true;
        } catch (EaseMobException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取本地黑名单用户名
     *
     * @return
     */
    public List<String> getBlackListUserNames() {
        return EMContactManager.getInstance().getBlackListUsernames();
    }

    /**
     * 从服务器获取黑名单用户名列表
     *
     * @return
     */
    public List<String> getBlackListUserNamesFromServer() {
        try {
            return EMContactManager.getInstance().getBlackListUsernamesFromServer();
        } catch (EaseMobException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 添加联系人到黑名单
     *
     * @param userName    待加入黑名单的联系人
     * @param blockAllMsg true表示加入黑名单后，双发都无法收到消息，false表示收不到对方发来的消息
     * @return
     */
    public boolean addContactToBlackList(String userName, boolean blockAllMsg) {
        if (!EMChatManager.getInstance().isConnected()) {
            return false;
        }
        try {
            EMContactManager.getInstance().addUserToBlackList(userName, blockAllMsg);
            return true;
        } catch (EaseMobException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将制定联系人从黑名单中移除
     *
     * @param userName 待移出黑名单的用户名
     * @return
     */
    public boolean removeContactFromBlackList(String userName) {
        if (!EMChatManager.getInstance().isConnected()) {
            return false;
        }
        return true;
    }

    /*********************************** IM事件回调 ***************************************************/

    /**
     * 联系人变化监听
     */
    private class ContactListener implements EMContactListener{

        @Override
        public void onContactAdded(List<String> strings) {
            if(contactManager != null){
                contactManager.onContactAdded(strings);
            }
            IMEvent event = new IMEvent(IMEvent.IMEventType.IMEVENT_CONTACT_CHANGE);
            event.setData(strings);
            postIMEvent(event);
        }

        @Override
        public void onContactDeleted(List<String> strings) {
            if(contactManager != null){
                contactManager.onContactDelete(strings);
            }
            IMEvent event = new IMEvent(IMEvent.IMEventType.IMEVENT_DELETED_CONTECT);
            event.setData(strings);
            postIMEvent(event);

        }

        @Override
        public void onContactInvited(String s, String s2) {
            if(contactManager != null){
                contactManager.onNewContactInvite(s, s2);
            }
            IMEvent event = new IMEvent(IMEvent.IMEventType.IMEVENT_DELETED_CONTECT);
            event.setData(s);
            postIMEvent(event);
        }

        @Override
        public void onContactAgreed(String s) {
            if(contactManager != null){
                contactManager.onContactAgreed(s);
            }
            IMEvent event = new IMEvent(IMEvent.IMEventType.IMEVENT_ACCEPT_INVITE);
            event.setData(s);
            postIMEvent(event);

        }

        @Override
        public void onContactRefused(String s) {
            if(contactManager != null){
                contactManager.onContactRefuse(s);
            }
            IMEvent event = new IMEvent(IMEvent.IMEventType.IMEVENT_REFUSE_INVITE);
            event.setData(s);
            postIMEvent(event);
        }
    }

    /**
     * IM连接状态监听器
     */
    private EMConnectionListener imConnectionListener = new EMConnectionListener() {
        @Override
        public void onConnected() {
            //登陆IM成功
            onIMLogined();
        }

        @Override
        public void onDisconnected(int i) {
            if (i == EMError.CONNECTION_CONFLICT) {
                //被踢下线了
                onIMLogout(true);
            } else {
                onIMLogout(false);
            }
        }
    };

    private ConnectionListener imNetworkConnectionListener = new ConnectionListener() {
        @Override
        public void onConnected() {
            if (imConfig.isShowIMLog()) {
                Log.d(TAG, "im connection established");
            }
        }

        @Override
        public void onDisConnected(String s) {
            if (imConfig.isShowIMLog()) {
                Log.d(TAG, "im connection disconnected: " + s);
            }
        }

        @Override
        public void onReConnected() {
            if (imConfig.isShowIMLog()) {
                Log.d(TAG, "im reconnect success");
            }
        }

        @Override
        public void onReConnecting() {
            if (imConfig.isShowIMLog()) {
                Log.d(TAG, "im invoking reconnecting");
            }
        }

        @Override
        public void onConnecting(String s) {
            if (imConfig.isShowIMLog()) {
                Log.d(TAG, "im invoking connecting: " + s);
            }
        }
    };


    /**
     * 登陆IM回调
     */
    private EMCallBack loginCallback = new EMCallBack() {
        @Override
        public void onSuccess() {
            if (imConfig.isShowIMLog()) {
                Log.d(TAG, "login im success");
            }

        }

        @Override
        public void onError(int i, String s) {
            if (imConfig.isShowIMLog()) {
                Log.d(TAG, "login im onError " + s);
            }
            onIMLoginFail(s, i);
        }

        @Override
        public void onProgress(int i, String s) {
            if (imConfig.isShowIMLog()) {
                Log.d(TAG, "login im onProgress " + s);
            }

        }
    };

    /**
     * 注销IM登陆回调
     */
    private EMCallBack logoutCallback = new EMCallBack() {
        @Override
        public void onSuccess() {
            if (imConfig.isShowIMLog()) {
                Log.d(TAG, "logout im success ");
            }
        }

        @Override
        public void onError(int i, String s) {
            if (imConfig.isShowIMLog()) {
                Log.d(TAG, "logout im onError " + s);
            }
        }

        @Override
        public void onProgress(int i, String s) {
            if (imConfig.isShowIMLog()) {
                Log.d(TAG, "logout im onProgress " + s);
            }
        }
    };

    /**
     * 新消息广播接收器
     */
    private BroadcastReceiver newMessageBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String msgid = intent.getStringExtra("msgid");
            // 收到这个广播的时候，message已经在db和内存里了，可以通过id获取mesage对象
            EMMessage message = EMChatManager.getInstance().getMessage(msgid);
            IMEvent event = new IMEvent(IMEvent.IMEventType.IMEVENT_RECEIVE_NEW_MESSAGE);
            event.setData(message);
            postIMEvent(event);
        }
    };

    /**
     * 消息回执广播接收器
     */
    private BroadcastReceiver messageAckBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String msgid = intent.getStringExtra("msgid");
            String from = intent.getStringExtra("from");
            EMConversation conversation = EMChatManager.getInstance().getConversation(from);
            if (conversation != null) {
                // 把message设为已读
                EMMessage msg = conversation.getMessage(msgid);
                if (msg != null) {
                    msg.isAcked = true;
                }
                IMEvent event = new IMEvent(IMEvent.IMEventType.IMEVENT_MESSAGE_READED);
                event.setData(msg);
                postIMEvent(event);
            }

        }
    };

    /**
     * 消息送达BroadcastReceiver
     */
    private BroadcastReceiver deliveryAckMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            abortBroadcast();

            String msgid = intent.getStringExtra("msgid");
            String from = intent.getStringExtra("from");
            EMConversation conversation = EMChatManager.getInstance().getConversation(from);
            if (conversation != null) {
                // 把message设为已读
                EMMessage msg = conversation.getMessage(msgid);
                if (msg != null) {
                    msg.isDelivered = true;
                }
                IMEvent event = new IMEvent(IMEvent.IMEventType.IMEVENT_MESSAGE_DILIVERED);
                event.setData(msg);
                postIMEvent(event);
            }

        }
    };


    /**
     * 抛出IM事件
     *
     * @param event
     */
    private void postIMEvent(IMEvent event) {
        EventBus.getDefault().post(event);
        if (imConfig.isShowIMLog()) {
            Log.d(TAG, "抛出IM事件 " + event.getType());
        }

    }

    /**
     * ******************************** IM模块初始化 *********************************************
     */
    private void initChatConfig() {
        EMChatOptions opt = EMChatManager.getInstance().getChatOptions();
        //设置是否需要已读回执
        opt.setRequireAck(imConfig.isReadedAckRequire());
        //设置是否需要送达回执
        opt.setRequireDeliveryAck(imConfig.isDeliverAckRequire());
        //设置是否需要自动接受加好友申请
        opt.setAcceptInvitationAlways(imConfig.isAutoAccpetInvitation());
        //设置收到新消息，在状态栏提示
        opt.setShowNotificationInBackgroud(imConfig.isShowNotifycationInStatusBar());
        //设置语音消息由扬声器播放
        opt.setUseSpeaker(imConfig.isAudioPlayForSpeaker());
        //设置是否需要IM服务器管理好友关系
        opt.setUseRoster(imConfig.isIMMaintainRoster());
        //设置发送消息是否需要加密
        opt.setUseEncryption(imConfig.isMsgEncrypt());
        //设置新消息通知铃声uri
        if (imConfig.getRingUri() != null) {
            opt.setNotifyRingUri(imConfig.getRingUri());
        }

        //设置新消息提醒方式,缺省为声音与振动
        switch (imConfig.getNoticeConfig()) {
            case VIBRATE_ONLY:
                EMChatManager.getInstance().getChatOptions().setNotificationEnable(true);
                EMChatManager.getInstance().getChatOptions().setNoticedByVibrate(true);
                break;
            case SOUND_ONLY:
                EMChatManager.getInstance().getChatOptions().setNotificationEnable(true);
                EMChatManager.getInstance().getChatOptions().setNoticeBySound(true);
                break;
            case VIBRATE_SOUND:
                EMChatManager.getInstance().getChatOptions().setNotificationEnable(true);
                EMChatManager.getInstance().getChatOptions().setNotifyBySoundAndVibrate(true);
                break;
            case DISABLE:
                EMChatManager.getInstance().getChatOptions().setNotificationEnable(false);
            default:
                EMChatManager.getInstance().getChatOptions().setNoticedByVibrate(false);
                EMChatManager.getInstance().getChatOptions().setNoticeBySound(false);
                EMChatManager.getInstance().getChatOptions().setNotificationEnable(true);
                break;

        }
    }
}
