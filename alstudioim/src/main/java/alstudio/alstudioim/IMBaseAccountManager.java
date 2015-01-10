package alstudio.alstudioim;

/**
 * Created by Alonso Lee on 2014/12/31.
 */
public abstract class IMBaseAccountManager {

    public abstract void saveIMAccount(String userName,String passWord);
    public abstract String getIMUserName();
    public abstract String getIMPassWord();
    public abstract boolean isLogined();

}
