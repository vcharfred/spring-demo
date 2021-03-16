package top.vchar.order.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.stereotype.Service;
import top.vchar.order.service.SentinelDemoService;

/**
 * <p> Sentinel 示例接口实现 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/3/16
 */
@Service
public class SentinelDemoServiceImpl implements SentinelDemoService {

    /**
     * sentinel 熔断限流示例1
     *
     * @return 返回结果
     */
    @SentinelResource(value = "SentinelDemoService#sentinelDemo1", defaultFallback = "sentinelDemo1Fallback")
    @Override
    public String sentinelDemo1() {
        return "sentinel 示例1";
    }

    /**
     * sentinel 熔断限流示例2; 基于动态配置实现
     *
     * @return 返回结果
     */
    @Override
    public String sentinelDemo2() {
        return "sentinel 示例2";
    }

    /**
     * 失败的时候会调用此方法
     */
    public String sentinelDemo1Fallback(Throwable t) {
        if (BlockException.isBlockException(t)) {
            return "Blocked by Sentinel: " + t.getClass().getSimpleName();
        }
        return "Oops, failed: " + t.getClass().getCanonicalName();
    }

}
