package top.vchar.redis;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;
import top.vchar.RedisApplication;
import top.vchar.entity.Student;

import java.util.*;
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

    /**
     * 无序不重复集合Set；注意set的结构是 Set<String> 这种格式的；可以用来存储登陆认证的token信息
     */
    @Test
    public void setDemo() {
        SetOperations<String, String> opsForSet = stringRedisTemplate.opsForSet();
        opsForSet.add("session", "token");
        opsForSet.add("session", "session");

        opsForSet.add("token", "token");
        opsForSet.add("token", "auth_token");

        // 查找2个集合中相同的元素；返回第一个key中相同的元素
        Set<String> intersect = opsForSet.intersect("session", "token");
        System.out.println("相同的元素：" + intersect);

        // 查找2个集合中不相同的元素；返回第一个key中不相同的元素
        Set<String> difference = opsForSet.difference("token", "session");
        System.out.println("不相同的元素：" + difference);

        // 随机取一个
        String session = opsForSet.randomMember("session");
        System.out.println("随机元素：" + session);

        // 将key为session的集合中的值session移到到token集合中去
        opsForSet.move("session", "session", "token");
        System.out.println("session的元素：" + opsForSet.members("session"));

        // 插入100w条数据
//        int i=0;
//        while (i<1000){
//            String[] sets = new String[1000];
//            for(int j=0; j<1000; j++){
//                sets[j] = UUID.randomUUID().toString();
//            }
//            opsForSet.add("set_max_size", sets);
//            i++;
//        }
        // 判断集合中是否存在 指定的值
        System.out.println("判断集合中是否存在 指定的值" + opsForSet.isMember("set_max_size", "39a036dc-6e0d-4b65-9b2e-06b09cbc4768"));

        // 和队列的pop操作一样
        String val = opsForSet.pop("session");
        System.out.println("pop的值(从头部推出一个值)：" + val);

        // 移除指定的值
        opsForSet.remove("token", "auth_token");
    }

    /**
     * list集合
     */
    @Test
    public void listDemo() {
        ListOperations<String, String> operations = stringRedisTemplate.opsForList();
        // 在队列的左边添加一个
        operations.leftPush("goods_id:1", "购买0个");
        operations.leftPush("goods_id:1", "购买1个");
        operations.leftPush("goods_id:1", "购买2个");
        operations.leftPush("goods_id:1", "购买3个");

        // 从右边取一个值; 因此可以基于左右推入和弹出的机制实现一个先进先出的队列
        String booking = operations.rightPop("goods_id:1");
        System.out.println("取出的值：" + booking);

        Long size = operations.size("goods_id:1");
        System.out.println("元素个数：" + size);

        List<String> range = operations.range("goods_id:1", 0, size);
        System.out.println("原始的数据：" + range);

        // 分页查询；后面的参数表示开始下标和结束下标；包含起始和结束的下标
        range = operations.range("goods_id:1", 1, 3);
        System.out.println("分页查询：" + range);

        // 在某个元素的左边插入一个新的元素
        operations.leftPush("goods_id:1", "购买1个", "新购4个");
        range = operations.range("goods_id:1", 0, operations.size("goods_id:1"));
        System.out.println("插入后的数据：" + range);

        // 更新某个下标的值
        operations.set("goods_id:1", 1, "新购4个");
        range = operations.range("goods_id:1", 0, operations.size("goods_id:1"));
        System.out.println("更新后的数据：" + range);

        // 裁剪list，即lTrim命令；只保留设置的区间内的元素
        operations.trim("goods_id:1", 1, 2);
        range = operations.range("goods_id:1", 0, operations.size("goods_id:1"));
        System.out.println("裁剪后的数据：" + range);

        // 移除指定的的个数;lRem命苦
        operations.remove("goods_id:1", 2, "新购4个");
        range = operations.range("goods_id:1", 0, operations.size("goods_id:1"));
        System.out.println("移除后的数据：" + range);

        // 设置等待超时时间，如果没有将阻塞等待，在超过指定时间还没有的话就抛出异常
        String val = operations.leftPop("goods_id:1", 60, TimeUnit.SECONDS);
        System.out.println(val);
    }
}
