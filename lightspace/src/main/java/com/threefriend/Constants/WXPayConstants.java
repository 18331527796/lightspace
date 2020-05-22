package com.threefriend.Constants;

import com.threefriend.lightspace.enums.AccountEnums;

public class WXPayConstants {

	 //第三方用户唯一ID
    public static String APPID = "";
    //第三方用户唯一凭证密码
    public static String APP_SECRET = "";
    //商户ID
    public static String MCH_ID = "1594367901";
    //微信商户平台-账户设置-安全设置-api安全,配置32位key
    //guangliangkongjian
    public static String KEY  = "295b843b97b715b53c707c090f41b70c";
    //交易类型
    public static String TRADE_TYPE_JS = "JSAPI";
    //微信支付回调url
    public static String NOTIFY_URL = "http://www.guangliangkongjian.com/lightspace/xcx/payReport";
    
    
    
    public static void set(String notifyUrl,String appId,String appSecret){
    	APPID=appId;
    	APP_SECRET=appSecret;
    	NOTIFY_URL=notifyUrl;
    }
    
    public enum SignType {
        MD5, HMACSHA256
    }

    public static final String DOMAIN_API = "api.mch.weixin.qq.com";
    public static final String DOMAIN_API2 = "api2.mch.weixin.qq.com";
    public static final String DOMAIN_APIHK = "apihk.mch.weixin.qq.com";
    public static final String DOMAIN_APIUS = "apius.mch.weixin.qq.com";


    public static final String FAIL     = "FAIL";
    public static final String SUCCESS  = "SUCCESS";
    public static final String HMACSHA256 = "HMAC-SHA256";
    public static final String MD5 = "MD5";

    public static final String FIELD_SIGN = "sign";
    public static final String FIELD_SIGN_TYPE = "sign_type";
}
