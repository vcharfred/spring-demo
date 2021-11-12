package top.vchar.demo.spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.vchar.demo.spring.bean.Hotel;
import top.vchar.demo.spring.mapper.HotelMapper;
import top.vchar.demo.spring.service.AccountService;
import top.vchar.demo.spring.service.HotelService;

/**
 * <p> 业务逻辑接口实现 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/10/29
 */
@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelMapper hotelMapper;

    @Autowired
    private AccountService accountService;

    @Override
    public Integer addHotel(Hotel hotel) {
        Hotel oldHotel = this.hotelMapper.selectByHotelName(hotel.getHotelName());
        if(null!=oldHotel){
            throw new RuntimeException("酒店名称已存在");
        }
        hotelMapper.addHotel(hotel);
        return hotel.getId();
    }

    @Override
    public Integer joinHotel(Hotel hotel) {
        Integer id = addHotel(hotel);
        accountService.addAccount(id, hotel.getHotelName());
        return id;
    }

    @Override
    public Hotel findById(Integer id) {
        return this.hotelMapper.selectById(id);
    }


}
