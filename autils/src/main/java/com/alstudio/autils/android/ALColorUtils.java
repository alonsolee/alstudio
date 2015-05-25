package com.alstudio.autils.android;

import android.content.Context;
import android.graphics.Color;

/**
 * 颜色转换器
 *
 * @author Alonso Lee
 */
public class ALColorUtils {

    public static int getColor(Context context, int colorRes) {
        return context.getResources().getColor(colorRes);
    }

    public static int parseColor(String color) {
        if (!color.startsWith("#")) {
            color = "#" + color;
        }
        return Color.parseColor(color);
    }

}
