package com.maicius.wake.InterChange;
/**
 * Created by Maicius on 2016/6/7.
 */

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.maicius.wake.alarmClock.MainActivity;
import com.maicius.wake.alarmClock.R;
import com.maicius.wake.web.WebService;

public class LogIn extends Activity {
    //创建等待框
    private ProgressDialog dialog;
    //返回的数据
    private String info;
    EditText username, password;
    private static Handler handler = new Handler();

    public void onCreate(Bundle savedInstanceState) {
        Log.v("maicius", "enter sign in");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        TextView Register = (TextView) findViewById(R.id.register);
        final Button SignIn = (Button) findViewById(R.id.signin_button);
        username = (EditText) findViewById(R.id.username_edit);
        password = (EditText) findViewById(R.id.password_edit);
        //点击注册
        Register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Register();
            }
        });
        //点击登录
        SignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View V) {
                dialog = new ProgressDialog(LogIn.this);
                dialog.setTitle("提示");
                dialog.setMessage("正在登陆，请稍后...");
                dialog.setCancelable(false);
                dialog.show();
                //创建子线程
                new Thread(new MyThread()).start();
            }
        });
        //setContentView(R.layout.user_space);
    }

    // 子线程接收数据，主线程修改数据
    public class MyThread implements Runnable {
        @Override
        public void run() {
            info = WebService.executeHttpGet(username.getText().toString(), password.getText().toString(), WebService.State.LogIn);
            Log.v("sss", "login:" + info);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    // 最好返回一个固定键值，根据键值判断是否登陆成功，有键值就保存该info跳转，没键值就是错误信息直接toast

                    dialog.dismiss();

                    if (info.equals("success")) {
                        Log.v("sss", "start user space!");
                        MainActivity.s_userName = username.getText().toString();
                        MainActivity.s_isLogged = true;
                        startActivity(new Intent(LogIn.this, UserSpace.class));
                        LogIn.this.finish();

                    } else if (info.equals("failed")) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LogIn.this);
                        alertDialog.setTitle("登陆信息").setMessage("登陆失败：用户名或密码错误！");
                        alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alertDialog.create().show();
                    }
                }
            });
        }
    }

    private void Register() {
        startActivity(new Intent(this, Register.class));
    }
}

