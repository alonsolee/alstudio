package alstudio.alstudiochatlib.chat.emoji;

import alstudio.alstudiochatlib.R;

/**
 * Created by Alonso Lee on 2015/1/8.
 */
public class Emoji {
    private String txt;
    private int type = DEFAULT_EMOJI;
    public final static int DEFAULT_EMOJI = 0;
    private int iconRes = 0;
    public static final String DELETE_EMOJI_TXT = "*delete_emoji*";
    public static final int DELETE_EMOJI_ICON_RES = R.drawable.delete_emoji;

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }
}
