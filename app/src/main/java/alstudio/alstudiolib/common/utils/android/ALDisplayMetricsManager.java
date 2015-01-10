/**
 * 
 */
package alstudio.alstudiolib.common.utils.android;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 获取屏幕高宽、像素密度
 * 
 * @author alonso lee
 * 
 */
public class ALDisplayMetricsManager {
	public static final int SCREEN_HEIGHT_NORMAL = 480;

	/**
	 * 获取屏幕高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Context context) {
		/* 获取手机屏幕分辨率 */
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager winManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		winManager.getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}

	/**
	 * 获取屏幕宽度
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager winManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		winManager.getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	/**
	 * 获取屏幕像素密度
	 * 
	 * @param context
	 * @return
	 */
	public static float getScreenDensity(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager winManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		winManager.getDefaultDisplay().getMetrics(dm);
		return dm.density;
	}

}
