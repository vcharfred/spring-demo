package top.vchar;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import top.vchar.demo.spring.TestDemoApplication;
import top.vchar.demo.spring.bean.Hotel;
import top.vchar.demo.spring.service.AccountService;
import top.vchar.demo.spring.service.HotelService;

import java.math.BigDecimal;

/**
 * <p> 基于spring boot 测试框架进行单元测试（运行速度较慢） </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/11/15
 */
@Rollback
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestDemoApplication.class)
public class HotelServiceTest2 {

    @MockBean
    private AccountService accountService;

    @Autowired
    private HotelService hotelService;

    @Test
    public void joinHotel(){

        Hotel hotel = new Hotel();
        hotel.setHotelName("大酒店");
        hotel.setPrice(new BigDecimal("230"));
        hotel.setRoomNum(130);

        // 设置任意参数均返回固定参数
        BDDMockito.given(this.accountService.findAccount(ArgumentMatchers.anyInt())).willReturn(null);

        Integer id = hotelService.joinHotel(hotel);
        Assert.assertNotNull(id);

        // 接口被调用测试统计
        Mockito.verify(accountService, Mockito.times(1))
                .findAccount(ArgumentMatchers.anyInt());

        // 检查执行方法执行顺序是否正确；inOrder(accountService)的参数可以多个，参数必须为mock出来的对象
        InOrder inOrder = Mockito.inOrder(accountService);
        inOrder.verify(accountService).findAccount(ArgumentMatchers.anyInt());
        inOrder.verify(accountService).addAccount(ArgumentMatchers.anyInt(), ArgumentMatchers.anyString());

    }

}
