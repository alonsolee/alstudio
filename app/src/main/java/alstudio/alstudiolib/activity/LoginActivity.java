package alstudio.alstudiolib.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import alstudio.alstudioim.IMEvent;
import alstudio.alstudioim.IMManager;
import alstudio.alstudiolib.R;
import alstudio.alstudiolib.common.activity.BaseActivity;

public class LoginActivity extends BaseActivity {

    private EditText username,password;
    private View login;
    private ProgressDialog mProgressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initUI() {
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("请稍后");
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                if(TextUtils.isEmpty(user)){
                    Toast.makeText(LoginActivity.this,"请输入账号",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(user)){
                    Toast.makeText(LoginActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                    return;
                }
                mProgressDialog.show();
                IMManager.getInstance().login(user,pass);


            }
        });
    }

    @Override
    public void handleIMConnectionEstablished() {
        super.handleIMConnectionEstablished();
        mProgressDialog.dismiss();
        Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void handleIMConnectionLost(IMEvent event) {
        super.handleIMConnectionLost(event);
        mProgressDialog.dismiss();
        Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();

    }
}
