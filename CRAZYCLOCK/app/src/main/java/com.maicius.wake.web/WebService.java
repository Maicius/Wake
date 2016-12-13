package com.maicius.wake.web;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.maicius.wake.alarmClock.MainActivity;
import com.maicius.wake.web.ConnectionDetector;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebService extends Activity{
    public enum State {
        LogIn,
        Register,
        GetUpTime,
        GetTimeList,
        GetUserInfo
    }
    // IP地址
    private static String IP = "116.62.41.211:8080";
    /**
     * DoGet
     */
    private static String doHttpGet(String path){
        HttpURLConnection conn = null;
        InputStream is = null;
        try {
            conn = (HttpURLConnection) new URL(path).openConnection();
            conn.setConnectTimeout(3000); // 设置超时时间
            conn.setReadTimeout(3000);
            conn.setDoInput(true);
            conn.setRequestMethod("GET"); // 设置获取信息方式
            conn.setRequestProperty("Charset", "UTF-8"); // 设置接收数据编码格式
            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                return parseInfo(is);
            }
            return null;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            // 意外退出时进行连接关闭保护
            if (conn != null) {
                conn.disconnect();
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return "服务器连接超时...";
    }
    /**
     * 登录
     */
    public static String executeHttpGet(String username, String password, State state) {
        String path;
        //path = "http://" + IP +"/HelloWeb/LogLet";
        path = "http://" + IP + "/LogLet";
        path = path + "?username=" + username + "&password=" + password;
        return doHttpGet(path);
    }

    /**
     * 注册
     */
    public static String executeHttpGet(String username, String password,
                                        String nickname, State state){
            String path;
            //path = "http://" + IP + "HelloWeb/RegLet";
            path = "http://" + IP + "/RegLet";
            path = path + "?username=" + username + "&password=" + password+"&nickname="+nickname;
            return doHttpGet(path);
    }
    /**
     * 上传起床时间
     */
    public static String executeHttpGet(long date, State state) {

            //String path = "http://" + IP + "HelloWeb/GetUpTime";
            String path = "http://" + IP + "/GetUpTime";
            path = path + "?username=" + MainActivity.s_userName + "&date=" + String.valueOf(date);
            return doHttpGet(path);

    }

    public static String executeHttpGet(String username, State state) {

            // URL 地址
        String path = "";
        switch (state) {
            case GetTimeList:
                //path = "http://" + IP + "/HelloWeb/TimeHistory";
                path = "http://" + IP + "/TimeHistory";
                break;
            case GetUserInfo:
                //path = "http://" + IP + "/HelloWeb/GetUserInfo";
                path = "http://" + IP + "/GetUserInfo";
                break;
            }
        path = path + "?username=" + username;
        Log.v("sss", path);
        return doHttpGet(path);
    }

    public static String executeHttpGet(String username, String nickname, String brief_intro) {
        String path;
        //path = "http://" + IP + "/HelloWeb/SetUserInfo";
        path = "http://" + IP + "/SetUserInfo";
        path = path + "?username=" + username + "&nickname=" + nickname + "&brief_intro=" + brief_intro;
        Log.v("sss", path);
        return doHttpGet(path);
    }

    // 将输入流转化为 String 型
    private static String parseInfo(InputStream inStream) throws Exception {
        byte[] data = read(inStream);
        // 转化为字符串
        return new String(data, "UTF-8");
    }

    // 将输入流转化为byte型
    public static byte[] read(InputStream inStream) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        inStream.close();
        return outputStream.toByteArray();
    }
}
