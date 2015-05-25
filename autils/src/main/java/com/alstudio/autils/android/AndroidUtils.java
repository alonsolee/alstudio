package com.alstudio.autils.android;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Debug;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.alstudio.autils.encode.ALMd5;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.Locale;


/**
 * Android 常用工具类
 *
 * @author Alonso Lee
 */
public class AndroidUtils {



    /**
     * 获取网络连接管理器实例对象
     *
     * @param ctx
     * @return
     */
    public static ConnectivityManager getConnectivityManager(Context ctx) {
        return (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    /**
     * 获取WIFI管理器实例对象
     *
     * @param ctx
     * @return
     */
    public static WifiManager getWifiManager(Context ctx) {
        return (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
    }

    /**
     * 获取电话管理器实例对象
     *
     * @param ctx
     * @return
     */
    public static TelephonyManager getTelephonyManager(Context ctx) {
        return (TelephonyManager) ctx
                .getSystemService(Context.TELEPHONY_SERVICE);
    }

    /**
     * 获取定位管理器实例对象
     *
     * @param ctx
     * @return
     */
    public static LocationManager getLocationManager(Context ctx) {
        return (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
    }

    /**
     * 检测当前是否有可用数据网络
     *
     * @param context
     * @return true表示当前有可用数据网络
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            return activeNetworkInfo.isConnected();
        }
        return false;
    }

    /**
     * 检测当前wifi网络是否可用
     *
     * @param context
     * @return true表示当前wifi网络已连接
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetworkInfo != null) {
            return wifiNetworkInfo.isConnected();
        }
        return false;
    }

    /**
     * 获取手机设备名
     *
     * @return
     */
    public String getDeviceModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取系统sdk版本号
     *
     * @return
     */
    public String getSystemSdkVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取系统当前语言
     *
     * @return
     */
    public String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取系统当前国家位置设定信息
     *
     * @return
     */
    public String getCountry() {
        return Locale.getDefault().getCountry();
    }


    /**
     * 将图片文件添加到相册
     *
     * @param context
     * @param path
     * @param name
     */
    public static void AddPicToGallery(final Context context,
                                       final String path, final String name) {

        new Thread() {

            public void run() {
                try {
                    MediaStore.Images.Media.insertImage(
                            context.getContentResolver(), path, name, name);
                    context.sendBroadcast(new Intent(
                            Intent.ACTION_MEDIA_MOUNTED,
                            Uri.parse("file://"
                                    + Environment.getExternalStorageDirectory())));
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            ;

        }.start();
    }

    /**
     * 发送电子邮件
     *
     * @param context       上下文
     * @param targetAddress 收件人地址
     * @param title         邮件标题
     * @param body          邮件正文
     */
    public static boolean sendEmail(Context context, String targetAddress,
                                    String title, String body) {

        try {
            Uri uri = Uri.parse("mailto:" + targetAddress);
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
            context.startActivity(emailIntent);
            return true;
        } catch (Exception e) {
        }
        return false;

    }

    /**
     * 发送短信
     *
     * @param ctx
     * @param phoneNumber
     * @param message
     */
    public static boolean sendSms(Context ctx, String phoneNumber, String message) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"
                    + phoneNumber));
            intent.putExtra("sms_body", message);
            ctx.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取内存使用详情
     *
     * @return
     */
    public static String showRuntimeMemory() {

        Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
        Debug.getMemoryInfo(memoryInfo);
        String memMessage = String.format(
                "Memory: Pss=%.2f MB,Private=%.2f MB, Shared=%.2f MB",
                memoryInfo.getTotalPss() / 1024.0,
                memoryInfo.getTotalPrivateDirty() / 1024.0,
                memoryInfo.getTotalSharedDirty() / 1024.0);
        return memMessage;
    }


    public boolean isApkRePackage(String defaultMd5) {
        if (TextUtils.isEmpty(defaultMd5))
            return false;
        return true;
    }

    public static String getApkSignatureMD5(String archiveFilePath, Context context) throws Exception {

        Class clazz = Class.forName("android.content.pm.PackageParser");
        Method parsePackageMethod = clazz.getMethod("parsePackage", File.class, String.class, DisplayMetrics.class, int.class);

        Object packageParser = clazz.getConstructor(String.class).newInstance("");
        Object packag = parsePackageMethod.invoke(packageParser, new File(archiveFilePath), null, context.getResources().getDisplayMetrics(), 0x0004);

        Method collectCertificatesMethod = clazz.getMethod("collectCertificates", Class.forName("android.content.pm.PackageParser$Package"), int.class);
        collectCertificatesMethod.invoke(packageParser, packag, PackageManager.GET_SIGNATURES);
        Signature signatures[] = (Signature[]) packag.getClass().getField("mSignatures").get(packag);

        Signature signature = signatures.length > 0 ? signatures[0] : null;
        if (signature == null)
            return null;
        return ALMd5.encode(signature.toCharsString());
    }

}
