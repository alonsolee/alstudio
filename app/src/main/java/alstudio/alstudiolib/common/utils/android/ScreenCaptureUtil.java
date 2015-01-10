package alstudio.alstudiolib.common.utils.android;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;

public class ScreenCaptureUtil {
	private Bitmap coverBitmap = null;

	public Bitmap prepare(Activity activity, int id) {
		if (coverBitmap != null) {
			coverBitmap.recycle();
		}

		coverBitmap = Bitmap.createBitmap(activity.findViewById(id).getWidth(),
				activity.findViewById(id).getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(coverBitmap);
		activity.findViewById(id).draw(canvas);
		return coverBitmap;
	}

	public Bitmap prepare(Activity activity, int id, int w, int h) {
		coverBitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		Canvas canvas = new Canvas(coverBitmap);
		activity.findViewById(id).draw(canvas);
		return coverBitmap;
	}

	public void recycleBitmap() {
		if (coverBitmap != null) {
			try {
				coverBitmap.recycle();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
