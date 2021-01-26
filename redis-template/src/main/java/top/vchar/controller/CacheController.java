package top.vchar.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.vchar.entity.Student;
import top.vchar.service.StudentService;

/**
 * <p> 缓存测试 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/1/25
 */
@RestController
@RequestMapping("/cache")
public class CacheController {

    private final StudentService studentService;

    public CacheController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * 示例1
     */
    @GetMapping("/demo1")
    public String crud1() {
        return this.studentService.crud1();
    }

    /**
     * 示例2
     */
    @GetMapping("/demo2")
    public Student crud2() {
        return this.studentService.crud2();
    }
}
