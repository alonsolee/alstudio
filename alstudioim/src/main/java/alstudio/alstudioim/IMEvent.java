package alstudio.alstudioim;

/**
 * Created by Alonso Lee on 2014/12/29.
 */
public class IMEvent {

    private IMEventType type;
    private Object data;
    private int code = RESPONSE_OK;
    public static int RESPONSE_OK = 0;
    private String errorMsg;

    public IMEvent(IMEventType type){
        this.type = type;
    }

    public IMEventType getType() {
        return type;
    }

    public void setType(IMEventType type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public enum IMEventType{

        //连接管理
        IMEVENT_LOGIN_FAILED,
        IMEVENT_LOGINED,
        IMEVENT_DISCONNECTED,
        IMEVENT_CONFLICT,

        //消息处理
        IMEVENT_MESSAGE_DILIVERED,
        IMEVENT_MESSAGE_READED,
        IMEVENT_MESSAGE_SEND_SUCCESS,
        IMEVENT_MESSAGE_SEND_FAILED,
        IMEVENT_RECEIVE_NEW_MESSAGE,
        IMEVENT_REGISTER_SUCCESS,
        IMEVENT_REGISTER_FAILED,

        //好友管理
        //新加好友邀请
        IMEVENT_NEW_ADD_CONTACT_INVITE,
        //新添加到联系人列表
        IMEVENT_CONTACT_CHANGE,
        //对方拒绝加好友邀请
        IMEVENT_REFUSE_INVITE,
        //对方接受加好友邀请
        IMEVENT_ACCEPT_INVITE,
        //被对方从联系人列表中移除
        IMEVENT_DELETED_CONTECT,

    }
}
