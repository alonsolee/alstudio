package alstudio.alstudiochatlib.chat;

import android.content.Context;
import android.view.View;

import alstudio.alstudiochatlib.R;
import alstudio.alstudiochatlib.chat.emoji.Emoji;
import alstudio.alstudiochatlib.chat.emoji.EmojiClickListener;
import alstudio.alstudiochatlib.chat.emoji.EmojiUtils;


/**
 * Created by Alonso Lee on 2014/12/23.
 */
public class ChatInputManager extends ChatInputLayoutEngine {


    private ChatOptView optView;
    private ChatInputMethodView inputMethodView;
    private ChatActionListener chatActionListener;
    private Context mContext;

    public static ChatInputManager build() {
        return new ChatInputManager();
    }


    @Override
    public void init(Context context, int keyboardHeight) {
        super.init(context, keyboardHeight);
        mContext = context;
        setInputView(new BaseInputView(context));
        initInputMethodView(context);
        initOptView(context);
    }


    private void initOptView(Context context) {
        optView = new ChatOptView(context);
        optView.initEmojiLayout(context, EmojiUtils.getDefault().getDefaultEmojis(), emojiClickListener);
        inputView.setFootForOpt(optView);
    }


    private boolean isAudioRecordActived = false;

    private void initInputMethodView(Context context) {
        inputMethodView = new ChatInputMethodView(context);

        //表情点击事件
        inputMethodView.getSmileView().setOnClickListener(smileViewClickListener);

        //切换语音与文字输入布局事件
        inputMethodView.getChatChangeView().setOnClickListener(changeViewClickListener);

        //输入框焦点切换事件
        inputMethodView.getInputEditText().setOnFocusChangeListener(inputEditTextFocusChangeListener);
        //点击更多事件
        inputMethodView.getAddView().setOnClickListener(addBtnClickListener);
        //发送按钮点击事件
        inputMethodView.getSendView().setOnClickListener(sendTxtBtnListener);

        inputView.setInputEditText(inputMethodView.getInputEditText());
        setInputEditText(inputView.getInputEditText());
        inputView.setFootForInput(inputMethodView);
    }

    private View.OnClickListener smileViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isOptCoverShown()) {
                if (isKeyboardShown()) {
                    hideKeyBoard();
                } else {
                    if (optView.isOptShown()) {
                        optView.showEmoji();
                    } else if (optView.isEmojiShown()) {
                        showKeyBoard();
                    } else
                        hideInputView();
                }
            } else {
                showOptCover();
                optView.showEmoji();

            }
        }
    };

    private View.OnClickListener changeViewClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            isAudioRecordActived = !isAudioRecordActived;
            if (isAudioRecordActived) {
                inputMethodView.getChatChangeView().setImageResource(R.drawable.chatting_setmode_keyboard_btn_normal);
                inputMethodView.getTalkView().setVisibility(View.VISIBLE);
                hideInputView();
            } else {
                inputMethodView.getTalkView().setVisibility(View.GONE);
                inputMethodView.getChatChangeView().setImageResource(R.drawable.chatting_setmode_voice_btn_normal);
                inputMethodView.getInputEditText().performClick();
                showKeyBoard();
            }
        }
    };


    private View.OnFocusChangeListener inputEditTextFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                inputMethodView.getInputParentlayout().setBackgroundResource(R.drawable.input_bar_bg_active);
            } else {
                inputMethodView.getInputParentlayout().setBackgroundResource(R.drawable.input_bar_bg_normal);
            }
        }
    };

    private View.OnClickListener addBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //显示操作框并隐藏表情框以及按住说话
            isAudioRecordActived = false;
            inputMethodView.getTalkView().setVisibility(View.GONE);
            inputMethodView.getChatChangeView().setImageResource(R.drawable.chatting_setmode_voice_btn_normal);
            showOptCover();
            optView.showOpt();
        }
    };

    private View.OnClickListener sendTxtBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    /**
     * ************************************************************ 表情回调 ***********************************************************
     */
    private EmojiClickListener emojiClickListener = new EmojiClickListener() {
        @Override
        public void onEmojiClick(Emoji emoji) {

            if (emoji.getTxt().equals(Emoji.DELETE_EMOJI_TXT)) {
                //删除表情
                disPatchEditTextDeletEvent();
            }else{
                inputMethodView.getInputEditText().append(EmojiUtils.getDefault().getSmiledText(mContext,emoji.getTxt()));
            }
        }
    };


}
