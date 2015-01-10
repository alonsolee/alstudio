package alstudio.alstudiochatlib.chat.emoji;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.ArrayList;

import alstudio.alstudiochatlib.R;


public class EmoticonsGridAdapter extends BaseAdapter{
	
	private ArrayList<Emoji> emojis;
	private int pageNumber;
    private Context mContext;
	
	private EmojiClickListener mListener;
    private LayoutInflater inflater;
	public EmoticonsGridAdapter(Context context, ArrayList<Emoji> emojis, int pageNumber, EmojiClickListener listener) {
		this.mContext = context;
		this.emojis = emojis;
		this.pageNumber = pageNumber;
		this.mListener = listener;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
	public View getView(int position, View convertView, ViewGroup parent){

		View v = convertView;
		if (v == null) {
			v = inflater.inflate(R.layout.emoticons_item, null);
		}
		
		Emoji emoji = emojis.get(position);
		
		ImageView image = (ImageView) v.findViewById(R.id.item);
        image.setImageResource(emoji.getIconRes());
        image.setTag(emoji);
		image.setOnClickListener(emojiClickListener);


		return v;
	}
	
	@Override
	public int getCount() {		
		return emojis.size();
	}
	
	@Override
	public Emoji getItem(int position) {
		return emojis.get(position);
	}
	
	@Override
	public long getItemId(int position) {		
		return position;
	}
	
	public int getPageNumber () {
		return pageNumber;
	}

    private OnClickListener emojiClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            mListener.onEmojiClick((Emoji) v.getTag());

        }
    };
}
