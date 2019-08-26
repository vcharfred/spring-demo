package top.vchar.demo.spring.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * <p> json工具类 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/8/26 23:08
 */
public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();


    public static JsonNode str2JsonNode(String str){
        try {
            return objectMapper.readTree(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
