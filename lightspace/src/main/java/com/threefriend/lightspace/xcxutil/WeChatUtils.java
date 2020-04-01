package com.threefriend.lightspace.xcxutil;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONException;
import org.json.JSONObject;
 
import java.util.HashMap;
import java.util.Map;
 
 
public class WeChatUtils {
 
    //网页授权获取access_token
    //public final static String OAUTH_URL_OPENID = "https://api.weixin.qq.com/cgi-bin/token";
    //网页授权获取用户信息
    //public final static String OAUTH_URL_USERINFO = "https://api.weixin.qq.com/sns/userinfo";
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
 
    *//**
     * 获取openId、accessToken
     *
     * @param appId
     * @param appSecret
     * @param code
     * @return
     *//*
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
            if (!jb.isNull("access_token") && jb.get("access_token") != null) {
                accessToken = jb.get("access_token").toString();
            }
        } catch (Exception e) {
            System.out.println("接口获取用户信息报错,appid：" + appId + " secret:" + appSecret );
        }
        return accessToken;
    }
 
    *//**
     * 获取微信用户信息
     *
     * @param accessToken
     * @param openid
     * @return
     *//*
    public static Customer findWxUserInfo(String accessToken, String openid) {
        Customer customer = new Customer();
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest
                    .post(OAUTH_URL_USERINFO)
                    .queryString("access_token", accessToken)
                    .queryString("openid", openid)
                    .queryString("lang", "zh_CN")
                    .asJson();
            JSONObject jb = jsonResponse.getBody().getObject();
            if (!jb.isNull("openid") && jb.get("openid") != null) {
                customer.setOpenid(jb.get("openid").toString());
            }
            if (!jb.isNull("nickname") && jb.get("nickname") != null) {
                customer.setNickname(jb.get("nickname").toString());
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
    }*/
    
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
        if (!jb.isNull("session_key") && jb.get("session_key") != null) {
            String sessionkey = jb.get("session_key").toString();
            result.put("sessionkey", sessionkey);
        }
        if (!jb.isNull("openid") && jb.get("openid") != null) {
            String openid = jb.get("openid").toString();
            result.put("openId", openid);
        }
		} catch (Exception e) {
			System.out.println("获取sessionkey接口报错");
		}
		return result;
    }

}