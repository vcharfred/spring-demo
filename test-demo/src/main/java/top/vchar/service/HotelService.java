package top.vchar.service;

import top.vchar.entity.HotelInfo;

/**
 * <p> 酒店业务接口 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/9/4
 */
public interface HotelService {

    /**
     * 通过酒店id查询酒店信息
     * @param id 酒店id
     * @return 返回酒店信息
     */
    HotelInfo findHotelById(Long id);

}
