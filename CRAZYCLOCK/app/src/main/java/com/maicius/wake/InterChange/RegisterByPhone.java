package com.maicius.wake.InterChange;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.maicius.wake.alarmClock.R;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

/**
 * Created by Maicius on 2016/12/11.
 */
public class RegisterByPhone extends Activity {
    public void onCreate(Bundle savedBundleInstance){
        super.onCreate(savedBundleInstance);
        Log.v("maicius", "enter sign in");
        //打开注册页面
        RegisterPage registerPage = new RegisterPage();
        registerPage.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
        // 解析注册结果
                if (result == SMSSDK.RESULT_COMPLETE) {
                    @SuppressWarnings("unchecked")
                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country");
                    String phone = (String) phoneMap.get("phone");
                    setContentView(R.layout.log_in);
                    // 提交用户信息（此方法可以不调用）
                    registerUser(country, phone);
                }
            }
        });
        registerPage.show(RegisterByPhone.this);

    }
    protected void registerUser(String country, String phone) {
        //提交的资料将当作“通信录好友”功能的建议资料。
        String uid = "1223";
        String nickName = "yj";
        SMSSDK.submitUserInfo(uid, nickName, null, country, phone);
    }
}
