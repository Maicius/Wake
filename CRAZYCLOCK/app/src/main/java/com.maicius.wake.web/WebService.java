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
     * 通过Get方式获取HTTP服务器数据
     */
    public static String executeHttpGet(String username, String password, State state) {

        HttpURLConnection conn = null;
        InputStream is = null;

        try {
            // 用户名 密码
            // URL 地址
            String path;
            switch (state) {
                case LogIn:
                    path = "http://" + IP + "/HelloWeb/LogLet";
                    break;

                case Register:
                    path = "http://" + IP + "/HelloWeb/RegLet";
                    break;

                default:
                    path = "http://" + IP + "/HelloWeb/LogLet";
                    break;
            }
            path = path + "?username=" + username + "&password=" + password;

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

    public static String executeHttpGet(long date, State state) {

        HttpURLConnection conn = null;
        InputStream is = null;

        try {
            // URL 地址
            String path;
            switch (state) {
                case GetUpTime:
                    path = "http://" + IP + "/HelloWeb/GetUpTime";
                    break;
                default:
                    path = "http://" + IP + "/HelloWeb/GetUpTime";
                    break;
            }
            //String dataStr = date.toString();
            //dataStr = dataStr.replaceAll(" ", "%20");
            path = path + "?username=" + MainActivity.s_userName + "&date=" + String.valueOf(date);

            Log.v("sss", path);
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

    public static String executeHttpGet(String username, State state) {

        HttpURLConnection conn = null;
        InputStream is = null;
        try {
            // URL 地址
            String path = "";
            switch (state) {
                case GetTimeList:
                    path = "http://" + IP + "/HelloWeb/TimeHistory";
                    break;
                case GetUserInfo:
                    path = "http://" + IP + "/HelloWeb/GetUserInfo";
                    break;
            }
            path = path + "?username=" + MainActivity.s_userName;
            Log.v("sss", path);
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

    public static String executeHttpGet(String username, String password, String phone) {

        HttpURLConnection conn = null;
        InputStream is = null;
        try {
            // URL 地址
            String path = "";
            path = "http://" + IP + "/HelloWeb/SetUserInfo";

            path = path + "?username=" + username + "&password=" + password + "&phone=" + phone;
            Log.v("sss", path);
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
