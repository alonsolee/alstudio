package alstudio.alstudiolib.module;

import android.content.Context;

/**
 * Created by Alonso Lee on 2014/12/19.
 */
public abstract class BaseManager {
    private Context mContext;
    private boolean isEnableDebug = false;

    public void setContext(Context context){
        this.mContext = context;
    }

    public void setEnableDebug(boolean debug){
        isEnableDebug = debug;
    }

    public abstract void reset();

    public abstract void init();


}
