package top.vchar.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <p> HMAC SHA1算法 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/20
 */
public class HmacSha1Util {

    private static final String HMAC_SHA1 = "HmacSHA1";

    /**
     * 生成HMAC
     * @param key 密钥
     * @param str 加密串
     * @return 返回加密后的 encodeBase64URLSafeString
     */
    public static String hmac(String key, String str) {
        try {
            SecretKey secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), HMAC_SHA1);
            Mac instance = Mac.getInstance(HMAC_SHA1);
            instance.init(secretKey);
            byte[] bytes = instance.doFinal(str.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeBase64URLSafeString(bytes);
        }catch (NoSuchAlgorithmException | InvalidKeyException e){
            e.printStackTrace();
        }
        return null;
    }

}
