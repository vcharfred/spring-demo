package top.vchar.service;

import top.vchar.entity.Train;

/**
 * <p> hystrix 示例 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/6/21
 */
public interface HystrixDemoService {

    Train hystrixMethodDemo1();

    Train hystrixMethodDemo2();

    Train hystrixMethodDemo3();

    Train hystrixMethodDemo4();
}
