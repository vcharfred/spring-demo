package top.vchar.service;

import top.vchar.entity.Train;

import java.util.concurrent.ExecutionException;

/**
 * <p> 资源隔离示例 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2022/2/16
 */
public interface HystrixSourceDemoService {
    Train findOne1();

    Train findOne2() throws ExecutionException, InterruptedException;
}
