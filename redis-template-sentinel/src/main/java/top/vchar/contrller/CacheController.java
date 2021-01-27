package top.vchar.contrller;

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
 * @create_date 2021/1/26
 */
@RestController
@RequestMapping("/cache")
public class CacheController {

    private final StudentService studentService;

    public CacheController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * 示例
     */
    @GetMapping("/demo1")
    public Student crud1() {
        return this.studentService.crud();
    }

}
