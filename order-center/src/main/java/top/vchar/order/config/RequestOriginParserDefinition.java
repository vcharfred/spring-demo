package top.vchar.order.config;

import com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> sentinel自定义授权来源获取规则 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/6/15
 */
@Component
public class RequestOriginParserDefinition implements RequestOriginParser {

    /**
     * 定义区分来源的规则：本质上是通过获取request域中获取来源标识，然后交给流控应用来进行匹配处理
     *
     * @param request request域
     * @return 返回区分来源的值
     */
    @Override
    public String parseOrigin(HttpServletRequest request) {
        String client = request.getHeader("client");
        if (StringUtils.isNotBlank(client)) {
            return "NONE";
        }
        return client;
    }
}
