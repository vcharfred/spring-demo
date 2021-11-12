package top.vchar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import top.vchar.demo.spring.bean.Hotel;
import top.vchar.demo.spring.mapper.HotelMapper;
import top.vchar.demo.spring.service.AccountService;
import top.vchar.demo.spring.service.HotelService;
import top.vchar.demo.spring.service.impl.HotelServiceImpl;

import java.math.BigDecimal;

/**
 * <p> 业务层逻辑测试 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/11/11
 */
@RunWith(MockitoJUnitRunner.class)
public class HotelServiceTest {

    @Spy
    @InjectMocks
    private HotelService hotelService = new HotelServiceImpl();
    @Mock
    private HotelMapper hotelMapper;
    @Mock
    private AccountService accountService;

    @Before
    public void before(){

//        Mockito.when(hotelMapper.selectByHotelName(Mockito.any())).thenReturn(null);
        Mockito.when(hotelMapper.addHotel(Mockito.any())).thenReturn(1);
    }

    @Test
    public void addHotel(){
        Hotel hotel = new Hotel();
        hotel.setId(5);
        hotel.setHotelName("云山大酒店");
        hotel.setPrice(new BigDecimal("230"));
        hotel.setRoomNum(130);
        hotelService.addHotel(hotel);
    }

}
