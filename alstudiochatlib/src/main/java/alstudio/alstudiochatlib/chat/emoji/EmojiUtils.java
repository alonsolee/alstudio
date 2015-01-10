package alstudio.alstudiochatlib.chat.emoji;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.text.Spannable;
import android.text.style.ImageSpan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import alstudio.alstudiochatlib.R;

/**
 * Created by Alonso LEMOJI on 2015/1/8.
 */
public class EmojiUtils {

    // 环信缺省
//    public static final String EMOJI_0 = "[):]";
//    public static final String EMOJI_1 = "[):]";
//    public static final String EMOJI_2 = "[:D]";
//    public static final String EMOJI_3 = "[;)]";
//    public static final String EMOJI_4 = "[:-o]";
//    public static final String EMOJI_5 = "[:p]";
//    public static final String EMOJI_6 = "[(H)]";
//    public static final String EMOJI_7 = "[:@]";
//    public static final String EMOJI_8 = "[:s]";
//    public static final String EMOJI_9 = "[:$]";
//    public static final String EMOJI_10 = "[:(]";
//    public static final String EMOJI_11 = "[:'(]";
//    public static final String EMOJI_12 = "[:|]";
//    public static final String EMOJI_13 = "[(a)]";
//    public static final String EMOJI_14 = "[8o|]";
//    public static final String EMOJI_15 = "[8-|]";
//    public static final String EMOJI_16 = "[+o(]";
//    public static final String EMOJI_17 = "[<o)]";
//    public static final String EMOJI_18 = "[|-)]";
//    public static final String EMOJI_19 = "[*-)]";
//    public static final String EMOJI_20 = "[:-#]";
//    public static final String EMOJI_21 = "[:-*]";
//    public static final String EMOJI_22 = "[^o)]";
//    public static final String EMOJI_23 = "[8-)]";
//    public static final String EMOJI_24 = "[(|)]";
//    public static final String EMOJI_25 = "[(u)]";
//    public static final String EMOJI_26 = "[(S)]";
//    public static final String EMOJI_27 = "[(*)]";
//    public static final String EMOJI_28 = "[(#)]";
//    public static final String EMOJI_29 = "[(R)]";
//    public static final String EMOJI_30 = "[({)]";
//    public static final String EMOJI_31 = "[(})]";
//    public static final String EMOJI_32 = "[(k)]";
//    public static final String EMOJI_33 = "[(F)]";
//    public static final String EMOJI_34 = "[(W)]";
//    public static final String EMOJI_35 = "[(D)]";


    public static int[] simlepids = {
            R.drawable.absent_minded, R.drawable.angry, R.drawable.applaud,
            R.drawable.crazy, R.drawable.curse, R.drawable.despair,
            R.drawable.disdain, R.drawable.dizzy, R.drawable.doubt,
            R.drawable.glutton, R.drawable.grievance, R.drawable.happy,
            R.drawable.hug, R.drawable.hush, R.drawable.kiss,
            R.drawable.miser, R.drawable.pitiful, R.drawable.proud,
            R.drawable.risus, R.drawable.scorn, R.drawable.shutup,
            R.drawable.shy, R.drawable.sleepy, R.drawable.think,
            R.drawable.titter, R.drawable.unhappy, R.drawable.vomit,
            R.drawable.yawn, R.drawable.face_00, R.drawable.face_01,
            R.drawable.face_02, R.drawable.face_03, R.drawable.face_04,
            R.drawable.face_05, R.drawable.face_06, R.drawable.face_07,
            R.drawable.face_08, R.drawable.face_09, R.drawable.face_10,
            R.drawable.face_11, R.drawable.face_12, R.drawable.face_13,
            R.drawable.face_14, R.drawable.face_15, R.drawable.face_16,
            R.drawable.face_17, R.drawable.face_18, R.drawable.face_19,
            R.drawable.face_20, R.drawable.face_21, R.drawable.face_22,
            R.drawable.face_23, R.drawable.face_24, R.drawable.face_25,
            R.drawable.face_26, R.drawable.face_27, R.drawable.face_28,
            R.drawable.face_29, R.drawable.face_30, R.drawable.face_31,
            R.drawable.face_32, R.drawable.face_33, R.drawable.face_34,
            R.drawable.face_35, R.drawable.face_36, R.drawable.face_37,
            R.drawable.face_38, R.drawable.face_39, R.drawable.face_40,
            R.drawable.face_41, R.drawable.face_42, R.drawable.face_43,
            R.drawable.face_44, R.drawable.face_45, R.drawable.face_46,
            R.drawable.face_47, R.drawable.face_48, R.drawable.face_49,
            R.drawable.face_50, R.drawable.face_51, R.drawable.face_52,
            R.drawable.face_53, R.drawable.face_54, R.drawable.face_55};


    public static String[] smilenames = {"{Absent-minded}", "{Angry}",
            "{Applaud}", "{Crazy}", "{Curse}", "{Despair}", "{Disdain}",
            "{Dizzy}", "{Doubt}", "{Glutton}", "{Grievance}", "{Happy}",
            "{Hug}", "{Hush}", "{Kiss}", "{Miser}", "{Pitiful}", "{Proud}",
            "{Risus}", "{Scorn}", "{Shut up}", "{Shy}", "{Sleepy}", "{Think}",
            "{Titter}", "{Unhappy}", "{Vomit}", "{Yawn}", "{00}", "{01}",
            "{02}", "{03}", "{04}", "{05}", "{06}", "{07}", "{08}", "{09}",
            "{10}", "{11}", "{12}", "{13}", "{14}", "{15}", "{16}", "{17}",
            "{18}", "{19}", "{20}", "{21}", "{22}", "{23}", "{24}", "{25}",
            "{26}", "{27}", "{28}", "{29}", "{30}", "{31}", "{32}", "{33}",
            "{34}", "{35}", "{36}", "{37}", "{38}", "{39}", "{40}", "{41}",
            "{42}", "{43}", "{44}", "{45}", "{46}", "{47}", "{48}", "{49}",
            "{50}", "{51}", "{52}", "{53}", "{54}", "{55}"};

    private static ArrayList<Emoji> defaultEmoji = new ArrayList<Emoji>();

    private static final Spannable.Factory spannableFactory = Spannable.Factory
            .getInstance();

    private static final Map<Pattern, Integer> emoticons = new HashMap<Pattern, Integer>();

    private static void initEmojis() {
        int len = smilenames.length;
        for (int i = 0; i < len; i++) {
            Emoji emoji = new Emoji();
            emoji.setTxt(smilenames[i]);
            emoji.setIconRes(simlepids[i]);
            defaultEmoji.add(emoji);
            addPattern(emoticons, smilenames[i], simlepids[i]);
        }
    }

    static {
        initEmojis();
        //环信缺省
//        addPattern(emoticons, EMOJI_1, R.drawable.EMOJI_1);
//        addPattern(emoticons, EMOJI_2, R.drawable.EMOJI_2);
//        addPattern(emoticons, EMOJI_3, R.drawable.EMOJI_3);
//        addPattern(emoticons, EMOJI_4, R.drawable.EMOJI_4);
//        addPattern(emoticons, EMOJI_5, R.drawable.EMOJI_5);
//        addPattern(emoticons, EMOJI_6, R.drawable.EMOJI_6);
//        addPattern(emoticons, EMOJI_7, R.drawable.EMOJI_7);
//        addPattern(emoticons, EMOJI_8, R.drawable.EMOJI_8);
//        addPattern(emoticons, EMOJI_9, R.drawable.EMOJI_9);
//        addPattern(emoticons, EMOJI_10, R.drawable.EMOJI_10);
//        addPattern(emoticons, EMOJI_11, R.drawable.EMOJI_11);
//        addPattern(emoticons, EMOJI_12, R.drawable.EMOJI_12);
//        addPattern(emoticons, EMOJI_13, R.drawable.EMOJI_13);
//        addPattern(emoticons, EMOJI_14, R.drawable.EMOJI_14);
//        addPattern(emoticons, EMOJI_15, R.drawable.EMOJI_15);
//        addPattern(emoticons, EMOJI_16, R.drawable.EMOJI_16);
//        addPattern(emoticons, EMOJI_17, R.drawable.EMOJI_17);
//        addPattern(emoticons, EMOJI_18, R.drawable.EMOJI_18);
//        addPattern(emoticons, EMOJI_19, R.drawable.EMOJI_19);
//        addPattern(emoticons, EMOJI_20, R.drawable.EMOJI_20);
//        addPattern(emoticons, EMOJI_21, R.drawable.EMOJI_21);
//        addPattern(emoticons, EMOJI_22, R.drawable.EMOJI_22);
//        addPattern(emoticons, EMOJI_23, R.drawable.EMOJI_23);
//        addPattern(emoticons, EMOJI_24, R.drawable.EMOJI_24);
//        addPattern(emoticons, EMOJI_25, R.drawable.EMOJI_25);
//        addPattern(emoticons, EMOJI_26, R.drawable.EMOJI_26);
//        addPattern(emoticons, EMOJI_27, R.drawable.EMOJI_27);
//        addPattern(emoticons, EMOJI_28, R.drawable.EMOJI_28);
//        addPattern(emoticons, EMOJI_29, R.drawable.EMOJI_29);
//        addPattern(emoticons, EMOJI_30, R.drawable.EMOJI_30);
//        addPattern(emoticons, EMOJI_31, R.drawable.EMOJI_31);
//        addPattern(emoticons, EMOJI_32, R.drawable.EMOJI_32);
//        addPattern(emoticons, EMOJI_33, R.drawable.EMOJI_33);
//        addPattern(emoticons, EMOJI_34, R.drawable.EMOJI_34);
//        addPattern(emoticons, EMOJI_35, R.drawable.EMOJI_35);
    }

    private static void addPattern(Map<Pattern, Integer> map, String smile,
                                   int resource) {
        map.put(Pattern.compile(Pattern.quote(smile)), resource);
    }

    /**
     * replace existing spannable with smiles
     *
     * @param context
     * @param spannable
     * @return
     */
    public boolean addSmiles(Context context, Spannable spannable) {
        boolean hasChanges = false;
        for (Map.Entry<Pattern, Integer> entry : emoticons.entrySet()) {
            Matcher matcher = entry.getKey().matcher(spannable);
            while (matcher.find()) {
                boolean set = true;
                for (ImageSpan span : spannable.getSpans(matcher.start(), matcher.end(), ImageSpan.class))
                    if (spannable.getSpanStart(span) >= matcher.start()
                            && spannable.getSpanEnd(span) <= matcher.end())
                        spannable.removeSpan(span);
                    else {
                        set = false;
                        break;
                    }
                if (set) {
                    hasChanges = true;
                    Drawable d = context.getResources().getDrawable(entry.getValue());
                    d.setBounds(0,0,d.getIntrinsicWidth()/2,d.getIntrinsicHeight()/2);
                    ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
//                    spannable.setSpan(new ImageSpan(context, entry.getValue()),
//                            matcher.start(), matcher.end(),
//                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    spannable.setSpan(span,
                            matcher.start(), matcher.end(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                }
            }
        }
        return hasChanges;
    }

    public Spannable getSmiledText(Context context, CharSequence text) {
        Spannable spannable = spannableFactory.newSpannable(text);
        addSmiles(context, spannable);
        return spannable;
    }

    public boolean containsKey(String key) {
        boolean b = false;
        for (Map.Entry<Pattern, Integer> entry : emoticons.entrySet()) {
            Matcher matcher = entry.getKey().matcher(key);
            if (matcher.find()) {
                b = true;
                break;
            }
        }
        return b;
    }

    private static EmojiUtils instance;


    public static EmojiUtils getDefault() {
        if (instance == null) {
            instance = new EmojiUtils();
        }
        return instance;
    }

    public ArrayList<Emoji> getDefaultEmojis(){
        return defaultEmoji;
    }

}
