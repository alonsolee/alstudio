package alstudio.alstudiolib.module.xmpp;

import alstudio.alstudiolib.module.BaseManager;

/**
 * XMPP管理器
 *
 * Created by Alonso Lee on 2014/12/19.
 */
public class ALXmppManager extends BaseManager{

    private static ALXmppManager instance;

    public static ALXmppManager getDefault(){
        if(instance == null){
            instance = new ALXmppManager();
        }
        return instance;
    }


    @Override
    public void reset() {

    }

    @Override
    public void init() {

    }
}
