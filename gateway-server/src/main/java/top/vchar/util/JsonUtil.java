package top.vchar.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

/**
 * JSON处理工具类
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/12
 */
public class JsonUtil {

    /**
     * json对象字符串开始标记
     */
    private final static String JSON_OBJECT_START = "{";

    /**
     * json对象字符串结束标记
     */
    private final static String JSON_OBJECT_END = "}";

    /**
     * json数组字符串开始标记
     */
    private final static String JSON_ARRAY_START = "[";

    /**
     * json数组字符串结束标记
     */
    private final static String JSON_ARRAY_END = "]";


    /**
     * 判断字符串是否json对象字符串
     *
     * @param val 字符串
     * @return true/false
     */
    public static boolean isJsonObj(String val) {
        if (StringUtils.isEmpty(val)) {
            return false;
        }
        val = val.trim();
        if (val.startsWith(JSON_OBJECT_START) && val.endsWith(JSON_OBJECT_END)) {
            try {
                JSONObject.parseObject(val);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    /**
     * 判断字符串是否json数组字符串
     *
     * @param val 字符串
     * @return true/false
     */
    public static boolean isJsonArr(String val) {
        if (StringUtils.isEmpty(val)) {
            return false;
        }
        val = val.trim();
        if (StringUtils.isEmpty(val)) {
            return false;
        }
        val = val.trim();
        if (val.startsWith(JSON_ARRAY_START) && val.endsWith(JSON_ARRAY_END)) {
            try {
                JSONObject.parseArray(val);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

}
