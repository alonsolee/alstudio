package alstudio.alstudiochatlib.chat;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Alonso Lee on 2014/12/23.
 */
public class ChatInputLayoutEngine implements View.OnTouchListener {

    /**
     * 待添加聊天输入框的根布局
     */
    private View parentView;

    /**
     * 键盘高度
     */
    private int keyboardHeight = 0;
    /**
     * 输入框底部的操作框架
     */
    private View optCover;

    private int previousDiffrence = 0;

    private Context context;

    private Activity mActivity;

    /**
     * 输入布局
     */
    protected BaseInputView inputView;

    private boolean isHideInputViewOnTouch = true;

    protected LayoutInflater mLayoutInflater;

    private boolean isTouchToDismiss = false;

    private boolean isKeyboardShown;

    private boolean isOptCoverShowRequest = false;

    private EditText mInputEditText;

    private boolean isOptCoverShown;


    private InputMethodManager imm = null;

    private KeyBoardHeightChangeListener keyBoardHeightChangeListener;
    private InputViewShowChangeListener inputViewShowChangeListener;


    /**
     * 修改输入框底部操作框的高度
     *
     * @param height
     */
    private void changeOptCoverHeight(int height) {

        if (keyBoardHeightChangeListener != null) {
            keyBoardHeightChangeListener.onKeyBoardHeightChange(height);
        }

        setOptCoverLayoutParams(height);
    }

    private void setOptCoverLayoutParams(int height) {
        if (optCover == null)
            return;
        if (height > 100) {
            keyboardHeight = height;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, keyboardHeight);
            optCover.setLayoutParams(params);
        }

    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context, int keyboardHeight) {
        setContext(context);

        imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        this.keyboardHeight = keyboardHeight;
        if (mLayoutInflater == null) {
            mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
    }

    /**
     * 键盘高度监听器
     *
     * @param listener
     */
    public void setKeyBoardHeightChangeListener(KeyBoardHeightChangeListener listener) {
        keyBoardHeightChangeListener = listener;
    }

    /**
     * 设置输入view显示、隐藏监听器
     *
     * @param listener
     */
    public void setInputViewShowChangeListener(InputViewShowChangeListener listener) {
        inputViewShowChangeListener = listener;
    }

    /**
     * 设置键盘高度
     *
     * @param keyboardHeight
     */
    public void setKeyboardHeight(int keyboardHeight) {
        this.keyboardHeight = keyboardHeight;
    }

    /**
     * 设置全局上下文
     *
     * @param context
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * 设置要加入的根布局对象。根据根布局获取键盘弹起、隐藏、键盘高度
     *
     * @param parentView
     */
    private void setParentView(View parentView) {
        this.parentView = parentView;
    }

    public void setInputView(BaseInputView view) {
        inputView = view;
        setParentView(inputView.getParentView());
        optCover = inputView.getFootForOpt();
        mInputEditText = inputView.getInputEditText();
    }

    public void setInputEditText(EditText editText) {
        this.mInputEditText = editText;
    }

    /**
     * 获取聊天输入布局
     *
     * @return
     */
    public BaseInputView getInputView() {
        return inputView;
    }

    /**
     * 设置加入输入布局的界面对象
     *
     * @param activity
     */
    public void setActivity(Activity activity) {
        mActivity = activity;
    }

    /**
     * 将输入布局添加到界面
     *
     * @param activity 待添加的界面
     */
    public void attach(Activity activity) {
        setOptCoverLayoutParams(keyboardHeight);
        setActivity(activity);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        activity.addContentView(inputView, params);
        startWatchKeyboardState();
        inputView.getTouchDismissLayout().setOnTouchListener(this);
        setSoftInputMode(true);
        if(mInputEditText == null){
            throw new IllegalArgumentException("输入框绝壁不能为空");
        }
        mInputEditText.setOnClickListener(editTextClickListener);
    }

    /**
     * 将输入布局添加到界面
     *
     * @param activity 待添加的界面
     */
    public void attach(Activity activity,ViewGroup viewGroup) {
        setOptCoverLayoutParams(keyboardHeight);
        setActivity(activity);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        viewGroup.addView(inputView,params);
        startWatchKeyboardState();
        inputView.getTouchDismissLayout().setOnTouchListener(this);
        setSoftInputMode(true);
        if(mInputEditText == null){
            throw new IllegalArgumentException("输入框绝壁不能为空");
        }
        mInputEditText.setOnClickListener(editTextClickListener);
    }

    /**
     * 将输入布局添加到界面。注意，请一定要在调用该方法之前调用setActivity方法
     */
    public void attach() {
        attach(mActivity);
    }

    /**
     * 解除引用
     */
    public void detach() {
        mActivity = null;
        inputViewShowChangeListener = null;
    }

    /**
     * 设置是否点击空白区域隐藏输入布局
     *
     * @param enable
     */
    public void setHideInputViewOnTouch(boolean enable) {
        isHideInputViewOnTouch = enable;
    }

    /**
     * 检测是否点击空白区域隐藏布局
     *
     * @return
     */
    public boolean isHideInputViewOnTouch() {
        return isHideInputViewOnTouch;
    }

    /**
     * 设置输入区背景颜色
     *
     * @param color
     */
    public void setFootForInputBgColor(int color) {
        inputView.setFootForInputBgColor(color);
    }

    /**
     * 设置输入区背景图片资源
     *
     * @param res
     */
    public void setFootForInputBgRes(int res) {
        inputView.setFootForInputBgRes(res);
    }

    /**
     * 设置操作区背景颜色
     *
     * @param color
     */
    public void setFootForOptBgColor(int color) {
        inputView.setFootForOptBgColor(color);
    }

    /**
     * 设置操作区背景图片资源
     *
     * @param res
     */
    public void setFootForOptBgRes(int res) {
        inputView.setFootForOptBgRes(res);
    }

    /**
     * 开始监听键盘高度变化
     */
    private void startWatchKeyboardState() {
        parentView.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);
    }

    /**
     * 显示操作框
     */
    public void showOptCover() {
        isOptCoverShowRequest = true;
        hideKeyBoard();
        {
            isOptCoverShown = true;
            if(inputViewShowChangeListener != null){
                inputViewShowChangeListener.onShow();
            }
            if (optCover != null) {
                optCover.setVisibility(View.VISIBLE);
            }
            setSoftInputMode(false);
        }
    }

    /**
     * 隐藏操作框
     */
    public void hideOptCover() {
        isTouchToDismiss = true;
        isOptCoverShowRequest = false;
        if (optCover != null) {
            optCover.setVisibility(View.GONE);
        }
        if(inputViewShowChangeListener != null){
            inputViewShowChangeListener.onHide();
        }
        isOptCoverShown = false;

        if(isKeyboardShown){
            setSoftInputMode(true);
        }

    }

    /**
     * 显示键盘
     */
    public void showKeyBoard() {
        isKeyboardShown = true;
//        setSoftInputMode(true);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
    }

    /**
     * 隐藏键盘
     */
    public void hideKeyBoard() {
        isKeyboardShown = false;
        imm.hideSoftInputFromWindow(((Activity) context)
                        .getCurrentFocus().getWindowToken(),
                0);

    }

    /**
     * 检测操作课是否显示
     *
     * @return
     */
    public boolean isOptCoverShown() {
        return isOptCoverShown;
    }

    /**
     * 检测键盘是否显示
     *
     * @return
     */
    public boolean isKeyboardShown() {
        return isKeyboardShown;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        onTouchDismiss();
        return false;
    }

    /**
     * 点击空白处隐藏输入布局
     */
    public void onTouchDismiss() {
        if (isHideInputViewOnTouch) {
            hideInputView();
        }
    }

    /**
     * 隐藏输入布局
     */
    public final void hideInputView() {
        isTouchToDismiss = true;
        hideOptCover();
        hideKeyBoard();
        setSoftInputMode(true);
        if(inputViewShowChangeListener != null){
            inputViewShowChangeListener.onHide();
        }
    }

    /**
     * 执行输入框的删除事件
     */
    public void disPatchEditTextDeletEvent(){
        if(mInputEditText != null){
            KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
            mInputEditText.dispatchKeyEvent(event);
        }
    }


    /**
     * 处理返回键事件
     */
    public boolean onBackKeyPressed() {
        if (isOptCoverShown() || isKeyboardShown()) {
            hideInputView();
            return true;
        }
        return false;
    }

    /**
     * 计算键盘是否显示，检查是否需要弹出操作框
     */
    private synchronized void calcKeyBoardHeight() {
        Rect r = new Rect();
        parentView.getWindowVisibleDisplayFrame(r);

        int screenHeight = parentView.getRootView()
                .getHeight();
        int heightDifference = screenHeight - (r.bottom);

        previousDiffrence = heightDifference;

        if (heightDifference > 100) {
            changeOptCoverHeight(heightDifference);
            parentView.getViewTreeObserver().removeGlobalOnLayoutListener(globalLayoutListener);
        }
    }

    private void setSoftInputMode(boolean isNormal){
        if(mActivity != null){
            if(isNormal){
                mActivity.getWindow()
                        .setSoftInputMode(
                                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                                        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            }else{
                mActivity.getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            }
        }
    }

    private View.OnClickListener editTextClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(inputViewShowChangeListener != null){
                inputViewShowChangeListener.onShow();
            }

            isKeyboardShown = true;
        }
    };

    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {

        @Override
        public void onGlobalLayout() {
            calcKeyBoardHeight();
        }
    };

    /**
     * 键盘高度变化回调
     */
    public interface KeyBoardHeightChangeListener {
        public void onKeyBoardHeightChange(int height);
    }

    public interface InputViewShowChangeListener {
        public void onShow();

        public void onHide();
    }

    public interface ChatActionListener{
        public void onTxtSend(String txt);
        public void onAudioSend(String filePath,int audioLen);
        public void onPicSend(String filePath);
    }


}
