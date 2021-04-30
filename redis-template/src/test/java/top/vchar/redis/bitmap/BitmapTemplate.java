package top.vchar.redis.bitmap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import top.vchar.RedisApplication;
import top.vchar.entity.Student;

/**
 * <p> 位图：也就是二进制 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/3/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApplication.class)
public class BitmapTemplate {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 使用bitmap实现用户访问操作日志的统计功能
     */
    @Test
    public void bitmapDemo() {
        UserOperaLog userOperaLog = new UserOperaLog(this.redisTemplate);
        userOperaLog.addLog("/mall/goods/add", 1L);
        userOperaLog.addLog("/mall/goods/add", 2L);
        System.out.println(userOperaLog.hasOpera("/mall/goods/add", 1L));
    }

    @Autowired
    private RedisTemplate<String, Student> redisStuTemplate;
    @Autowired
    private RedisTemplate<String, Long> redisLongTemplate;
    private RedisBloomFilter<Long> bloomFilter;

    @Test
    public void bloomFilterDemo() {
        bloomFilter = new RedisBloomFilter<>("bloom_member_id_table", this.redisLongTemplate, 1000000, 0.01);

        bloomFilter.put(1L);
        Student stu = findById(1L);
        System.out.println(stu);
        stu = findById(100000L);
        System.out.println(stu);
    }

    private Student findById(Long uid) {
        if (bloomFilter.mightContain(uid)) {
            System.out.println("存在");

            Student stu = redisStuTemplate.opsForValue().get("member" + uid);
            System.out.println("查询缓存");

            if (stu == null) {
                System.out.println("查询数据库");
                // 从数据库查询，之后写入缓存
                stu = Student.create();
                if (stu == null) {
                    stu = new Student();
                }
                redisStuTemplate.opsForValue().set("member" + uid, stu);
            }

            return stu;
        }
        return null;
    }


}
