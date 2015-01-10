package alstudio.alstudioim;

import android.net.Uri;

/**
 * Created by Alonso Lee on 2014/12/29.
 */
public class IMConfig {

    //是否自动登录
    private boolean isAutoLogin = true;
    //是否输出IM日志
    private boolean isShowIMLog = true;
    //是否为测试环境
    private boolean isDebugEnv = true;

    //是否需要接收送达回执
    private boolean isDeliverAckRequire = true;
    //是否需要已读回执
    private boolean isReadedAckRequire = true;
    // 设置新消息提示方式
    private IMNoticeConfig noticeConfig = IMNoticeConfig.VIBRATE_SOUND;
    //设置发送消息是否加密，缺省为明文
    private boolean isMsgEncrypt = false;
    //设置是否需要IM服务器管理好友,缺省为不管理
    private boolean isIMMantainRoster = false;
    //设置新消息提示是否在状态栏显示,缺省为显示
    private boolean isShowNotifycationInStatusBar = true;
    //设置是否需要自动接受加好友申请,缺省为不接受
    private boolean isAutoAccpetInvitation = false;

    //设置音频消息通过扬声器播放,缺省为扬声器播放
    private boolean isAudioPlayForSpeaker = true;

    //新消息通知铃声uri
    private Uri ringUri;

    private String username;
    private String password;

    public IMConfig() {
    }

    public static IMConfig createDefaultConfig(){
        return new IMConfig();
    }

    public boolean isAutoLogin() {
        return isAutoLogin;
    }

    public void setAutoLogin(boolean isAutoLogin) {
        this.isAutoLogin = isAutoLogin;
    }

    public boolean isShowIMLog() {
        return isShowIMLog;
    }

    public void setShowIMLog(boolean isShowIMLog) {
        this.isShowIMLog = isShowIMLog;
    }

    public boolean isDebugEnv() {
        return isDebugEnv;
    }

    public void setDebugEnv(boolean isDebugEnv) {
        this.isDebugEnv = isDebugEnv;
    }

    public boolean isDeliverAckRequire() {
        return isDeliverAckRequire;
    }

    public void setDeliverAckRequire(boolean isDeliverAckRequire) {
        this.isDeliverAckRequire = isDeliverAckRequire;
    }

    public boolean isReadedAckRequire() {
        return isReadedAckRequire;
    }

    public void setReadedAckRequire(boolean isReadedAckRequire) {
        this.isReadedAckRequire = isReadedAckRequire;
    }

    public IMNoticeConfig getNoticeConfig() {
        return noticeConfig;
    }

    public void setNoticeConfig(IMNoticeConfig noticeConfig) {
        this.noticeConfig = noticeConfig;
    }

    public boolean isMsgEncrypt() {
        return isMsgEncrypt;
    }

    public void setMsgEncrypt(boolean isMsgEncrypt) {
        this.isMsgEncrypt = isMsgEncrypt;
    }

    public boolean isIMMaintainRoster() {
        return isIMMantainRoster;
    }

    public void setIMMaintainRoster(boolean isIMMantainRoster) {
        this.isIMMantainRoster = isIMMantainRoster;
    }

    public boolean isShowNotifycationInStatusBar() {
        return isShowNotifycationInStatusBar;
    }

    public void setShowNotifycationInStatusBar(boolean isShowNotifycationInStatusBar) {
        this.isShowNotifycationInStatusBar = isShowNotifycationInStatusBar;
    }

    public boolean isAutoAccpetInvitation() {
        return isAutoAccpetInvitation;
    }

    public void setAutoAccpetInvitation(boolean isAutoAccpetInvitation) {
        this.isAutoAccpetInvitation = isAutoAccpetInvitation;
    }

    public boolean isAudioPlayForSpeaker() {
        return isAudioPlayForSpeaker;
    }

    public void setAudioPlayForSpeaker(boolean isAudioPlayForSpeaker) {
        this.isAudioPlayForSpeaker = isAudioPlayForSpeaker;
    }

    public Uri getRingUri() {
        return ringUri;
    }

    public void setRingUri(Uri ringUri) {
        this.ringUri = ringUri;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 收到新消息通知配置
     */
    public enum IMNoticeConfig{
        DISABLE,
        NONE,
        VIBRATE_ONLY,
        SOUND_ONLY,
        VIBRATE_SOUND,
    }
}
