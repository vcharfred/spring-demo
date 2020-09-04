package top.vchar.service.impl;

import org.springframework.stereotype.Service;
import top.vchar.entity.HotelInfo;
import top.vchar.service.HotelService;

/**
 * <p> 酒店业务接口 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/9/4
 */
@Service
public class HotelServiceImpl implements HotelService {

    /**
     * 通过酒店id查询酒店信息
     * @param id 酒店id
     * @return 返回酒店信息
     */
    @Override
    public HotelInfo findHotelById(Long id) {
        return null;
    }
}
