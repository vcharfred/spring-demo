package top.vchar.demo.spring.service;

/**
 * <p> 账号服务 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/11/12
 */
public interface AccountService {

    /**
     * 添加账号
     * @param id ID
     * @param name 名称
     * @return 返回结果
     */
    boolean addAccount(Integer id, String name);

    /**
     * 通过ID查询账号
     * @param id 酒店ID
     * @return 返回酒店名称
     */
    String findAccount(Integer id);
}
