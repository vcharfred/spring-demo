package top.vchar.integral.service;

/**
 * <p> 积分接口 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/3/26
 */
public interface IntegralService {

    /**
     * 扣除积分
     *
     * @param id 用户ID
     * @param integral 扣除的积分
     * @return 返回结果
     */
    boolean deductIntegral(Long id, int integral);
}
