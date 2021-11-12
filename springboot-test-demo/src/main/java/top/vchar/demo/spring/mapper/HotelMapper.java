package top.vchar.demo.spring.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import top.vchar.demo.spring.bean.Hotel;

/**
 * <p> 数据操作 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/10/29
 */
public interface HotelMapper {

    /**
     * 添加酒店信息
     * @param hotel 酒店信息
     * @return 返回成功数
     */
    @Insert("INSERT INTO `demo`.`hotel`(`hotel_name`, `room_num`, `price`) VALUES (#{hotel.hotelName}, #{hotel.roomNum}, #{hotel.price})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int addHotel(@Param("hotel") Hotel hotel);

    /**
     * 通过ID查询酒店信息
     * @param id 酒店ID
     * @return 返回酒店信息
     */
    @Select("SELECT id, hotel_name as hotelName, room_num as roomNum, price FROM hotel WHERE id=#{id}")
    Hotel selectById(@Param("id") Integer id);

    /**
     * 通过名称查询酒店信息
     * @param hotelName 酒店ID
     * @return 返回酒店信息
     */
    @Select("SELECT id, hotel_name as hotelName, room_num as roomNum, price FROM hotel WHERE hotel_name=#{hotelName}")
    Hotel selectByHotelName(@Param("hotelName") String hotelName);

}
