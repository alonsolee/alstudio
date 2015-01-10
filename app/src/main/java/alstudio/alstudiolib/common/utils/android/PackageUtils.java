package alstudio.alstudiolib.common.utils.android;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * 安装apk与卸载apk工具类
 *
 * Created by Alonso Lee on 2014/12/18.
 */
public class PackageUtils {


    /**
     * 调用系统广播安装app
     *
     * @param context
     * @param filePath
     * @return
     */
    public static boolean installApp(Context context, String filePath) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        File file = new File(filePath);
        if (file == null || !file.exists() || !file.isFile() || file.length() <= 0) {
            return false;
        }

        i.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        return true;
    }

    /**
     * 卸载指定包名的应用
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean uninstallApp(Context context, String packageName) {
        if (packageName == null || packageName.length() == 0) {
            return false;
        }

        Intent i = new Intent(Intent.ACTION_DELETE, Uri.parse(new StringBuilder(32).append("package:")
                .append(packageName).toString()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        return true;
    }

}
