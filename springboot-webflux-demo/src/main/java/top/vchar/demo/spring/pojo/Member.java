package top.vchar.demo.spring.pojo;

import java.io.Serializable;

/**
 * <p> 用户信息 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/8/4 8:55
 */
public class Member implements Serializable {

    private int id;
    private String name;

    public Member(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
