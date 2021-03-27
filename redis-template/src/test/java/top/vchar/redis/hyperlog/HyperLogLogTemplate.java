package top.vchar.redis.hyperlog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HyperLogLogOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import top.vchar.RedisApplication;

import java.time.LocalDate;

/**
 * <p> HyperLogLog的示例 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/3/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApplication.class)
public class HyperLogLogTemplate {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * hyperLogLog 的使用场景：比如要统计的用户日活UV等信息，我们如何使用set的话，那么最终在redis中会存储大量的值（而这个几乎对我们没有什么大的用处），耗费大量内存；
     * 而使用hyperLogLog则可以避免这个问题，1个hyperLogLog占用内存大约为12kb; 需要注意的是这是个近似的值
     */
    @Test
    public void hyperLogLogDemo() {
        HyperLogLogOperations<String, String> opsForHyperLogLog = redisTemplate.opsForHyperLogLog();

        String key = "hyperLogLog_uv:" + LocalDate.now().toString();
        for (int i = 0; i < 1921; i++) {
            opsForHyperLogLog.add(key, String.valueOf(i));
            if (i > 1000) {
                // 添加函数：如果添加的值存在则返回0，否则返回1
                opsForHyperLogLog.add(key + 1, String.valueOf(i + 500));
            }
        }

        // 当前数量
        System.out.println("total_uv: " + opsForHyperLogLog.size(key, key + 1));
        System.out.println("uv: " + opsForHyperLogLog.size(key));
        System.out.println("uv_1: " + opsForHyperLogLog.size(key + 1));

        // 将后面的key合并到第一key里面
        System.out.println("共计: " + opsForHyperLogLog.union(key, key + 1));
        System.out.println("uv: " + opsForHyperLogLog.size(key));
        System.out.println("uv_1: " + opsForHyperLogLog.size(key + 1));

        // 删除key
        opsForHyperLogLog.delete(key + 1);
        System.out.println("total_uv: " + opsForHyperLogLog.size(key, key + 1));

    }

    /**
     * 网站评论垃圾重复信息过滤；业务场景描述：评论区被人提交一些广告等不良信息如何过滤屏蔽
     */
    @Test
    public void garbageFilter() {
        HyperLogLogOperations<String, String> opsForHyperLogLog = redisTemplate.opsForHyperLogLog();
        // 添加函数：如果添加的值存在则返回0，否则返回1
        long flag = opsForHyperLogLog.add("garbage_filter", "正常提交");
        System.out.println(flag == 1 ? "不存在" : "存在");

        flag = opsForHyperLogLog.add("garbage_filter", "广告");
        System.out.println(flag == 1 ? "不存在" : "存在");
        flag = opsForHyperLogLog.add("garbage_filter", "广告");
        System.out.println(flag == 1 ? "不存在" : "存在");
    }


}
