package top.vchar.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpMethod;

import java.util.Map;

/**
 * <p> 签名工具类 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/21
 */
public class SignUtil {

    /**
     * 签名
     * @param url 请求地址
     * @param method 请求方式
     * @param timestamp 时间戳
     * @param nonce 随机字符串
     * @param data 参数
     * @return 返回签名
     */
    public static String sign(String url, HttpMethod method, long timestamp, String nonce, Map<String, Object> data){
        StringBuilder signStr = new StringBuilder();
        signStr.append(method.name()).append("\n").append(url).append("\n?")
                .append("timestamp=").append(timestamp).append("&nonce=").append(nonce).append("&data=").append(JSONObject.toJSONString(data));
        System.out.println(signStr.toString());
        return HmacSha1Util.hmac("123456", signStr.toString());
    }

}
