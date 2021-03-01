package top.vchar.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import top.vchar.goods.entity.Goods;

/**
 * <p> 商品mapper </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/6/12
 */
@Mapper
@Repository
public interface GoodsMapper extends BaseMapper<Goods> {


    @Update("update goods set inventory=inventory-#{num} where id=#{id} and inventory-#{num}>=0")
    int deductInventory(@Param("id") Long id, @Param("num") int num);
}
