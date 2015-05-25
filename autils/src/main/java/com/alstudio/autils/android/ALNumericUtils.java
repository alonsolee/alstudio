package com.alstudio.autils.android;


/**
 * 将字符串数字转为指定类型的数字
 *
 * @author alonso lee
 */
public final class ALNumericUtils {

    public static int parseInt(String string, int defaultValue) {
        int value;

        try {
            value = Integer.parseInt(string);
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long parseLong(String string, long defaultValue) {
        long value;

        try {
            value = Long.parseLong(string);
        } catch (Exception e) {
            value = defaultValue;
        }
        return value;
    }
}
