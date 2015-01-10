package alstudio.alstudioim;

import java.util.List;

/**
 * IM联系人变化管理器。用于保存联系人
 * Created by Alonso Lee on 2015/1/4.
 */
public abstract class IMBaseContactManager {
    /**
     * 用户接受加好友邀请回调
     *
     * @param userName 接受我的邀请的用户名
     */
    public abstract void onContactAgreed(String userName);

    /**
     * 用户拒绝加好友邀请回调
     *
     * @param userName 拒绝邀请的用户名
     */
    public abstract void onContactRefuse(String userName);

    /**
     * 收到添加好友的邀请
     *
     * @param userName 发送添加好友邀请的用户名
     * @param reason   邀请原因
     */
    public abstract void onNewContactInvite(String userName, String reason);

    /**
     * 被用户从联系人列表中删除回调
     *
     * @param strings 发送从他的联系人列表删除我的请求的用户名
     */
    public abstract void onContactDelete(List<String> strings);

    /**
     * 新用户加入我的联系人列表回调
     *
     * @param strings
     */
    public abstract void onContactAdded(List<String> strings);
}
