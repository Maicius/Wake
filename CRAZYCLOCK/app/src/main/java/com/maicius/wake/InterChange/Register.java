package com.maicius.wake.InterChange;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.maicius.wake.alarmClock.R;
import com.maicius.wake.web.WebService;
import java.util.regex.Pattern;

public class Register extends Activity {
    private String info;
    private ProgressDialog dialog;
    EditText username, password, password_confirm;
    private static Handler handler = new Handler();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("maicius", "enter sign in");
        setContentView(R.layout.register);
        TextView Login = (TextView) findViewById(R.id.login);
        Button register_button = (Button) findViewById(R.id.sign_up_button);
        username = (EditText) findViewById(R.id.register_username);
        password = (EditText) findViewById(R.id.register_password);
        password_confirm = (EditText) findViewById(R.id.register_password_confirm);
        Login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //LogIn();
                registerByPhone();
            }
        });

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("")){
                    raiseAlertDialog("提示","用户名不能为空");
                }
                else if(!isUserName(username.getText().toString())){
                    raiseAlertDialog("提示","");

                }
                else if (password_confirm.getText().toString().equals("")
                        || password.getText().toString().equals("")) {
                    raiseAlertDialog("提示","密码不能为空");
                }
                else if(password.getText().toString().length()<6
                        || password.getText().toString().length()>16){
                    raiseAlertDialog("提示","密码长度必须在6-16之间");
                }
                else if (!(password_confirm.getText().toString().equals(password.getText().toString()))) {
                    raiseAlertDialog("提示","两次密码不一致");
                }
                else{
                    dialog = new ProgressDialog(Register.this);
                    dialog.setTitle("提示");
                    dialog.setMessage("正在注册，请稍后...");
                    dialog.setCancelable(false);
                    dialog.show();
                    //创建子线程
                    new Thread(new SignUpThread()).start();
                }
            }
        });
    }

    public class SignUpThread implements Runnable {
        public void run() {
            info = WebService.executeHttpGet(username.getText().toString(), password.getText().toString(), WebService.State.Register);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    // 最好返回一个固定键值，根据键值判断是否登陆成功，有键值就保存该info跳转，没键值就是错误信息直接toast
                    dialog.dismiss();

                    if (info.equals("success")) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Register.this);
                        alertDialog.setTitle("注册成功").setMessage("欢迎来到Wake！");
                        alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Register.this.finish();
                                setContentView(R.layout.log_in);
                            }
                        });
                        alertDialog.create().show();

                    }
                    else if (info.equals("failed")) {
                        raiseAlertDialog("注册信息","Sorry, 注册失败");
                    }
                }
            });
        }
    }
    private void LogIn() {
        startActivity(new Intent(this, LogIn.class));
    }
    private void registerByPhone() {
        startActivity(new Intent(this, RegisterByPhone.class));
    }
    private boolean isUserName(String username){
        return Pattern.matches("^[a-zA-Z]\\w{5,17}$", username);
    }

    private void raiseAlertDialog(String title, String message){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Register.this);
        alertDialog.setTitle(title).setMessage(message);
        alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.create().show();
    }
}
