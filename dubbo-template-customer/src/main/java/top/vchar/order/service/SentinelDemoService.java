package top.vchar.order.service;

/**
 * <p> Sentinel 示例接口 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/3/16
 */
public interface SentinelDemoService {

    /**
     * sentinel 熔断限流示例1
     *
     * @return 返回结果
     */
    String sentinelDemo1();

    /**
     * sentinel 熔断限流示例2; 基于动态配置实现
     *
     * @return 返回结果
     */
    String sentinelDemo2();
}
