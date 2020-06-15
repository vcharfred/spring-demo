package top.vchar.order.config;

import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlBlockHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p> Sentinel自定义异常返回 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/6/15
 */
@Component
public class SentinelExceptionHandler implements UrlBlockHandler {

    /**
     * 异常处理
     *
     * @param request  请求
     * @param response 响应
     * @param e        BlockException异常接口，包含Sentinel的五个异常
     *                 FlowException  限流异常
     *                 DegradeException  降级异常
     *                 ParamFlowException  参数限流异常
     *                 AuthorityException  授权异常
     *                 SystemBlockException  系统负载异常
     * @throws IOException IO异常
     */
    @Override
    public void blocked(HttpServletRequest request, HttpServletResponse response, BlockException e) throws IOException {
        JSONObject responseData = new JSONObject();
        if (e instanceof FlowException) {
            responseData.put("message", "限流异常");
            responseData.put("code", "C5001");
        } else if (e instanceof DegradeException) {
            responseData.put("message", "降级异常");
            responseData.put("code", "C5002");
        } else if (e instanceof ParamFlowException) {
            responseData.put("message", "参数限流异常");
            responseData.put("code", "C5003");
        } else if (e instanceof AuthorityException) {
            responseData.put("message", "授权异常");
            responseData.put("code", "C5004");
        } else if (e instanceof SystemBlockException) {
            responseData.put("message", "系统负载异常");
            responseData.put("code", "C5005");
        }
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(responseData.toJSONString());
    }
}
