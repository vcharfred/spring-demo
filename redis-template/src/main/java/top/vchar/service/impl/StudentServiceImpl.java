package top.vchar.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import top.vchar.entity.Student;
import top.vchar.service.StudentService;

import java.util.List;

/**
 * <p> 学生信息接口实现 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/1/25
 */
@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<String, Student> redisTemplate;


    /**
     * 增删查改示例1
     */
    @Override
    public String crud1() {
        ValueOperations<String, String> operations = this.stringRedisTemplate.opsForValue();
        // 获取数据
        String val = operations.get("student1:1");
        if (null == val) {
            log.info("缓存中数据不存在");
            Student student = Student.create();
            val = JSON.toJSONString(student);
            // 添加数据
            operations.set("student1:1", val);

            Student student2 = Student.create();
            student2.setId(2L);
            operations.set("student1:2", JSON.toJSONString(student2));
            this.stringRedisTemplate.opsForList().leftPush("student_list1", JSON.toJSONString(student));
            this.stringRedisTemplate.opsForList().leftPush("student_list1", JSON.toJSONString(student2));

        } else {
            // 删除数据
            this.stringRedisTemplate.delete("student1:2");
            log.info("删除缓存");
        }
        log.info(val);

        // 获取列表数据
        List<String> list = this.stringRedisTemplate.opsForList().range("student_list1", 0, 3);
        log.info(JSON.toJSONString(list));
        return val;
    }

    @Override
    public Student crud2() {
        ValueOperations<String, Student> operations = this.redisTemplate.opsForValue();
        // 获取数据
        Student val = operations.get("student2:1");
        if (null == val) {
            log.info("缓存中数据不存在");
            val = Student.create();
            // 添加数据
            operations.set("student2:1", val);

            Student student2 = Student.create();
            student2.setId(2L);
            operations.set("student2:2", student2);
            this.redisTemplate.opsForList().leftPush("student_list2", val);
            this.redisTemplate.opsForList().leftPush("student_list2", student2);
        } else {
            // 删除数据
            this.redisTemplate.delete("student2:2");
            log.info("删除缓存");
        }
        log.info(JSON.toJSONString(val));

        // 获取列表数据
        List<Student> list = this.redisTemplate.opsForList().range("student_list2", 0, 3);
        log.info(JSON.toJSONString(list));
        return val;
    }


}
