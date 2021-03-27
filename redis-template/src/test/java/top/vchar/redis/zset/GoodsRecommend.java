package top.vchar.redis.zset;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p> 商品推荐案例 </p>
 * <p>
 * 业务场景：用户在购买了一批商品后，根据用户购买的商品内容，智能的推荐用户可能会购买的商品
 * <p>
 * 实现思路：每次用户购买商品后，就将这些商品组合放到set里面，同时分数加一，之后用户根据用户购买的商品来查询出相关联的商品
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/3/18
 */
public class GoodsRecommend {

    private final RedisTemplate<String, String> redisTemplate;
    private static Map<Long, String> productMap = new HashMap<>();

    public GoodsRecommend(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;

        productMap.put(1L, "手机");
        productMap.put(2L, "无线充电器");
        productMap.put(3L, "耳机");
        productMap.put(4L, "钢化模");
        productMap.put(5L, "手机套");
    }

    /**
     * 本次购买的商品
     *
     * @param productIds 本次购买的商品ID
     */
    public void buyGoods(List<Long> productIds) {
        for (Long productId : productIds) {
            continuePurchase(productId, productIds);
        }
    }

    /**
     * ZINCRBY
     */
    private void continuePurchase(Long productId, List<Long> productIds) {
        ZSetOperations<String, String> opsForZSet = redisTemplate.opsForZSet();
        for (Long otherProductId : productIds) {
            if (productId.compareTo(otherProductId) != 0) {
                // 每次购买商品后就其关联的商品的值累加1
                opsForZSet.incrementScore("goods_sell:" + productId, String.valueOf(otherProductId), 1);
            }
        }
    }

    /**
     * 商品推荐
     */
    public Map<Long, String> recommend(List<Long> productIds) {
        Map<Long, String> map = new HashMap<>();

        ZSetOperations<String, String> opsForZSet = redisTemplate.opsForZSet();
        for (Long productId : productIds) {
            // 取出对应的商品
            Set<String> set = opsForZSet.reverseRange("goods_sell:" + productId, 0, 1);
            if (null != set && !set.isEmpty()) {
                set.forEach(id -> map.put(Long.valueOf(id), productMap.get(Long.valueOf(id))));
            }
        }
        return map;
    }


}
