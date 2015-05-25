package com.alstudio.autils.log;


import com.alstudio.autils.android.ALFileManager;
import com.alstudio.autils.android.AndroidUtils;
import com.alstudio.autils.android.TimeUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;


/**
 * //Mylog类用于程序在运行期间的运行情况写到SD卡上以供开发人员查看。 本类提供
 *
 * @author Alonso Lee
 */
public class Log2SdCardUtils {


    private static File file = null;

    /* 以下为LOG级别 */
    public static final String INFO = "INFO"; // 普通信息
    public static final String WARN = "WARN"; // 警告
    public static final String ERROR = "ERROR"; // 错误

    private String pathName = ALFileManager.getExtStoragePath();
    private OutputStreamWriter outputStreamWriter;
    private FileOutputStream stream = null;

    // 此常量为日志名
    public final String fileName = "/AndroidRec.vmd";

    private boolean isInit = false;

    // 此常量用于标识日志文件最大只能为1M
    private int MAX_FILE_SIZE = 1000 * 1000 * 2; // 1M

    private boolean isLogEnable = true;

    private static Log2SdCardUtils instance;

    public static Log2SdCardUtils getDefault() {
        if (instance == null) {
            instance = new Log2SdCardUtils();
        }
        return instance;
    }


    public void setLogEnable(boolean isLogEnable) {
        this.isLogEnable = isLogEnable;
    }

    public void setLogFilePath(String path) {
        pathName = path;
    }


    /**
     * @param message 要输出的消息
     */
    public synchronized void logToSD(String message) {

        if (!isLogEnable) {
            return;
        }

        if (message == null) {
            return;
        }
        if (!isInit) {
            isInit = true;

            if (file == null) {
                File path = new File(pathName);
                file = new File(pathName + fileName);

                if (!path.exists()) {
                    // 创建Log文件夹
                    if (path.mkdirs() == false) {
                        return;
                    }
                }
                if (!file.exists()) {
                    try {
                        if (file.createNewFile() == false) {
                            return;
                        }
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                try {
                    stream = new FileOutputStream(file, true);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                try {
                    outputStreamWriter = new OutputStreamWriter(stream, "GBK");
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        if (file != null) {
            if (file.exists()) {
                if (file.length() >= MAX_FILE_SIZE) {
                    file.delete();
                    createNewLogFile();
                }
            }
        }

        try {

            String curTime = TimeUtil.getTime(TimeUtil.getCurrentTimeInLong());
            String messageBase = "";


            // alonso 2011-8-17 取消输出tag信息
            messageBase = "[ " + curTime + ": " +  message + " ]" + "\r\n\r\n";

            if (outputStreamWriter != null) {
                outputStreamWriter.write(messageBase);
                outputStreamWriter.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建新的日志文件
     *
     * @return true 创建成功；false 创建失败
     */
    private synchronized boolean createNewLogFile() {
        try {
            if (file.createNewFile() == false) {
                return false;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

        try {
            stream = new FileOutputStream(file, true);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        try {
            outputStreamWriter = new OutputStreamWriter(stream, "GBK");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
