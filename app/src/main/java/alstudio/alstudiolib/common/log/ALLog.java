package alstudio.alstudiolib.common.log;

import android.util.Log;


/**
 * 此类封装系统Log方法，通过debug变量来控制是否输出调试信息
 *
 * @author Alonso Lee
 */
public class ALLog {

    private final String TAG = getClass().getSimpleName();

    /**
     * debug or not
     */
    private boolean debug = true;

    private static ALLog instance = null;

    public static ALLog getDefault() {
        if (instance == null) {
            instance = new ALLog();
        }
        return instance;
    }

    private String getFileName() {

        StackTraceElement[] sts = Thread.currentThread().getStackTrace();

        if (instance == null)
            return null;
        if (sts == null) {
            return null;
        }

        for (StackTraceElement st : sts) {
            if (st.isNativeMethod()) {
                continue;
            }

            if (st.getClassName().equals(Thread.class.getName())) {
                continue;
            }

            if (st.getClassName().equals(instance.getClass().getName())) {
                continue;
            }

            return st.getFileName().replace(".java", "");
        }

        return null;

    }

    private String getFunctionName() {

        StackTraceElement[] sts = Thread.currentThread().getStackTrace();

        if (sts == null) {
            return null;
        }

        for (StackTraceElement st : sts) {
            if (st.isNativeMethod()) {
                continue;
            }

            if (st.getClassName().equals(Thread.class.getName())) {
                continue;
            }

            if (st.getClassName().equals(instance.getClass().getName())) {
                continue;
            }

            return st.getFileName() + " " + st.getMethodName() + ": line "
                    + st.getLineNumber();
        }

        return null;
    }

    private String createMessage(String msg) {
        String functionName = getFunctionName();
        String message = (functionName == null ? msg : ("[ " + functionName
                + " - " + msg + " ]"));
        return message;
    }

    /**
     * log.i
     */
    public void i(String msg) {
        if (debug) {
            String message = createMessage(msg);
            String tag = getFileName();
            if (tag != null) {
                Log.i(tag, message);
            } else {
                Log.i(TAG, message);
            }
        }
    }

    /**
     * log.v
     */
    public void v(String msg) {
        if (debug) {
            String message = createMessage(msg);
            String tag = getFileName();
            if (tag != null) {
                Log.v(tag, message);
            } else {
                Log.v(TAG, message);
            }
        }
    }

    /**
     * log.d
     */
    public void d(String msg) {
        if (debug) {
            String message = createMessage(msg);
            String tag = getFileName();
            if (tag != null) {
                Log.i(tag, message);
            } else {
                Log.i(TAG, message);
            }
        }
    }

    /**
     * log.e
     */
    public void e(String msg) {
        if (debug) {
            String message = createMessage(msg);
            String tag = getFileName();
            if (tag != null) {
                Log.e(tag, message);
            } else {
                Log.e(TAG, message);
            }
        }
    }

    /**
     * log.error
     */
    public void error(Exception e) {
        if (debug) {
            StringBuffer sb = new StringBuffer();
            String name = getFunctionName();
            StackTraceElement[] sts = e.getStackTrace();

            if (name != null) {
                sb.append(name + " - " + e + "\r\n");
            } else {
                sb.append(e + "\r\n");
            }
            if (sts != null && sts.length > 0) {
                for (StackTraceElement st : sts) {
                    if (st != null) {
                        sb.append("[ " + st.getFileName() + ":"
                                + st.getLineNumber() + " ]\r\n");
                    }
                }
            }
            e(sb.toString());
        }
    }

    /**
     * log.w
     */
    public void w(String msg) {
        if (debug) {
            String message = createMessage(msg);
            String tag = getFileName();
            if (tag != null) {
                Log.i(tag, message);
            } else {
                Log.i(TAG, message);
            }
        }
    }

    /**
     * set debug
     */
    public void setDebug(boolean d) {
        debug = d;
    }

}
