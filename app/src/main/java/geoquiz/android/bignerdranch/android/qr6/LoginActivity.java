package geoquiz.android.bignerdranch.android.qr6;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.sms.BmobSMS;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{


    private EditText mLoginUsername;
    private EditText mLoginUserpass;
    private Button login;
    private Button cancle;
    private TextView sign;
    private TextView mRegisternow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bmob.initialize(this, "c22f7d5f3cf16e33db01670599e9487c");
        BmobSMS.initialize(this,"c22f7d5f3cf16e33db01670599e9487c");
        initialize();
    }

    private void initialize(){
        mLoginUsername = (EditText) findViewById(R.id.login_user_name);
        mLoginUserpass = (EditText) findViewById(R.id.login_user_pass);
        login = (Button) findViewById(R.id.button_1);
        cancle = (Button)findViewById(R.id.button_2);
        mRegisternow = (TextView)findViewById(R.id.register_now);

        cancle.setOnClickListener(this);
        login.setOnClickListener(this);
        mRegisternow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.button_1:
                progressLogin(mLoginUsername.getText().toString(),mLoginUserpass.getText().toString());
                break;
            case R.id.register_now:
                //UIUtils.nextPage(LoginActivity.this,Register.class);
                Intent intent = new Intent(LoginActivity.this,Register.class);
                startActivity(intent);
                break;

        }
    }



    public void progressLogin(final String username, String pass){
        User bu2 = new User();
        if (ToolsUtils.isMobileNO(username)){
            if (ToolsUtils.isCorrectUserPwd(pass)){
                bu2.setMobilePhoneNumber(username);
                bu2.setPassword(pass);
                bu2.login(new SaveListener<User>() {
                    public void done(User user, BmobException e) {
                        if(e==null){
                            /*Toast.makeText(LoginActivity.this, "成功", Toast.LENGTH_SHORT).show();
                            user = BmobUser.getCurrentUser(User.class);
                            loginSuccess(user);*/
                            Toast.makeText(LoginActivity.this, "成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                            Toast.makeText(LoginActivity.this, "账户名或密码不正确", Toast.LENGTH_SHORT).show();
                            /*loginFailed("登录失败");*/
                            //loge(e);
                        }
                    }

                });
            }else{
               /* loginFailed("密码中含有非法字符");*/
                Toast.makeText(LoginActivity.this, "账户名或密码不正确1", Toast.LENGTH_SHORT).show();
            }
        }else {
            if (ToolsUtils.isCorrectUserPwd(pass)){
                bu2.setUsername(username);
                bu2.setPassword(pass);
                bu2.login(new SaveListener<User>() {

                    public void done(User user, BmobException e) {
                        if(e==null){
                            Toast.makeText(LoginActivity.this, "成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            /*user = BmobUser.getCurrentUser(User.class);
                            loginSuccess(user);*/
                        }else{
                            Toast.makeText(LoginActivity.this, "账户名或密码不正确", Toast.LENGTH_SHORT).show();
                            /*loginFailed("登录失败");*/
                        }
                    }

                });
            }else{
                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                /*loginFailed("密码中含有非法字符");*/
            }
        }
    }

}
