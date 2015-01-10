package alstudio.alstudiochatlib;

import android.view.View;
import android.widget.EditText;

/**
 * Created by Alonso Lee on 2015/1/5.
 */
public abstract class BaseChatManager {

    protected EditText mEditText;

    public void attachEditText(EditText editText,View btnSend){
        mEditText = editText;
    }

    public void clearEditText(){
        if(mEditText != null){
            mEditText.setText("");
        }
    }

    public void reset(){
        clearEditText();
        mEditText = null;
    }



}
