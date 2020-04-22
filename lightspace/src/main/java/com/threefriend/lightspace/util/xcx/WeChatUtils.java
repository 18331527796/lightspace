package com.threefriend.lightspace.util.xcx;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.threefriend.lightspace.mapper.xcx.GzhUserMapper;
import com.threefriend.lightspace.vo.wechatmenu.Menu;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
 
 
public class WeChatUtils {
	

    private static Logger log = LoggerFactory.getLogger(WeChatUtils.class);
 
    //网页授权获取access_token 公众号
    public final static String OAUTH_URL_OPENID = "https://api.weixin.qq.com/cgi-bin/token";
    //网页授权获取用户信息
    public final static String OAUTH_URL_USERINFO = "https://api.weixin.qq.com/cgi-bin/user/info";
    //获取sessionkey
    public final static String OAUTH_URL_SESSIONKEY ="https://api.weixin.qq.com/sns/jscode2session";
    
    /**
     * 获取微信信息
     *
     * @param code      前端获取的微信code
     * @param appId     微信appid
     * @param appSecret 微信appSecret
     * @return
     */
    /*public static Customer indexUserInfo(String appId, String appSecret,String openid) {
    	Customer result = null;
        //获取openid、accessToken
    	String accessToken = findAccessToken(appId, appSecret);
 
        //通过accessToken、openid获取微信用户详细信息
        Customer authCustomer = findWxUserInfo(accessToken, openid);
        System.out.println(authCustomer.toString());
        if (authCustomer == null) {
            System.out.println("微信用户oauth2.0用户信息获得失败：accessToken：" + accessToken + "；openid" + openid);
            return result;
        }
 
 
        return authCustomer;
    }
 
    /**
     * 获取openId、accessToken 公众号
     *
     * @param appId
     * @param appSecret
     * @param code
     * @return
     */
    public static String findAccessToken(String appId, String appSecret) {
        Map result = new HashMap();
        String accessToken="";
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest
                    .post(OAUTH_URL_OPENID)
                    .queryString("appid", appId)
                    .queryString("secret", appSecret)
                    .queryString("grant_type", "client_credential")
                    .asJson();
            JSONObject jb = jsonResponse.getBody().getObject();
            System.out.println(jb.toString());
            if (!jb.isNull("access_token") && jb.get("access_token") != null) {
                accessToken = jb.get("access_token").toString();
            }
        } catch (Exception e) {
            System.out.println("接口获取用户信息报错,appid：" + appId + " secret:" + appSecret );
        }
        return accessToken;
    }
 
    /**
     * 获取微信用户信息
     *
     * @param accessToken
     * @param openid
     * @return
     */
    public static GzhUserMapper findWxUserInfo(String accessToken, String openid) {
    	GzhUserMapper customer = new GzhUserMapper();
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest
                    .post(OAUTH_URL_USERINFO)
                    .queryString("access_token", accessToken)
                    .queryString("openid", openid)
                    .queryString("lang", "zh_CN")
                    .asJson();
            JSONObject jb = jsonResponse.getBody().getObject();
            System.out.println(accessToken);
            System.out.println(openid);
            System.out.println("解析的内容："+jb.toString());
            if (!jb.isNull("openid") && jb.get("openid") != null) {
                customer.setOpenid(jb.get("openid").toString());
            }
            if (!jb.isNull("sex") && jb.get("sex") != null) {
                customer.setSex(Integer.parseInt(jb.get("sex").toString()));
            }
            if (!jb.isNull("province") && jb.get("province") != null) {
                customer.setProvince(jb.get("province").toString());
            }
            if (!jb.isNull("city") && jb.get("city") != null) {
                customer.setCity(jb.get("city").toString());
            }
            if (!jb.isNull("country") && jb.get("country") != null) {
                customer.setCountry(jb.get("country").toString());
            }
            if (!jb.isNull("headimgurl") && jb.get("headimgurl") != null) {
                customer.setHeadImageUrl(jb.get("headimgurl").toString());
            }
            if (!jb.isNull("unionid") && jb.get("unionid") != null) {
                customer.setUnionid(jb.get("unionid").toString());
            }
        } catch (Exception e) {
            System.out.println("接口获取用户信息报错,accessToken：" + accessToken + " openid:" + openid);
        }
        return customer;
    }
    
    public static Map getsessionKey(String appid,String secret,String code){
    	Map result = new HashMap();
    	try {
    	HttpResponse<JsonNode> jsonResponse;
			jsonResponse = Unirest
			        .post(OAUTH_URL_SESSIONKEY)
			        .queryString("appid", appid)
			        .queryString("secret", secret)
			        .queryString("js_code", code)
			        .queryString("grant_type", "authorization_code")
			        .asJson();
        JSONObject jb = jsonResponse.getBody().getObject();
        System.out.println(jb.toString());
        if (!jb.isNull("session_key") && jb.get("session_key") != null) {
            String sessionkey = jb.get("session_key").toString();
            result.put("sessionkey", sessionkey);
        }
        if (!jb.isNull("openid") && jb.get("openid") != null) {
            String openid = jb.get("openid").toString();
            result.put("openId", openid);
        }
        if (!jb.isNull("unionid") && jb.get("unionid") != null) {
            String openid = jb.get("unionid").toString();
            result.put("unionId", openid);
        }
		} catch (Exception e) {
			System.out.println("获取sessionkey接口报错");
		}
		return result;
    }

    
    private static String byteToStr(byte[] byteArray) {
        StringBuilder strDigest = new StringBuilder();
        for (int i = 0; i < byteArray.length; i++) {
            strDigest.append(byteToHexStr(byteArray[i]));
        }
        return strDigest.toString();
    }
    
    
    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
                'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        String s = new String(tempArr);
        return s;
    }
    
    public static boolean checkSignature(String signature, String timestamp,
            String nonce) {
    	String token = "lightspace";
        // 1.将token、timestamp、nonce三个参数进行字典序排序
        String[] arr = new String[] { token, timestamp, nonce };
        Arrays.sort(arr);

        // 2. 将三个参数字符串拼接成一个字符串进行sha1加密
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        content = null;
        System.out.println("接入微信验证："+tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false);
        // 3.将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }
    
    
 // 菜单创建（POST） 限100（次/天）
    public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
 
    
    
    
    /**
     * 创建菜单
     * 
     * @param menu 菜单实例
     * @param accessToken 有效的access_token
     * @return 0表示成功，其他值表示失败
     */
    public static int createMenu(Menu menu, String accessToken) {
        int result = 0;
        // 拼装创建菜单的url
        String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
        // 将菜单对象转换成json字符串
        String jsonMenu = net.sf.json.JSONObject.fromObject(menu).toString();
        System.out.println("cs-->"+jsonMenu);
        // 调用接口创建菜单
        net.sf.json.JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);
        if (null != jsonObject) {
            if (0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode");
                log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
 
        return result;
    }
    
    /**
     * 描述:  发起https请求并获取结果
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static net.sf.json.JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        net.sf.json.JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
 
            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);
 
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);
 
            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();
 
            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
 
            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
 
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = net.sf.json.JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("Weixin server connection timed out.");
        } catch (Exception e) {
            log.error("https request error:{}", e);
        }
        return jsonObject;
    }


}