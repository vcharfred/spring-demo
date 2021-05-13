package top.vchar.user.service.impl.profits;

import org.springframework.stereotype.Service;
import top.vchar.user.service.ProfitsShareService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> 李四的分润实现 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/5/11
 */
@Service
public class LsProfitsShareServiceImpl implements ProfitsShareService {

    @Override
    public String getOrgType() {
        return "ls";
    }

    @Override
    public String allocation(Long uid, int type, BigDecimal money) {
        Map<Long, BigDecimal> map = findRate(uid, type);
        StringBuilder sb = new StringBuilder("李四的分润结果：");
        map.forEach((k, v) -> sb.append(k).append(":").append(money.multiply(v)).append(", "));
        return sb.toString();
    }

    private Map<Long, BigDecimal> findRate(Long uid, int type) {
        Map<Long, BigDecimal> map = new HashMap<>(2);
        switch (type) {
            case 1:
                // 业务1
                map.put(uid, new BigDecimal("0.1"));
                map.put(3L, new BigDecimal("0.3"));
                map.put(0L, new BigDecimal("0.6"));
                break;
            case 2:
                // 业务2
                map.put(uid, new BigDecimal("0.25"));
                map.put(3L, new BigDecimal("0.35"));
                map.put(0L, new BigDecimal("0.40"));
                break;
            default:
                map.put(3L, new BigDecimal("0.40"));
                map.put(0L, new BigDecimal("0.60"));
                break;
        }
        return map;
    }


}
