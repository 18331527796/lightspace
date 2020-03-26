package com.threefriend.lightspace.xcxutil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.threefriend.lightspace.enums.AccountEnums;
import com.threefriend.lightspace.enums.UrlEnums;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

public class CreateQrcore {

    /*
     * 获取 token
     * 普通的 get 可获 token
     */
    public static String getToken() throws Exception {
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+AccountEnums.APIKEY.getUrl()+"&secret="+AccountEnums.SECRETKEY.getUrl();
        URL url = new URL(requestUrl);
        // 打开和URL之间的连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        // 设置通用的请求属性
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setUseCaches(false);
        connection.setDoOutput(true);
        connection.setDoInput(true);
 
        // 得到请求的输出流对象
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.writeBytes("");
        out.flush();
        out.close();
 
        // 建立实际的连接
        connection.connect();
        // 定义 BufferedReader输入流来读取URL的响应
        BufferedReader in = null;
        if (requestUrl.contains("nlp"))
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "GBK"));
        else
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        String result = "";
        String getLine;
        while ((getLine = in.readLine()) != null) {
            result += getLine;
        }
        in.close();
        JSONObject jsonObject = JSON.parseObject(result);
		String accesstoken=jsonObject.getString("access_token");
        return accesstoken;
    }

    /**
     * 生成带参小程序二维码
     *
     * @param scene       要输入的内容
     * @param accessToken token
     */
    public static void postMiniqrQr(String scene, String accessToken, String path) {
        try {
            URL url = new URL(UrlEnums.WECHAT_XCX_CODE_PAHT.getUrl()+ accessToken);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true); // 打开写入属性
            httpURLConnection.setDoInput(true); // 打开读取属性
            httpURLConnection.setRequestMethod("POST");// 提交方式
            // 不得不说一下这个提交方式转换！！真的坑。。改了好长时间！！一定要记得加响应头
            httpURLConnection.setRequestProperty("Content-Type", "application/x-javascript; charset=UTF-8");// 设置响应头
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            JSONObject paramJson = new JSONObject();
            //paramJson.put("scene", scene); // 你要放的内容
            paramJson.put("path", "pages/index/index?scene=" + scene);
            paramJson.put("width", 430); // 宽度
            paramJson.put("auto_color", true);
            printWriter.write(paramJson.toString());
            // flush输出流的缓冲
            printWriter.flush();
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
            File file = new File(path);
            if (!file.exists()) {
                file.mkdir();
            }
            //创建一个空文件
            OutputStream os = new FileOutputStream(new File(path + scene + ".jpg"));
            //ByteArrayOutputStream os = new ByteArrayOutputStream();
            int len;
            byte[] arr = new byte[1024];
            while ((len = bis.read(arr)) != -1) {
                os.write(arr, 0, len);
                os.flush();
            }
            os.close();
            // 上传云储存
            //InputStream is = new ByteArrayInputStream(os.toByteArray());
            //retMap = UploadUtils.upload(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * 获取 二维码图片
     *
     */
    public static String getminiqrQr(String accessToken, HttpServletRequest request, String serialNum) {
        String p = UrlEnums.CODE_PATH.getUrl(); //二维码生产的地址  本地F盘code文件夹
        System.out.println(p);
        String codeUrl = p + "/" + serialNum + ".png";
        String twoCodeUrl = serialNum + ".png";
        try {
            URL url = new URL("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + accessToken);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");// 提交模式
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            JSONObject paramJson = new JSONObject();
            paramJson.put("scene", serialNum);//这就是你二维码里携带的参数 String型  名称不可变
            paramJson.put("path", "pages/index/index"); //这是设置扫描二维码后跳转的页面
            paramJson.put("width", 430);
            paramJson.put("is_hyaline", false);
            paramJson.put("auto_color", false);

            System.out.println("httpURLConnection" + httpURLConnection);
            System.out.println("paramJson.toString()" + paramJson.toString());
            printWriter.write(paramJson.toString());
            // flush输出流的缓冲
            printWriter.flush();
            //开始获取数据
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
            OutputStream os = new FileOutputStream(new File(codeUrl));
            int len;
            byte[] arr = new byte[1024];
            while ((len = bis.read(arr)) != -1) {
                os.write(arr, 0, len);
                os.flush();
            }
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codeUrl;
    }
}