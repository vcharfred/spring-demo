package top.vchar.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
    private RedisTemplate<String, Student> redisTemplate;

    @Override
    public Student crud() {
        ValueOperations<String, Student> operations = this.redisTemplate.opsForValue();
        // 获取数据
        Student val = operations.get("student4:1");
        if (null == val) {
            log.info("缓存中数据不存在");
            val = Student.create();
            // 添加数据
            operations.set("student4:1", val);

            Student student2 = Student.create();
            student2.setId(2L);
            operations.set("student4:2", student2);
            this.redisTemplate.opsForList().leftPush("student_list4", val);
            this.redisTemplate.opsForList().leftPush("student_list4", student2);
        } else {
            // 删除数据
            this.redisTemplate.delete("student4:2");
            log.info("删除缓存");
        }
        log.info(JSON.toJSONString(val));

        // 获取列表数据
        List<Student> list = this.redisTemplate.opsForList().range("student_list4", 0, 3);
        log.info(JSON.toJSONString(list));
        return val;
    }


}
