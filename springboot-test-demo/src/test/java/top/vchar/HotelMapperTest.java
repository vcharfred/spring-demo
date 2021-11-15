package top.vchar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import top.vchar.demo.spring.TestDemoApplication;
import top.vchar.demo.spring.bean.Hotel;
import top.vchar.demo.spring.mapper.HotelMapper;

/**
 * <p> 数据库mapper测试 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/11/15
 */
@Rollback
@Transactional(rollbackFor = Exception.class)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestDemoApplication.class)
public class HotelMapperTest {

    @Autowired
    private HotelMapper hotelMapper;

    /**
     * 对于@Sql 注解说明：可以提前执行一些SQL
     */
    @Sql("/hotel.sql")
    @Test
    public void findHotel(){

        Hotel hotel = hotelMapper.selectById(100);
        System.out.println(hotel);
    }

}
