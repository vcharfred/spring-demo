package top.vchar.redis;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import top.vchar.RedisApplication;
import top.vchar.entity.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p> redis示例 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/2/24
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApplication.class)
public class RedisTemplateTest {

    @Autowired
    private RedisTemplate<String, Long> redisTemplate;

    @Autowired
    private RedisTemplate<String, String> stringRedisTemplate;


    /**
     * 字符串相关操作
     */
    @Test
    public void stringDemo() {
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();

        // 简单的存储
        opsForValue.set("key", "value");
        // 保存key，同时设置有效期为60s; 最后一个是时间单位
        opsForValue.set("key_time", "value", 60, TimeUnit.SECONDS);

        // 累加，默认累加1
        opsForValue.increment("order_id_default");
        // 累加，加指定的值
        opsForValue.increment("order_id", 3);
        opsForValue.increment("order_id_double", 0.1);

        // 累减，默认累减1
        opsForValue.decrement("order_id_default");
        // 累减，减指定的值
        opsForValue.decrement("order_id", 2);

        // 批量设置key-value
        Map<String, String> member = new HashMap<>();
        member.put("name", "张三");
        member.put("age", "10");
        opsForValue.multiSet(member);
        List<String> list = opsForValue.multiGet(Lists.newArrayList("name", "age"));
        System.out.println(list);

        // 统计存储的字符串的长度；中文字符在UTF-8编码下一个中文字符是3
        System.out.println(opsForValue.size("name"));

        // 截取一部分，由于中文字符的问题，得到的结果可能会有乱码
        System.out.println(opsForValue.get("name", 0, 3));

        // Nx命令：不存在则设置，否则不操作返回false；并发条件下redis将保证最终只有一个可以操作成功
        opsForValue.setIfAbsent("lock_flag", "1");

        // 追加实现
        opsForValue.setIfAbsent("2020-02-24", "");
        opsForValue.append("2020-02-24", "追加字符串");
        System.out.println(opsForValue.get("2020-02-24"));


    }

    /**
     * 基于redis的hash数据类型实现IP次数的统计
     */
    @Test
    public void hashDemo() {
        HashOperations<String, String, Long> opsForHash = redisTemplate.opsForHash();
        // 累加
        opsForHash.increment("ip_review_count", "127.0.0.1", 1);
        opsForHash.increment("ip_review_count", "127.0.0.1", 1);
        opsForHash.increment("ip_review_count", "127.0.0.2", 1);
        // 获取值
        System.out.println(opsForHash.get("ip_review_count", "127.0.0.1"));
        // 获取所有的值，数据量的时候不要直接用这种方式获取值，否则将耗费大量的服务内存
        Map<String, Long> reviewCount = opsForHash.entries("ip_review_count");
        System.out.println(reviewCount);

        // 使用hash来存储对象
        HashOperations<String, String, Object> opsForHash2 = redisTemplate.opsForHash();
        List<Student> students = new ArrayList<>();
        Student stu1 = Student.create();
        students.add(stu1);
        Student stu2 = Student.create();
        stu2.setId(2L);
        stu2.setName("张三");
        students.add(stu2);
        for (Student stu : students) {
            opsForHash2.putAll("sut" + stu.getId(), JSON.parseObject(JSON.toJSONString(stu)));
        }
        System.out.println(opsForHash2.entries("sut1"));

        // 局部更新：只更新sut2的名字为李四
        opsForHash2.put("sut2", "name", "李四");
        System.out.println(opsForHash2.entries("sut2"));
    }

}
