package top.vchar.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import top.vchar.common.response.ApiCode;
import top.vchar.common.exception.BizRunTimeException;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * xss拦截工具类
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/12
 */
public class XssUtil {

    private final static String RICH_TEXT = "</";

    /**
     * 自定义白名单
     */
    private final static Whitelist CUSTOM_WHITELIST = Whitelist.relaxed()
            .addAttributes("video", "width", "height", "controls", "alt", "src")
            .addAttributes(":all", "style", "class");

    /**
     * jsoup不格式化代码
     */
    private final static Document.OutputSettings OUTPUT_SETTINGS = new Document.OutputSettings().prettyPrint(false);

    /**
     * 清除json对象中的xss攻击字符
     *
     * @param val json对象字符串
     * @return 清除后的json对象字符串
     */
    private static String cleanObj(String val) {
        JSONObject jsonObject = JSONObject.parseObject(val);
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            if (entry.getValue() != null && entry.getValue() instanceof String) {
                String str = (String) entry.getValue();
                str = cleanXss(str);
                entry.setValue(str);
            }
        }
        return jsonObject.toJSONString();
    }

    /**
     * 清除json数组中的xss攻击字符
     *
     * @param val json数组字符串
     * @return 清除后的json数组字符串
     */
    private static String cleanArr(String val) {
        List<String> list = JSONObject.parseArray(val, String.class);
        JSONArray result = new JSONArray();
        for (String str : list) {
            str = cleanXss(str);
            if (JsonUtil.isJsonObj(str) || JsonUtil.isJsonArr(str)) {
                result.add(JSONObject.parseObject(str));
            } else {
                result.add(str);
            }
        }
        return result.toJSONString();
    }

    /**
     * 清除xss攻击字符串
     *
     * @param str 字符串
     * @return 清除后无害的字符串
     */
    public static String cleanXss(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        if (JsonUtil.isJsonObj(str)) {
            str = cleanObj(str);
        } else if (JsonUtil.isJsonArr(str)) {
            str = cleanArr(str);
        } else {
            boolean richText = richText(str);
            if (!richText) {
                str = str.trim();
                str = str.replaceAll(" +", " ");
            }
            String afterClean = Jsoup.clean(str, "", CUSTOM_WHITELIST, OUTPUT_SETTINGS);
            if (paramError(richText, afterClean, str)) {
                throw new BizRunTimeException(ApiCode.PARAM_ERROR.value(), "参数包含特殊字符");
            }
            str = richText ? afterClean : backSpecialStr(afterClean);
        }
        return str;
    }


    /**
     * 判断是否是富文本
     *
     * @param str 待判断字符串
     * @return true/false
     */
    private static boolean richText(String str) {
        return str.contains(RICH_TEXT);
    }

    /**
     * 判断是否参数错误
     *
     * @param richText   是否富文本
     * @param afterClean 清理后字符
     * @param str        原字符串
     * @return true/false
     */
    private static boolean paramError(boolean richText, String afterClean, String str) {
        // 如果包含富文本字符，那么不是参数错误
        if (richText) {
            return false;
        }
        // 如果清理后的字符和清理前的字符匹配，那么不是参数错误
        if (Objects.equals(str, afterClean)) {
            return false;
        }
        // 如果仅仅包含可以通过的特殊字符，那么不是参数错误
        if (Objects.equals(str, backSpecialStr(afterClean))) {
            return false;
        }
        // 如果还有
        return true;
    }

    /**
     * 转义回特殊字符
     *
     * @param str 已经通过转义字符
     * @return 转义后特殊字符
     */
    private static String backSpecialStr(String str) {
        return str.replaceAll("&amp;", "&");
    }

}
