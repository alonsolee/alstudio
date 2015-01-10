package alstudio.alstudiolib.common.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.easemob.chat.EMMessage;

import alstudio.alstudioim.IMEvent;
import alstudio.alstudioim.IMManager;
import alstudio.alstudiolib.R;
import de.greenrobot.event.EventBus;

public abstract class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        EventBus.getDefault().register(this);
    }

    public abstract void initUI();

    @Override
    protected void onResume() {
        super.onResume();
        IMManager.getInstance().onActivityResume();
    }

    @Override
    public void finish() {
        super.finish();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(IMEvent event) {
        switch (event.getType()) {
            case IMEVENT_LOGIN_FAILED:
                handleIMConnectionLoginFailed(event);
                break;
            case IMEVENT_CONFLICT:
                handleIMConnectionConflict();
                break;
            case IMEVENT_DISCONNECTED:
                handleIMConnectionLost(event);
                break;
            case IMEVENT_LOGINED:
                handleIMConnectionEstablished();
                break;
            case IMEVENT_MESSAGE_DILIVERED:
                handleIMReceiveMessageDeliveredAck((EMMessage) event.getData());
                break;
            case IMEVENT_MESSAGE_READED:
                handleIMReceiveMessageReadedAck((EMMessage) event.getData());
                break;
            case IMEVENT_MESSAGE_SEND_SUCCESS:
                handleIMSendMessageSuccess((EMMessage) event.getData());
                break;
            case IMEVENT_MESSAGE_SEND_FAILED:
                handleIMSendMessageFailed(event);
                break;
            case IMEVENT_RECEIVE_NEW_MESSAGE:
                handleIMReceiveNewMessage((EMMessage) event.getData());
                break;
        }
    }

    /**
     * IM连接丢失回调
     *
     * @param event
     */
    public void handleIMConnectionLost(IMEvent event) {

    }

    /**
     * IM登陆成功
     */
    public void handleIMConnectionEstablished() {

    }

    /**
     * IM连接被踢下线
     */
    public void handleIMConnectionConflict() {

    }

    /**
     * IM收到新消息
     *
     * @param msg
     */
    public void handleIMReceiveNewMessage(EMMessage msg) {

    }

    /**
     * IM收到消息送达回调
     *
     * @param msg
     */
    public void handleIMReceiveMessageDeliveredAck(EMMessage msg) {

    }

    /**
     * IM发送消息成功回调
     *
     * @param msg
     */
    public void handleIMSendMessageSuccess(EMMessage msg) {

    }

    /**
     * IM发送消息失败回调
     *
     * @param event
     */
    public void handleIMSendMessageFailed(IMEvent event) {

    }

    public void handleIMReceiveMessageReadedAck(EMMessage msg) {

    }

    /**
     * 登录失败
     * @param event
     */
    public void handleIMConnectionLoginFailed(IMEvent event){

    }
}
