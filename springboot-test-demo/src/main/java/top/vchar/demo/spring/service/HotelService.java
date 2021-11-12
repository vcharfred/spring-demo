package top.vchar.demo.spring.service;

import top.vchar.demo.spring.bean.Hotel;

/**
 * <p> 业务逻辑接口 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/10/29
 */
public interface HotelService {

    /**
     * 添加酒店信息
     * @param hotel 酒店信息
     * @return 返回酒店ID
     */
    Integer addHotel(Hotel hotel);

    /**
     * 添加酒店信息
     * @param hotel 酒店信息
     * @return 返回酒店ID
     */
    Integer joinHotel(Hotel hotel);

    /**
     * 通过ID查询酒店信息
     * @param id 酒店ID
     * @return 返回酒店信息
     */
    Hotel findById(Integer id);
}
