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

import java.util.StringTokenizer;

import com.maicius.wake.alarmClock.DeskClockMainActivity;
import com.maicius.wake.alarmClock.R;
import com.maicius.wake.web.WebService;

public class UserInfo extends Activity {

    private EditText m_name;
    private EditText m_password;
    private EditText m_phone;
    private Button m_button;
    private static Handler m_handler = new Handler();
    private ProgressDialog m_proDialog;
    private ProgressDialog m_saveDialog;
    private String m_info;
    private String m_saveInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        mInitUI();
    }

    private void mInitUI() {
        m_name = (EditText) findViewById(R.id.username);
        m_password = (EditText) findViewById(R.id.password);
        m_phone = (EditText) findViewById(R.id.phone);
        m_button = (Button) findViewById(R.id.button_save);
        m_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                m_saveDialog = new ProgressDialog(UserInfo.this);
                m_saveDialog.setTitle("提示");
                m_saveDialog.setMessage("正在更新个人信息，请稍后...");
                m_saveDialog.setCancelable(false);
                m_saveDialog.show();
                new Thread(new UpdateInfo()).start();
            }
        });
        m_proDialog = new ProgressDialog(UserInfo.this);
        m_proDialog.setTitle("提示");
        m_proDialog.setMessage("正在获取个人信息，请稍后...");
        m_proDialog.setCancelable(false);
        m_proDialog.show();
        //创建子线程
        new Thread(new MyThread()).start();
    }

    public class MyThread implements Runnable {
        @Override
        public void run() {
            m_info = WebService.executeHttpGet(DeskClockMainActivity.s_userName, WebService.State.GetUserInfo);
            Log.v("sss", "login:" + m_info);
            m_handler.post(new Runnable() {
                @Override
                public void run() {
                    // 最好返回一个固定键值，根据键值判断是否登陆成功，有键值就保存该info跳转，没键值就是错误信息直接toast

                    m_proDialog.dismiss();

                    if (m_info.equals("failed")) {

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(UserInfo.this);
                        alertDialog.setTitle("提示").setMessage("获取个人信息失败！");
                        alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alertDialog.create().show();

                    } else {
                        Log.v("sss***************", m_info);
                        StringTokenizer st = new StringTokenizer(m_info, "#");
                        String name = st.nextToken();
                        String password = st.nextToken();
                        String phone = st.nextToken();
                        m_name.setText(name);
                        m_password.setText(password);
                        m_phone.setText(phone);
                    }
                }
            });
        }
    }

    public class UpdateInfo implements Runnable {
        @Override
        public void run() {
            m_saveInfo = WebService.executeHttpGet(DeskClockMainActivity.s_userName,m_password.getText().toString(),
                    m_phone.getText().toString());
            Log.v("sss", "login:" + m_saveInfo);
            m_handler.post(new Runnable() {
                @Override
                public void run() {
                    // 最好返回一个固定键值，根据键值判断是否登陆成功，有键值就保存该info跳转，没键值就是错误信息直接toast

                    m_saveDialog.dismiss();

                    if (m_saveInfo.equals("failed")) {

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(UserInfo.this);
                        alertDialog.setTitle("提示").setMessage("获取个人信息失败！");
                        alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alertDialog.create().show();

                    } else {
                        Log.v("sss***************", m_saveInfo);
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(UserInfo.this);
                        alertDialog.setTitle("提示").setMessage("更新个人信息成功！");
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
