package com.maicius.wake.InterChange;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.maicius.wake.alarmClock.R;
import com.maicius.wake.web.WebService;

public class Register extends Activity {
    private String info;
    private ProgressDialog dialog;
    EditText username=null, password=null, password_confirm=null;
    private static Handler handler = new Handler();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("maicius", "enter sign in");
        setContentView(R.layout.sign_up);
        Button SignUp = (Button) findViewById(R.id.sign_up_button);
        username = (EditText) findViewById(R.id.register_username);
        password = (EditText) findViewById(R.id.register_password);
        password_confirm = (EditText) findViewById(R.id.register_password_confirm);

        if(username == null || password_confirm ==null || password==null){
            dialog = new ProgressDialog(Register.this);
            dialog.setMessage("信息没有填完哦");
            dialog.show();
            username = (EditText) findViewById(R.id.register_username);
            password = (EditText) findViewById(R.id.register_password);
            password_confirm = (EditText) findViewById(R.id.register_password_confirm);
        }

        while(password !=password_confirm){
            dialog = new ProgressDialog(Register.this);
            dialog.setMessage("两次输入密码不一样");
            dialog.show();
            password = null;
            password_confirm = null;
            password = (EditText) findViewById(R.id.register_password);
            password_confirm = (EditText) findViewById(R.id.register_password_confirm);
            if(password == password_confirm)
                break;
        }

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ProgressDialog(Register.this);
                dialog.setTitle("提示");
                dialog.setMessage("正在注册，请稍后...");
                dialog.setCancelable(false);
                dialog.show();
                //创建子线程
                new Thread(new SignUpThread()).start();
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
                        Register.this.finish();
                        setContentView(R.layout.sign_in);
                    }
                    else if (info.equals("failed")) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Register.this);
                        alertDialog.setTitle("注册信息").setMessage("注册失败！");
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
}
