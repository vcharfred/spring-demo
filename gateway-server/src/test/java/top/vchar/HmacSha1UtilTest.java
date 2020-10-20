package top.vchar;

import org.junit.Test;
import top.vchar.util.HmacSha1Util;

/**
 * <p> Hmac Sha1算法测试 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/20
 */
public class HmacSha1UtilTest {

    @Test
    public void hmac(){
        String key = "123456";
        String str = "=08754321!@#$%^&*()";
        String hmac = HmacSha1Util.hmac(key, str);
        System.out.println(hmac);
    }
}
