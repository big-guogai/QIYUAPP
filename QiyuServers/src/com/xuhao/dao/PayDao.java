package com.xuhao.dao;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import com.xuhao.utils.SignUtils;

public class PayDao {
	
    /**
     * 用于支付宝支付业务的入参 app_id。
     * 奇育2019111369095924
     * 沙箱2016101300679514
     */
    public static final String APPID = "2016101300679514";
    

    /**
     * pkcs8 格式的商户私钥。
     */
    public static final String RSA2_PRIVATE = "";
    public static final String RSA_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCQiocDY4FXSKVTwDuYl5lv5Xn9/hYj64Li9u7ZDjuab7S2dpTIip2hU+OknXSSFgEfJAWp26NbcaMaYQtKnuCIgrcUPplipMa5168BWsSjQE33ldCB3NO0p8ne8rEX1aAfr2CE9PUwgdYXY0xETCfJp2TpnsQkcLlqoR3PPZ0geC43GERqKP7wn407sANST0wr9bIoqeqomJc3hqXsMTGKj8k+pFHUkKODPwK5YYKqg1pDvyseaeBuT2xB9DwFqfCtXd6xmF6fW4iJZy6dMVBgiYv121GiFhHaKeFQHwwLxWZIXjr39FgSmyCUZbCztD+MvDtwJMfYB2TEZwI1agzlAgMBAAECggEAEYks7iVRWNSQckO9NzsVO3UcwsrjxsHY5E5PFfhbFNZ+lzB1xorCqwLxeFQ+A0c2JaAKK9xgykRUJGwnoYr69LTfH06k70nMblmTeteXSbMQgnwjis3OxXkhzbGl+J1LSuzNCfRqFtN5tOawJw0SOq3bWViQtVcTt7MKWc1A1eJvQXHUV0QarI9Y5WfeiZqqIVuM/T3kQILWaQjtELvIfDn3QH5O6xp83M0gohi1BxBlpJV8yH+Xj4fgisvS3FUvMmbnT5JYY6oJiutGIRyNU3xx29Fjnw9ZPQYe6lLWYSR/DDhH0siy8PoRX1fcsD+VCzgosTPh+gjwurmWYqzp4QKBgQDoBs2LdjPKY1m9Ibd+Ftc1EdiOzZQL2DturYOuactE6+nteknthK9Wwo2SU2vTIMTXctIj838TRyuTbauvaBCv42BA2EUL2sX9zv3Vq1pKHng1Hf0EwbBIWlvW5sfNu0l54LIvMNKjYM5xaXY1UPHHQczgqrQ6RkfqoiQ9T6XarQKBgQCfebLjKRGjKPpLtuhTut3wRX5lC3cvxuUhM8bRuIbKZM6jDY0vj0ul9jzGSxBnxyFuJWg8IewOH3aG7hgBxraJFceZU3XnhMmpm1CeUTfkSe5k56tRZuLlVnv40UgGEQcCX2MFdxKU/Z+pS2o3enK1P5hUEAWnCjuKXztss266GQKBgQDjWYceF/w3W5Ia2GbLjeXhglMH2jguQvo5QJu+0rryFZdpsmGt8k08XfYKYuNMRNebpE50G6twYFQ6aUPI49c2AT3XTBKbwwot4ByouPYHmkN/CJtbhc4NlzxWHlu5IsKkQZ9WzigXHkwqpIPHSkEeVUNDAQEH+MWPCsEnslt5IQKBgAgaZO6J7a1+rYkfWBq5CgHlZhC4TmwNDSNswBNz7tVl3eIHFD9wZoWeR2RD+CdtxNMV73/usVHeY2Vjv7CHakr44lV2Gi2TX7PQgn4o3lApOYXDB79xHOJOTKpN5/z27IZAVoW2kSkk4fH2NQVwoVd4TcMntUHRkUjsodZaH6wxAoGAMaQ4G60cJn5FmAyDkXhBqECXoMlkDcdHgonbVct5cDNvRcz3jh3DxOBZniXb35Lyx2Pmn/Eu/w9g0kVorMv7YZHallIMYNXpxeevPBoWPQmrCiP7ZUjwSyQhwfI55mQ6OpNvgDCK2DYWOAXQGbMFdVLoHuv6ZVQmwtVGWxjl0E0=";
    
    /**
     *支付宝支付业务
     * @param indentIntroduction 订单介绍
     * @param price 价格
     * @return 商品信息
     */
	public String payIndent(String indentIntroduction,float price) {
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = buildOrderParamMap(APPID, rsa2, price, indentIntroduction);
        String orderParam = buildOrderParam(params);
        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = getSign(params, privateKey, rsa2);
        String orderInfo = orderParam + "&" + sign;
        return orderInfo;
	}	
	/**
	 * 构造支付订单参数列表
	 */
	public static Map<String, String> buildOrderParamMap(String app_id, boolean rsa2,Float price,String indentIntroduction) {
		Map<String, String> keyValues = new HashMap<String, String>();

		keyValues.put("app_id", app_id);

		keyValues.put("biz_content", "{\"timeout_express\":\"30m\",\"product_code\":\"QUICK_MSECURITY_PAY\",\"total_amount\":\"" + price + "\",\"subject\":\"" + indentIntroduction + "\",\"body\":\"我是测试数据\",\"out_trade_no\":\"" + getOutTradeNo() +  "\"}");

		keyValues.put("charset", "utf-8");

		keyValues.put("method", "alipay.trade.app.pay");

		keyValues.put("sign_type", rsa2 ? "RSA2" : "RSA");

		keyValues.put("timestamp", "2016-07-29 16:55:53");

		keyValues.put("version", "1.0");

		return keyValues;
	}
	
	/**
	 * 构造支付订单参数信息
	 *
	 * @param map
	 * 支付订单参数
	 * @return
	 */
	public static String buildOrderParam(Map<String, String> map) {
		List<String> keys = new ArrayList<String>(map.keySet());

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < keys.size() - 1; i++) {
			String key = keys.get(i);
			String value = map.get(key);
			sb.append(buildKeyValue(key, value, true));
			sb.append("&");
		}

		String tailKey = keys.get(keys.size() - 1);
		String tailValue = map.get(tailKey);
		sb.append(buildKeyValue(tailKey, tailValue, true));

		return sb.toString();
	}
	
	/**
	 * 拼接键值对
	 *
	 * @param key
	 * @param value
	 * @param isEncode
	 * @return
	 */
	private static String buildKeyValue(String key, String value, boolean isEncode) {
		StringBuilder sb = new StringBuilder();
		sb.append(key);
		sb.append("=");
		if (isEncode) {
			try {
				sb.append(URLEncoder.encode(value, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				sb.append(value);
			}
		} else {
			sb.append(value);
		}
		return sb.toString();
	}
	
	/**
	 * 对支付参数信息进行签名
	 *
	 * @param map
	 *            待签名授权信息
	 *
	 * @return
	 */
	public static String getSign(Map<String, String> map, String rsaKey, boolean rsa2) {
		List<String> keys = new ArrayList<String>(map.keySet());
		// key排序
		Collections.sort(keys);

		StringBuilder authInfo = new StringBuilder();
		for (int i = 0; i < keys.size() - 1; i++) {
			String key = keys.get(i);
			String value = map.get(key);
			authInfo.append(buildKeyValue(key, value, false));
			authInfo.append("&");
		}

		String tailKey = keys.get(keys.size() - 1);
		String tailValue = map.get(tailKey);
		authInfo.append(buildKeyValue(tailKey, tailValue, false));

		String oriSign = SignUtils.sign(authInfo.toString(), rsaKey, rsa2);
		String encodedSign = "";

		try {
			encodedSign = URLEncoder.encode(oriSign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "sign=" + encodedSign;
	}
	
	/**
	 * 要求外部订单号必须唯一。
	 * @return
	 */
	private static String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
		Date date = new Date(0);
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}
}
