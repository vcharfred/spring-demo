package top.vchar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import top.vchar.demo.spring.TestDemoApplication;
import top.vchar.demo.spring.bean.Hotel;
import top.vchar.demo.spring.controller.HotelController;
import top.vchar.demo.spring.service.HotelService;

import java.math.BigDecimal;

/**
 * <p> 酒店路由测试 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/11/12
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestDemoApplication.class)
@AutoConfigureMockMvc
public class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HotelService hotelService;

    @Test
    public void findHotel() throws Exception {

        Hotel hotel = new Hotel();
        hotel.setId(1);
        hotel.setHotelName("世外桃源酒店");
        hotel.setRoomNum(20);
        hotel.setPrice(new BigDecimal("120.90"));

        BDDMockito.given(this.hotelService.findById(ArgumentMatchers.anyInt())).willReturn(hotel);

        // 这里是模拟发起一个http请求
        mockMvc.perform(MockMvcRequestBuilders.get("/hotel/detail?id=1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON))
                // 对结果断言
                .andExpect(MockMvcResultMatchers.jsonPath("$.roomNum").value(20))
                // 打印请求内容
                .andDo(MockMvcResultHandlers.print());
    }

}
