package top.vchar;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import top.vchar.demo.spring.bean.Hotel;
import top.vchar.demo.spring.mapper.HotelMapper;
import top.vchar.demo.spring.service.HotelService;
import top.vchar.demo.spring.service.impl.HotelServiceImpl;
import top.vchar.demo.spring.util.CacheUtil;

import java.math.BigDecimal;

/**
 * <p> 静态方法测试 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/11/15
 */
@PrepareForTest({CacheUtil.class})// 可以配置多个，如果里面还依赖了其他静态类，也需要这这里配置上
@RunWith(PowerMockRunner.class)
public class StaticMethodTest {

    @Spy
    @InjectMocks
    private final HotelService hotelService = new HotelServiceImpl();
//    @Mock
//    private HotelMapper hotelMapper;

    @Test
    public void findHotel(){
        Hotel hotel = new Hotel();
        hotel.setId(1);
        hotel.setHotelName("大酒店");
        hotel.setPrice(new BigDecimal("230"));
        hotel.setRoomNum(130);

//        Mockito.when(hotelMapper.selectById(1)).thenReturn(hotel);

        // 对CacheUtil的静态方法着mock
        PowerMockito.mockStatic(CacheUtil.class);
        PowerMockito.when(CacheUtil.getVal(Mockito.any())).thenReturn(hotel);

        Hotel val = hotelService.findById(1);
        Assert.assertNotNull(val);
    }
}
