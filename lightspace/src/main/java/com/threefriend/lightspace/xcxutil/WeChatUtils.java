package com.threefriend.lightspace.xcxutil;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.threefriend.lightspace.mapper.GzhUserMapper;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
 
 
public class WeChatUtils {
	
 
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
}