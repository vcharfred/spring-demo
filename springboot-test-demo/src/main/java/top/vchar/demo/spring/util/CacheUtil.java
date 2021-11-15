package top.vchar.demo.spring.util;

import java.util.HashMap;
import java.util.Map;

/**
 * <p> 缓存工具类 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/11/15
 */
public class CacheUtil {

    private static Map<String, Object> map = new HashMap<>();

    public static void setVal(String key, Object val){
        System.out.println("缓存数据"+key);
        map.put(key, val);
    }

    public static Object getVal(String key){
        System.out.println("查询缓存："+key);
        return map.get(key);
    }
}
