package top.vchar.demo.spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.vchar.demo.spring.bean.Hotel;
import top.vchar.demo.spring.mapper.HotelMapper;
import top.vchar.demo.spring.service.AccountService;
import top.vchar.demo.spring.service.HotelService;
import top.vchar.demo.spring.util.CacheUtil;

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
        if(null!=accountService.findAccount(id)){
            throw new RuntimeException("数据异常");
        }
        accountService.addAccount(id, hotel.getHotelName());
        return id;
    }

    @Override
    public Hotel findById(Integer id) {
        Hotel hotel = (Hotel) CacheUtil.getVal(String.valueOf(id));
        if(null==hotel){
            System.out.println("缓存无数据...");
            hotel = this.hotelMapper.selectById(id);
            if(hotel!=null){
                CacheUtil.setVal(String.valueOf(id), hotel);
            }
        }
        return hotel;
    }


}
