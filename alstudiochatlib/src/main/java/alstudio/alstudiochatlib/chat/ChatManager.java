package alstudio.alstudiochatlib.chat;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.ViewGroup;


/**
 * Created by Alonso Lee on 2014/12/24.
 */
public class ChatManager implements ChatInputLayoutEngine.KeyBoardHeightChangeListener {
    private static ChatManager ourInstance = new ChatManager();

    public static ChatManager getInstance() {
        return ourInstance;
    }

    private ChatInputLayoutEngine chatEngine;

    private int keyBoardHeight = 0;

    private Context context;
    private SharedPreferences sp;
    private final String KEYBOARD_HEIGHT_TAG = "keyboardheight";

    private ChatManager() {

    }

    public void init(Context context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void createDefault(Context context, int keyboardHeight) {
        if (sp == null) {
            init(context);
        }
        if (keyboardHeight == 0) {
            keyBoardHeight = keyboardHeight;
        }
        keyBoardHeight = sp.getInt(KEYBOARD_HEIGHT_TAG, keyboardHeight);

        chatEngine = ChatInputManager.build();
        chatEngine.init(context, keyBoardHeight);
        chatEngine.setKeyBoardHeightChangeListener(this);
    }

    public ChatInputLayoutEngine getChatInputEngine() {
        return chatEngine;
    }

    public void attach(Activity activity, ViewGroup viewGroup, ChatInputLayoutEngine.InputViewShowChangeListener listener) {
        chatEngine.setInputViewShowChangeListener(listener);
        chatEngine.attach(activity, viewGroup);
    }

    public void detach() {
        chatEngine.detach();
    }

    public boolean invokeBackPressed() {
        if (chatEngine != null) {
            return chatEngine.onBackKeyPressed();
        }
        return false;
    }

    @Override
    public void onKeyBoardHeightChange(int height) {
        keyBoardHeight = height;
        //保存
        sp.edit().putInt(KEYBOARD_HEIGHT_TAG, height).commit();
    }
}
