package top.vchar.user.service;

import java.math.BigDecimal;

/**
 * <p> 利润分成接口 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/5/11
 */
public interface ProfitsShareService {

    /**
     * 获取机构标识
     *
     * @return 返回机构标识
     */
    String getOrgType();

    /**
     * 分配利润
     *
     * @param uid   用户ID
     * @param type  业务类型
     * @param money 分润金额
     * @return 返回分润结果
     */
    String allocation(Long uid, int type, BigDecimal money);

}
