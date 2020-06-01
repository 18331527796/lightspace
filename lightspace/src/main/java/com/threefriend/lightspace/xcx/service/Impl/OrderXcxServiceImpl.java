package com.threefriend.lightspace.xcx.service.Impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.threefriend.Constants.WXPayConstants;
import com.threefriend.lightspace.enums.AccountEnums;
import com.threefriend.lightspace.enums.DeliveryTypeEnums;
import com.threefriend.lightspace.enums.OrderStatusEnum;
import com.threefriend.lightspace.enums.ResultEnum;
import com.threefriend.lightspace.enums.UrlEnums;
import com.threefriend.lightspace.mapper.StudentMapper;
import com.threefriend.lightspace.mapper.xcx.IntegralMapper;
import com.threefriend.lightspace.mapper.xcx.OrderMapper;
import com.threefriend.lightspace.mapper.xcx.ProductMapper;
import com.threefriend.lightspace.mapper.xcx.SpecificationsMapper;
import com.threefriend.lightspace.repository.IntegralRepository;
import com.threefriend.lightspace.repository.OrderRepository;
import com.threefriend.lightspace.repository.ProductRepository;
import com.threefriend.lightspace.repository.SpecificationsRepository;
import com.threefriend.lightspace.repository.StudentRepository;
import com.threefriend.lightspace.util.HttpUtils;
import com.threefriend.lightspace.util.ResultVOUtil;
import com.threefriend.lightspace.util.WXPayUtil;
import com.threefriend.lightspace.vo.ResultVO;
import com.threefriend.lightspace.xcx.service.OrderXcxService;

/**
 * 订单实现类
 * @author Administrator
 *
 */
@Service
public class OrderXcxServiceImpl implements OrderXcxService {

	@Autowired
	private OrderRepository order_dao;
	@Autowired
	private ProductRepository product_dao;
	@Autowired
	private StudentRepository student_dao;
	@Autowired
	private IntegralRepository integral_dao;
	@Autowired
	private SpecificationsRepository specifications_dao;

	/* 
	 * 新增
	 */
	@Override
	public ResultVO addOrder(Map<String, String> params) {
		String openId = params.get("openId");
		Integer number = Integer.valueOf(params.get("number"));
		Integer  delivryType= Integer.valueOf(params.get("delivryType"));
		StudentMapper student = student_dao.findById(Integer.valueOf(params.get("studentId"))).get();
		SpecificationsMapper specifications = specifications_dao
				.findById(Integer.valueOf(params.get("specificationsId"))).get();
		ProductMapper product = product_dao.findById(specifications.getProductId()).get();
		Long studentIntegral = integral_dao.findIntegtalByStudentId(student.getId());
		Long paymoney = 0l;
		if (specifications.getStock() < number)
			return ResultVOUtil.error(ResultEnum.STOCK_ERROR.getStatus(), ResultEnum.STOCK_ERROR.getMessage());
		if (specifications.getIntegral()*number > (studentIntegral == null ? 0 : studentIntegral))
			return ResultVOUtil.error(ResultEnum.INTEGRAL_ERROR.getStatus(), ResultEnum.INTEGRAL_ERROR.getMessage());
		
		String pic = UrlEnums.IMG_URL.getUrl()+product.getPicture().split(",")[0];
		
		OrderMapper newOrder = new OrderMapper();
		newOrder.setDelivrytype((Integer.valueOf(params.get("delivryType")) == DeliveryTypeEnums.HOME.getCode())
				? DeliveryTypeEnums.HOME.getMessage()
				: DeliveryTypeEnums.SCHOOL.getMessage());
		if (!StringUtils.isEmpty(params.get("remark")))
			newOrder.setRemark(params.get("remark"));
		newOrder.setContacts(params.get("contacts"));
		newOrder.setPhone(params.get("phone"));
		newOrder.setNumber(number);
		newOrder.setPaymoney(paymoney);
		newOrder.setProductId(product.getId());
		newOrder.setProductName(product.getName());
		newOrder.setStudentId(student.getId());
		newOrder.setSchoolId(student.getSchoolId());
		newOrder.setSchoolName(student.getSchoolName());
		newOrder.setSpecificationId(specifications.getId());
		newOrder.setSpecificationName(specifications.getName());
		newOrder.setPic(pic);
		if (delivryType == DeliveryTypeEnums.HOME.getCode()) {
			newOrder.setFreight(specifications.getFreight());
			newOrder.setAddress(params.get("address"));
			paymoney += Long.valueOf(specifications.getFreight());
			newOrder.setStatus(OrderStatusEnum.NEW.getMessage());
			newOrder.setDisplay(2);
		}else {
			newOrder.setStatus(OrderStatusEnum.SUCCESS.getMessage());
			newOrder.setDisplay(1);
			integral_dao.save(new IntegralMapper(newOrder.getStudentId(), 0, specifications.getIntegral()*newOrder.getNumber(), "兑换"+newOrder.getProductName(), new Date()));
		}
		order_dao.save(newOrder);

		if (delivryType != DeliveryTypeEnums.HOME.getCode()) {
			return ResultVOUtil.success();
		}else {
			Map<String, String> createOrder = createOrder(openId,paymoney,newOrder.getId(),product.getName());
			createOrder.put("signType", "MD5");
			System.out.println(createOrder.toString());
			return ResultVOUtil.success(createOrder);
		}
	}

	/* 
	 * 小程序领取列表
	 */
	@Override
	public ResultVO orderByStudent(Map<String, String> params) {
		int page = 0 ;
		if(!StringUtils.isEmpty(params.get("page"))) page = Integer.valueOf(params.get("page")) - 1;
		List<OrderMapper> content = order_dao.findByStudentIdAndDisplayOrderByGenTimeDateDesc(Integer.valueOf(params.get("studentId")),1,PageRequest.of(page, 10)).getContent();
		return ResultVOUtil.success(content);
	}

	/**
	 * 请求预支付标识
	 * @param openid
	 * @param money
	 * @param orderId
	 * @return
	 */
	@Override
	public Map<String, String> createOrder(String openid, Long money , int orderId , String productName) {

		String mch_id = WXPayConstants.MCH_ID; // 商户号

		String today = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String WXPay = WXPayUtil.createCode(8);
		String out_trade_no =orderId+"-"+ WXPay;// 生成订单号
		//String out_trade_no ="lightspace-"+orderId+"-"+ WXPay;// 生成订单号
		Map<String, String> result = new HashMap<String, String>();
		String formData = getopenid(openid, out_trade_no, money ,productName);
		// 在servlet层中生成签名成功后，把下单所要的参数以xml的格式拼接，发送下单接口
		String httpResult = HttpUtils.httpXMLPost("https://api.mch.weixin.qq.com/pay/unifiedorder", formData);
		try {
			// xml转换成Map对象或者值
			Map<String, String> resultMap = WXPayUtil.xmlToMap(httpResult);
			result.put("package", "prepay_id=" + resultMap.get("prepay_id")); // 这里是拿下单成功的微信交易号去拼接，因为在下面的接口中必须要这个样子
			result.put("nonceStr", resultMap.get("nonce_str")); // 随机字符串
		} catch (Exception e) {
			e.printStackTrace();
		}

		String times = WXPayUtil.getCurrentTimestamp() + ""; // 获取当前时间
		result.put("timeStamp", times); // 当前时间戳
		// 生成调用支付接口要的签名
		Map<String, String> packageParams = new HashMap<String, String>();
		packageParams.put("appId", AccountEnums.APIKEY.getUrl());
		packageParams.put("signType", WXPayConstants.MD5);
		packageParams.put("nonceStr", result.get("nonceStr") + "");
		packageParams.put("timeStamp", times);
		packageParams.put("package", result.get("package") + "");// 商户订单号
		String sign = "";
		try {
			sign = WXPayUtil.generateSignature(packageParams, WXPayConstants.KEY); // 生成签名:
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("paySign", sign);
		System.out.println("sign:" + result.get("paySign"));
		return result; // 所有的参数放进map中保存发送到小程序页面中,去调用微信支付接口
	}

	/**
     * 统一下单
     * @param openid 用户标识
     * @param out_trade_no 订单号
     * @param total_fee 金额
     * @return String
     */
    @Override
    public String getopenid(String openid,String out_trade_no,Long total_fee,String productName) {

        //下单的金额，因为在微信支付中默认是分所以要这样处理
        Long total_fees=total_fee*100;
        //微信下单的金额是String类型的所以要转换类型
        String money=/*total_fees.toString();*/ "1";
        String nonceStr=WXPayUtil.generateUUID(); //设置UUID作为随机字符串

        Map<String ,String> map = new HashMap<String ,String>();
        map.put("appid",AccountEnums.APIKEY.getUrl()); //商户appid
        map.put("mch_id", WXPayConstants.MCH_ID);//商户号
        map.put("nonce_str",nonceStr); //随机数
        map.put("body",productName);//商户名称
        map.put("out_trade_no",out_trade_no);//商户订单号
        map.put("total_fee",money);//下单金额
        map.put("spbill_create_ip", "47.104.222.22");//终端IP
        map.put("notify_url",WXPayConstants.NOTIFY_URL);//回调地址 这里的接口必须是在线上用户支付成功才能收到微信发送的信息
        map.put("trade_type",WXPayConstants.TRADE_TYPE_JS);//交易类型
        map.put("openid",openid);//用户openid
        map.put("sign_type",WXPayConstants.MD5);//加密类型
        String sign="";
        try {
        sign= WXPayUtil.generateSignature(map, WXPayConstants.KEY); //生成sign签名WeChatTool.sercet_key是商户的支付秘钥
        } catch (Exception e) {
            e.printStackTrace();
        }
        //拼接成xml的格式，这里的参数必须要和上面的一致，并且每次下单的订单号不能一致
        String formData="<xml>";

        formData += "<appid>"+ AccountEnums.APIKEY.getUrl()+"</appid>"; 
        formData += "<mch_id>"+ WXPayConstants.MCH_ID+"</mch_id>"; 
        formData += "<nonce_str>"+nonceStr+"</nonce_str>";
        formData += "<body>"+productName+"</body>";
        formData += "<out_trade_no>"+out_trade_no +"</out_trade_no>"; 
        formData += "<total_fee>"+money+"</total_fee>"; 
        formData += "<spbill_create_ip>47.104.222.22</spbill_create_ip>"; 
        formData += "<notify_url>"+WXPayConstants.NOTIFY_URL+"</notify_url>"; 
        formData += "<trade_type>"+WXPayConstants.TRADE_TYPE_JS+"</trade_type>";
        formData += "<openid>"+openid+"</openid>"; //appid
        formData += "<sign_type>"+WXPayConstants.MD5+"</sign_type>";
        formData += "<sign>"+sign+"</sign>"; //签名算法
        formData += "</xml>";
        return formData;
    }

	/*  
	 * 确认收货
	 */
	@Override
	public ResultVO confirmReceipt(Map<String, String> params) {
		Integer orderId = Integer.valueOf(params.get("id"));
		OrderMapper order = order_dao.findById(orderId).get();
		order.setStatus(OrderStatusEnum.FINISHED.getMessage());
		order.setSuccesstime(new Date());
		order.setGenTimeDate(new Date());
		order_dao.save(order);
		return ResultVOUtil.success();
	}

	/* 
	 * 取消显示
	 */
	@Override
	public ResultVO display(Map<String, String> params) {
		OrderMapper order = order_dao.findById(Integer.valueOf(params.get("id"))).get();
		order.setDisplay(2);
		order_dao.save(order);
		return ResultVOUtil.success();
	}
    
    

}
