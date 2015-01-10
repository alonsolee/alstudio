package alstudio.alstudiochatlib.chat;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import alstudio.alstudiochatlib.R;

/**
 * Created by Alonso Lee on 2015/1/7.
 */
public class ChatInputMethodView extends RelativeLayout {

    private LayoutInflater mLayoutInflater;

    private ImageView record_change_icon;
    private View smile_icon;
    private View press_to_talk;
    private View add_icon;
    private View send_btn;

    private View input_layout;

    private EditText inputText;

    public ChatInputMethodView(Context context) {
        super(context);
        initView(context);
    }

    public ChatInputMethodView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ChatInputMethodView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }


    private void initView(Context context) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayoutInflater.inflate(R.layout.chat_input_method_layout, this);

        record_change_icon = (ImageView)findViewById(R.id.record_change_icon);
        smile_icon = findViewById(R.id.smile_icon);
        press_to_talk = findViewById(R.id.press_to_talk);
        add_icon = findViewById(R.id.add_icon);
        send_btn = findViewById(R.id.send_btn);
        inputText = (EditText) findViewById(R.id.txt_input);
        input_layout = findViewById(R.id.input_layout);
        //测试
    }


    public ImageView getChatChangeView(){
        return record_change_icon;
    }

    public View getSmileView(){
        return smile_icon;
    }

    public View getTalkView(){
        return press_to_talk;
    }

    public View getAddView(){
        return add_icon;
    }

    public View getSendView(){
        return send_btn;
    }

    public EditText getInputEditText(){
        return inputText;
    }


    public View getInputParentlayout() {
        return input_layout;
    }
}
