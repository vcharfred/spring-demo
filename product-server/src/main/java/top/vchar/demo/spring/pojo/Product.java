package top.vchar.demo.spring.pojo;

import java.io.Serializable;

/**
 * <p> 产品实体类 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/8/4 21:54
 */
public class Product implements Serializable {

    private int id;

    private String name;

    private int price;

    private int store;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStore() {
        return store;
    }

    public void setStore(int store) {
        this.store = store;
    }
}
