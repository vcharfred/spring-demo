package top.vchar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.vchar.entity.Member;

/**
 * <p>  TODO 接口描述 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/6/16
 */
public interface MemberService extends IService<Member> {

    /**
     * 查询
     *
     * @param id 值
     * @return 返回名字
     */
    String findNameById(Integer id);
}
