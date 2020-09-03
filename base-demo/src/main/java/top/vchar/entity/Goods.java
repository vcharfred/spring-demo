package top.vchar.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p> 商品信息 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/9/3
 */
public class Goods implements Serializable {

    private Long id;

    private String goodName;

    private BigDecimal price;

    private Integer amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", goodName='" + goodName + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }
}
