package top.vchar.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p> 学生信息 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/1/25
 */
@Data
public class Student implements Serializable {

    private Long id;

    private String name;

    private int age;

    private String address;

    private LocalDateTime createTime;

    private LocalDate birth;

    private Date date;

    public static Student create() {
        Student student = new Student();
        student.setId(1L);
        student.setName("小瑞");
        student.setAge(26);
        student.setAddress("太阳系地球村");
        student.setCreateTime(LocalDateTime.now());
        student.setBirth(LocalDate.of(1995, 10, 16));
        student.setDate(new Date());
        return student;
    }
}
