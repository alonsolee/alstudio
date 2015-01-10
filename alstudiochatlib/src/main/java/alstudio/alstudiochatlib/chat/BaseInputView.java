package alstudio.alstudiochatlib.chat;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import alstudio.alstudiochatlib.R;


/**
 * Created by Alonso Lee on 2014/12/23.
 */
public class BaseInputView extends RelativeLayout {

    private Context context;
    private LayoutInflater mLayoutInflater;
    private LinearLayout footParent;
    private LinearLayout footForInput;
    private LinearLayout footForOpt;
    private View parent;
    private View touch_dismiss_layout;
    private EditText inputEditText;

    public BaseInputView(Context context) {
        super(context);
        init(context);
    }

    public BaseInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @TargetApi(11)
    public BaseInputView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context){
        this.context = context;
        mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayoutInflater.inflate(R.layout.base_input_layout,this);
        parent = findViewById(R.id.base_input_layout_parent);
        footParent = (LinearLayout)findViewById(R.id.foot_parent);
        footForInput = (LinearLayout)findViewById(R.id.foot_for_input);
        footForOpt = (LinearLayout)findViewById(R.id.footer_for_opt);
        touch_dismiss_layout = findViewById(R.id.touch_dismiss_layout);
    }

    public View getParentView(){
        return parent;
    }

    public void setFootForInputBgColor(int color){
        footForInput.setBackgroundColor(color);
    }

    public void setFootForInputBgRes(int res){
        footForInput.setBackgroundResource(res);
    }

    public void setFootForOptBgColor(int color){
        footForOpt.setBackgroundColor(color);
    }

    public void setFootForOptBgRes(int res){
        footForOpt.setBackgroundResource(res);
    }

    public void setFootForInput(View v){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        footForInput.addView(v,params);
    }

    public void setFootForOpt(View v){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        footForOpt.addView(v,params);
    }

    public View getFootForOpt(){
        return footForOpt;
    }

    public View getFootForInput(){
        return footForInput;
    }

    public View getTouchDismissLayout(){
        return touch_dismiss_layout;
    }

    public EditText getInputEditText(){
        return inputEditText;
    }

    public void setInputEditText(EditText inputEditText){
        this.inputEditText = inputEditText;
    }



}
