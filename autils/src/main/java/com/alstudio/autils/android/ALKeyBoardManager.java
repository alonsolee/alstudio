package com.alstudio.autils.android;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;


/**
 * 键盘显示、隐藏切换管理
 *
 * @author Alonso Lee
 */
public class ALKeyBoardManager {

    /**
     * 显示键盘
     *
     * @param activity
     */
    public static void showKeyBoard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
    }

    /**
     * 隐藏键盘
     *
     * @param activity
     */
    public static void dismissKeyBoard(Activity activity) {
        // Hide the keyboard
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow((activity)
                            .getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void toggle(Activity context) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

    }

    public interface ALKeyBoardStateListener {
        public void onKeyBoardShow();

        public void onKeyBoardDismiss();
    }

}
