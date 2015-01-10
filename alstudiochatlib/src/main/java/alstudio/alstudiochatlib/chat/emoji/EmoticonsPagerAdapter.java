package alstudio.alstudiochatlib.chat.emoji;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;

import alstudio.alstudiochatlib.R;


public class EmoticonsPagerAdapter extends PagerAdapter {

    private ArrayList<Emoji> emojis;
	private static final int NO_OF_EMOTICONS_PER_PAGE = 20;
    private Context context;
    private EmojiClickListener mListener;

    private LayoutInflater mLayoutInflater;

    private ArrayList<View> emojiViews = new ArrayList<View>();


    public EmoticonsPagerAdapter(Context context,
			ArrayList<Emoji> emojis, EmojiClickListener listener) {
		this.emojis = emojis;
		this.context = context;
		this.mListener = listener;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int sumPage = (int) Math.ceil((double) emojis.size()
                / (double) NO_OF_EMOTICONS_PER_PAGE);
        for(int i = 0;i < sumPage;i++){
            int initialPosition = i * NO_OF_EMOTICONS_PER_PAGE;
            ArrayList<Emoji> emoticonsInAPage = new ArrayList<Emoji>();

            for (int j = initialPosition; j < initialPosition
                    + NO_OF_EMOTICONS_PER_PAGE
                    && j < emojis.size(); j++) {
                emoticonsInAPage.add(emojis.get(j));
            }
            Emoji emoji = new Emoji();
            emoji.setTxt(Emoji.DELETE_EMOJI_TXT);
            emoji.setIconRes(Emoji.DELETE_EMOJI_ICON_RES);
            emoticonsInAPage.add(emoji);
            View layout = mLayoutInflater.inflate(
                    R.layout.expression_gridview, null);
            GridView gridView = (GridView) layout.findViewById(R.id.gridview);

            EmoticonsGridAdapter adapter = new EmoticonsGridAdapter(
                    context, emoticonsInAPage, i,
                    mListener);
            gridView.setAdapter(adapter);

            emojiViews.add(gridView);
        }
    }

	@Override
	public int getCount() {
		return (int) Math.ceil((double) emojis.size()
				/ (double) NO_OF_EMOTICONS_PER_PAGE);
	}

	@Override
	public Object instantiateItem(View collection, int position) {

        ((ViewPager) collection).removeView(emojiViews.get(position));
        ((ViewPager) collection).addView(emojiViews.get(position));

        return emojiViews.get(position);
	}

	@Override
	public void destroyItem(View collection, int position, Object view) {
		((ViewPager) collection).removeView((View) view);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}
}