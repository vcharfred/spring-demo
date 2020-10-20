package top.vchar.util;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import top.vchar.common.exception.BizRunTimeException;
import top.vchar.common.exception.SignException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.Map;

/**
 * <p> 基于jwt 的token工具类 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/20
 */
@Slf4j
public class JwtTokenProvider {

    /**
     * 生成签名的时候使用的秘钥secret，签发jwt时需要，如果泄漏那么任何人都可以签名jwt了
     */
    private final SecretKey secretKey;

    /**
     * 创建token实例
     * @param base64String base64规范的字符串
     */
    public JwtTokenProvider(String base64String){
        this.secretKey = generalKey(base64String);
    }

    /**
     * 根据给定的字符串使用AES加密算法构造一个密钥
     *
     */
    public SecretKey generalKey(String key) {
        byte[] encodedKey = Base64.decodeBase64(key);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    /**
     * 生成token
     * @param claims payload的私有声明
     * @param sub 代表这个JWT的主体，即所属者
     * @param expiration 有效时间，单位毫秒
     * @return 返回生成的token
     */
    public String generateToken(Map<String, Object> claims, String sub, long expiration){
        // 生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        try{
            // 为payload添加各种标准声明和私有声明
            return Jwts.builder().setClaims(claims).setSubject(sub).setIssuedAt(new Date(nowMillis))
                    .setExpiration(new Date(nowMillis+expiration)).signWith(SignatureAlgorithm.HS512, secretKey).compact();
        }catch (Exception e){
            log.error("生成token异常", e);
            throw new BizRunTimeException("信息异常，请联系管理员");
        }
    }

    /**
     * 解析token
     * @param token token
     * @return 返回解析结果
     */
    public Claims parseToken(String token) {
        try{
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        }catch (Exception e){
            log.warn("token解析异常", e);
            throw new SignException("无效的token");
        }
    }

    public <T> T parseTokenToObj(String token, Class<T> classes){
        Claims claims = parseToken(token);
        return JSONObject.parseObject(JSONObject.toJSONString(claims), classes);
    }

    /**
     * 验证token是否过期
     * @param token token信息
     * @return 过期返回true；否则返回false
     */
    public boolean isExpiration(String token){
        try{
            Claims claims = parseToken(token);
            Date expiration = claims.getExpiration();
            if(expiration==null){
                return false;
            }
            return expiration.before(new Date());
        }catch (Exception e) {
            log.warn("解析token过期时间异常", e);
        }
        return true;
    }

}
