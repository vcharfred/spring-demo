package top.vchar;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;
import top.vchar.util.JwtTokenProvider;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> jsonwebtoken包jwt测试 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/20
 */
@Slf4j
public class JsonwebtokenTest {

    private JwtTokenProvider jwtTokenProvider;
    @Before
    public void init(){
        jwtTokenProvider = new JwtTokenProvider("d2FsbGV0MjAyMW1vbmV5MQ==");
    }

    @Test
    public void generateToken(){
        String str = Base64.encodeBase64String("wallet2021money1".getBytes(StandardCharsets.UTF_8));
        System.out.println(str);

        Map<String, Object> claims = new HashMap<>();
        claims.put("uid", "9527");
        claims.put("iss", "vchar");
        String token = jwtTokenProvider.generateToken(claims, "张三", 1000*60);
        System.out.println(token);
        Claims body = jwtTokenProvider.parseToken(token);
        System.out.println(body);
    }








}
