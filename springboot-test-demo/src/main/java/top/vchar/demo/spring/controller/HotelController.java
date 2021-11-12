package top.vchar.demo.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.vchar.demo.spring.bean.Hotel;
import top.vchar.demo.spring.service.HotelService;

/**
 * <p> 酒店路由 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/8 23:16
 */
@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping("/add")
    public Integer addHotel(@RequestBody Hotel hotel){
        return this.hotelService.addHotel(hotel);
    }

    @GetMapping("/detail")
    public Hotel findById(Integer id){
        return this.hotelService.findById(id);
    }


}
