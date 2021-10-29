package top.vchar.demo.spring.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import top.vchar.demo.spring.service.DemoService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * <p> 任务 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/16 23:32
 */
@Component
public class DmoTask {

    @Autowired
    private DemoService demoService;

    @Async
    public Future<List<Integer>> pullInfo(int pageIndex, int total){
        List<Integer> list = new ArrayList<>();
        do{
            List<Integer> ids = demoService.demo(pageIndex);
            if(ids.size()>0){
                list.addAll(ids);
            }
            pageIndex++;
        }while (pageIndex<=total);
        return AsyncResult.forValue(list);
    }

}
