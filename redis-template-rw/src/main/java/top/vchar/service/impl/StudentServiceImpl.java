package top.vchar.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.vchar.config.RedisHelper;
import top.vchar.entity.Student;
import top.vchar.service.StudentService;

import java.util.List;

/**
 * <p> 学生信息接口实现 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/1/26
 */
@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private RedisTemplate<String, Student> writeRedisTemplate;
    @Autowired
    private RedisTemplate<String, Student> readRedisTemplate;

    private final RedisHelper redisHelper;

    public StudentServiceImpl(RedisHelper redisHelper) {
        this.redisHelper = redisHelper;
    }

    /**
     * 由使用者自己判断应该是读还是写
     */
    @Override
    public Student crud1() {
        // 获取数据
        Student val = this.readRedisTemplate.opsForValue().get("student-rw1:1");
        if (null == val) {
            log.info("缓存中数据不存在");
            val = Student.create();
            // 添加数据
            this.writeRedisTemplate.opsForValue().set("student-rw1:1", val);

            Student student2 = Student.create();
            student2.setId(2L);
            this.writeRedisTemplate.opsForValue().set("student-rw1:2", student2);
            this.writeRedisTemplate.opsForList().leftPush("student_list-rw1", val);
            this.writeRedisTemplate.opsForList().leftPush("student_list-rw1", student2);
        } else {
            // 删除数据
            this.writeRedisTemplate.delete("student-rw1:2");
            log.info("删除缓存");
        }
        log.info(JSON.toJSONString(val));

        // 获取列表数据
        List<Student> list = this.readRedisTemplate.opsForList().range("student_list-rw1", 0, 3);
        log.info(JSON.toJSONString(list));
        return val;
    }

    /**
     * 通过工具类自动切换
     */
    @Override
    public Student crud2() {
        // 获取数据
        Student val = this.redisHelper.get("student-rw2:1", Student.class);
        if (null == val) {
            log.info("缓存中数据不存在");
            val = Student.create();
            // 添加数据
            this.redisHelper.set("student-rw2:1", val);

            Student student2 = Student.create();
            student2.setId(2L);
            this.redisHelper.set("student-rw2:2", student2);

            List<Student> list = Lists.newArrayList(val, student2);
            this.redisHelper.set("student_list-rw2", list);

        } else {
            // 删除数据
            this.redisHelper.del("student-rw2:2");
            log.info("删除缓存");
        }
        log.info(JSON.toJSONString(val));
        // 获取列表数据
        List<Student> list = this.redisHelper.getList("student_list-rw2", Student.class);
        log.info(JSON.toJSONString(list));
        return val;
    }

    /**
     * 测试在读类上是否可以写，写上面是否可以读
     */
    @Override
    public Student readWrite() {
        // 获取数据
        Student val = this.writeRedisTemplate.opsForValue().get("student-rw3:1");
        if (null == val) {
            log.info("缓存中数据不存在");
            val = Student.create();
            // 添加数据
            this.readRedisTemplate.opsForValue().set("student-rw3:1", val);

            Student student2 = Student.create();
            student2.setId(2L);
            this.readRedisTemplate.opsForValue().set("student-rw3:2", student2);
            this.readRedisTemplate.opsForList().leftPush("student_list-rw3", val);
            this.readRedisTemplate.opsForList().leftPush("student_list-rw3", student2);
        } else {
            // 删除数据
            this.readRedisTemplate.delete("student-rw3:2");
            log.info("删除缓存");
        }
        log.info(JSON.toJSONString(val));

        // 获取列表数据
        List<Student> list = this.writeRedisTemplate.opsForList().range("student_list-rw3", 0, 3);
        log.info(JSON.toJSONString(list));
        return val;
    }


}
