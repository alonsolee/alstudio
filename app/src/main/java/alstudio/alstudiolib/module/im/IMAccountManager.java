package alstudio.alstudiolib.module.im;

import android.content.Context;
import android.text.TextUtils;

import alstudio.alstudioim.IMBaseAccountManager;
import alstudio.alstudiolib.common.utils.android.ALSharedPreferencesManager;

/**
 * Created by Alonso Lee on 2014/12/31.
 */
public class IMAccountManager extends IMBaseAccountManager {

    private ALSharedPreferencesManager spManager ;
    private final String IM_SP_USER_NAME = getClass().getSimpleName().toUpperCase()+"_IM_USER_NAME";
    private final String IM_SP_PASS_WORD = getClass().getSimpleName().toUpperCase()+"_IM_PASS_WORD";

    public IMAccountManager(Context context){
        spManager = new ALSharedPreferencesManager(context);
    }

    @Override
    public void saveIMAccount(String userName, String passWord) {
        spManager.putString(IM_SP_USER_NAME,userName);
        spManager.putString(IM_SP_PASS_WORD,passWord);
        System.out.println("密码"+passWord+"账号"+userName);
    }

    @Override
    public String getIMUserName() {
        return spManager.getString(IM_SP_USER_NAME,"");
    }

    @Override
    public String getIMPassWord() {
        return spManager.getString(IM_SP_PASS_WORD,"");
    }

    @Override
    public boolean isLogined() {
        return (!TextUtils.isEmpty(getIMPassWord()) && !TextUtils.isEmpty(getIMUserName()))?true:false;
    }
}

