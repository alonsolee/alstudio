package alstudio.alstudiochatlib.chat;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;

import alstudio.alstudiochatlib.R;
import alstudio.alstudiochatlib.chat.emoji.Emoji;
import alstudio.alstudiochatlib.chat.emoji.EmojiClickListener;
import alstudio.alstudiochatlib.chat.emoji.EmoticonsPagerAdapter;

/**
 * Created by Administrator on 2015/1/7.
 */
public class ChatOptView extends FrameLayout {

    private LayoutInflater mLayoutInflater;

    private ViewPager emoji_layout;
    private View opt_layout;

    private EmoticonsPagerAdapter emoticonsPagerAdapter;


    public ChatOptView(Context context) {
        super(context);
        init(context);
    }

    public ChatOptView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ChatOptView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayoutInflater.inflate(R.layout.chat_opt_view_layout, this);

        opt_layout = findViewById(R.id.opt_layout);
        emoji_layout = (ViewPager) findViewById(R.id.emoji_layout);
        hideAll();

    }

    public void initEmojiLayout(Context context,
                                ArrayList<Emoji> emojis, EmojiClickListener listener) {
        emoticonsPagerAdapter = new EmoticonsPagerAdapter(context, emojis, listener);
        emoji_layout.setAdapter(emoticonsPagerAdapter);
    }

    public ViewPager getEmojiLayout() {
        return emoji_layout;
    }

    public View getOptLayout() {
        return opt_layout;
    }

    public void hideAll() {
        emoji_layout.setVisibility(View.GONE);
        opt_layout.setVisibility(View.GONE);
    }

    public void showEmoji() {
        emoji_layout.setVisibility(View.VISIBLE);
        opt_layout.setVisibility(View.GONE);
    }

    public void showOpt() {
        emoji_layout.setVisibility(View.GONE);
        opt_layout.setVisibility(View.VISIBLE);
    }

    public boolean isOptShown() {
        return opt_layout.getVisibility() == View.VISIBLE ? true : false;
    }

    public boolean isEmojiShown(){
        return emoji_layout.getVisibility() == View.VISIBLE ? true : false;
    }
}
