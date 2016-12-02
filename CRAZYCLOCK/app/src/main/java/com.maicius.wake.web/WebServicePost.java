package com.maicius.wake.web;

public class WebServicePost {

    // IP地址
    private static String IP = "172.27.35.2:8080";

    /**
     * 通过 POST 方式获取HTTP服务器数据
     */
    public static String executeHttpPost(String username) {

       /* String url = "http://" + IP + "/HelloWeb/LogLet";
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        BasicNameValuePair param = new BasicNameValuePair("param1", paramValue);
        paramList.add(param);

        post.setEntity(new UrlEnodedFormEntity(paramList, HTTP.UTF - 8));

        HttpResponse httpResponse = httpClient.execute(post);

        if (httpResponse.getStatusLine().getStatusCode() == 200) {

            String result = EntityUtils.toString(httpResponse.getEntity());

        }*/
        return "";
    }

}