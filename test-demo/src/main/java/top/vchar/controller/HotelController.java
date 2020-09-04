package top.vchar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.vchar.entity.HotelInfo;
import top.vchar.service.HotelService;

/**
 * <p> 酒店信息 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/9/4
 */
@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping
    public HotelInfo findHotel(Long id){
        return hotelService.findHotelById(id);
    }



}
