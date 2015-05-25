package com.alstudio.autils.android;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Alonso Lee on 2014/12/18.
 */
public class AppUtils {


    /**
     * 获取AndroidManifests中的meta节点数据
     *
     * @param context
     * @param metaKey
     * @return
     */
    public static String getMetaValue(Context context, String metaKey) {
        Bundle metaData = null;
        String apiKey = null;
        if (context == null || metaKey == null) {
            return null;
        }
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                apiKey = metaData.getString(metaKey);
            }
        } catch (PackageManager.NameNotFoundException e) {

        }
        return apiKey;
    }

    /**
     * 跳转到google play评价软件
     *
     * @param context
     */
    public static void rateApplication(Activity context) {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri
                    .parse("http://play.google.com/store/apps/details?id="
                            + context.getPackageName())));
        }
    }


    /**
     * 检测应用是否在前台运行
     *
     * @param context
     * @return true 正在前台运行；false 已经退到后台运行
     */
    public static boolean isApplicationInForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(1);
        if (taskList != null && !taskList.isEmpty()) {
            ComponentName topActivity = taskList.get(0).topActivity;
            if (topActivity != null && topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;

    }

    /**
     * 获取栈顶activity intent
     *
     * @return
     */
    public static Intent getLastActivity(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RecentTaskInfo> rti = am.getRecentTasks(10, 0);
        List<ActivityManager.RunningTaskInfo> rts = am.getRunningTasks(5);

        String packageName = context.getPackageName();

        int pid = -1;

        if (rts != null && rts.size() > 0) {
            if (rts != null && rts.size() > 0) {
                for (int i = 0; i < rts.size(); i++) {
                    try {
                        if (packageName.equals(rts.get(i).baseActivity
                                .getPackageName())) {
                            pid = rts.get(i).id;
                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if (pid != -1) {
            if (rti != null && rti.size() > 0) {
                for (int i = 0; i < rti.size(); i++) {
                    if (rti.get(i).baseIntent.getComponent().getPackageName()
                            .equals(packageName)) {
                        return rti.get(i).baseIntent;
                    }

                    if (rti.get(i).id == pid) {
                        return rti.get(i).baseIntent;
                    }
                }
            }
        }

        return null;
    }

    /**
     * check the application process name if process name is not qualified, then we think it is a service process and we will not init SDK
     * @param pID
     * @return
     */
    private String getAppName(Context appContext,int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
            }
        }
        return processName;
    }

}
