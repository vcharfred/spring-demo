package top.vchar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.vchar.service.OrderService;

/**
 * <p> feign测试 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/5/17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FeignDemoTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void demo1() {
        orderService.demo1();
    }

    @Test
    public void demo2() {
        orderService.demo2();
    }

    @Test
    public void demo3() {
        orderService.demo3();
    }

    @Test
    public void demo4() {
        orderService.demo4();
    }

}
