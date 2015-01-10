package alstudio.alstudiolib.common.utils.android;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间格式化工具类
 * Created by Alonso Lee on 2014/12/18.
 */
public class TimeUtil {

    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 将时间戳转化为指定日期格式的字符串
     *
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    /**
     * 将时间戳转为缺省日期格式"yyyy-MM-dd HH:mm:ss"的字符串
     *
     * @param timeInMillis
     * @return
     */
    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
    }

    /**
     * 获取当前毫秒时间戳
     *
     * @return
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前毫秒时间戳并转为缺省日期格式"yyyy-MM-dd HH:mm:ss"的字符串
     *
     * @return
     */
    public static String getCurrentTimeInString() {
        return getTime(getCurrentTimeInLong());
    }

    /**
     * 获取当前毫秒时间戳并转为指定日期格式的字符串
     *
     * @return
     */
    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
        return getTime(getCurrentTimeInLong(), dateFormat);
    }

}
