package top.vchar.redis.zset;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;
import top.vchar.RedisApplication;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p> 有序set案例示例 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/3/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApplication.class)
public class ZsetTemplate {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 排序的set
     */
    @Test
    public void sortSetDemo() {
        ZSetOperations<String, String> opsForZSet = redisTemplate.opsForZSet();

        // 添加数据，会根据分数从小到大排序
        opsForZSet.add("music_top", "tom", 100);
        opsForZSet.add("music_top", "jerry", 10);
        opsForZSet.add("music_top", "mary", 10);
        opsForZSet.add("music_top", "nike", 0);
        opsForZSet.add("music_top", "coco", 10);

        // 统计分数在0~10之间的数量
        Long count = opsForZSet.count("music_top", 0, 10);
        System.out.println(count);

        // 获取指定位置的数据，下标从0开始
        Set<String> music = opsForZSet.range("music_top", 1, 2);
        System.out.println("指定位置正序：" + JSON.toJSONString(music));
        music = opsForZSet.reverseRange("music_top", 1, 2);
        System.out.println("指定位置到序：" + JSON.toJSONString(music));

        // 返回指定分数区间的值
        music = opsForZSet.rangeByScore("music_top", 8, 20);
        System.out.println("指定分数区间正序：" + JSON.toJSONString(music));
        music = opsForZSet.reverseRangeByScore("music_top", 8, 20);
        System.out.println("指定分数区间到序：" + JSON.toJSONString(music));


        // 获取指定值字典序之间的值；注意集合中的分数必须一样该命令才会生效；否则无法返回正确的结果
        RedisZSetCommands.Range range = new RedisZSetCommands.Range();
        range.gte("mary");
        range.lte("tom");
        music = opsForZSet.rangeByLex("music_top", range);
        System.out.println("指定值字典序之间的值：" + JSON.toJSONString(music));
        opsForZSet.add("music_top_lex", "tom", 10);
        opsForZSet.add("music_top_lex", "jerry", 10);
        opsForZSet.add("music_top_lex", "mary", 10);
        opsForZSet.add("music_top_lex", "nike", 10);
        music = opsForZSet.rangeByLex("music_top_lex", range);
        System.out.println("指定值字典序之间的值：" + JSON.toJSONString(music));

    }

    /**
     * 排行榜功能
     */
    @Test
    public void gameLeaderboard() {
        GameLeaderboard gameLeaderboard = new GameLeaderboard(this.redisTemplate);
        gameLeaderboard.add("apple");
        gameLeaderboard.add("bank");
        gameLeaderboard.add("car");
        gameLeaderboard.add("dance");
        gameLeaderboard.add("fuck");

        gameLeaderboard.inc("apple", 1);
        gameLeaderboard.inc("bank", 2);
        gameLeaderboard.inc("car", 5);
        gameLeaderboard.inc("dance", 3);
        gameLeaderboard.inc("fuck", 4);

        // 获取排名信息
        System.out.println(JSON.toJSONString(gameLeaderboard.leaderboard()));
    }

    /**
     * 购买商品后，智能给用户推荐可能需要购买的商品
     */
    @Test
    public void goodsRecommend() {
        GoodsRecommend goodsRecommend = new GoodsRecommend(this.redisTemplate);

        List<Long> goodsCar = Lists.newArrayList(1L, 3L, 4L);
        goodsRecommend.buyGoods(goodsCar);
        Map<Long, String> recommend = goodsRecommend.recommend(goodsCar);
        System.out.println("推荐购买：" + JSON.toJSONString(recommend));

        recommend = goodsRecommend.recommend(Lists.newArrayList(1L));
        System.out.println("推荐购买：" + JSON.toJSONString(recommend));
    }

    /**
     * 输入框智能提示
     */
    @Test
    public void inputPrompt() {
        InputPrompt prompt = new InputPrompt(this.redisTemplate);
        prompt.buildPrompt("中国");
        prompt.buildPrompt("中国四川");
        prompt.buildPrompt("中国四川成都");

        // 获取提示信息
        System.out.println(prompt.search("中国"));
    }

}
