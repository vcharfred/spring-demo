package top.vchar.client;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p> 商品信息 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/11/19
 */
public class GoodsInfo implements Serializable {

    private String name;

    private BigDecimal price;

    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
