package top.vchar.demo.spring.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * <p> 定时任务 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/16 23:31
 */
@Component
public class TestScheduling {

    @Autowired
    private DmoTask dmoTask;


    boolean run = true;
    @Scheduled(fixedDelay = 2000)
    public void demo() throws ExecutionException, InterruptedException {
        while (run){
            run = false;

            int index = 1;
            int total = 2;
            int page = 20;

            long time = System.currentTimeMillis();
            List<Future<List<Integer>>> list = new ArrayList<>();
            int num = 0;
            do{
                Future<List<Integer>> future = dmoTask.pullInfo(index, total);
                index = total+1;
                total = total+1;
                num++;
                list.add(future);
            }while (index<page);

            List<Integer> ids = new ArrayList<>();
            while (num>0){
                for(Future<List<Integer>> future:list){
                    if(future.isDone()){
                        num--;
                        List<Integer> list1 = future.get();
                        if(list1!=null)
                            ids.addAll(list1);
                    }
                }
            }

            System.out.println("运行时长："+(System.currentTimeMillis()-time)+" ms");
            System.out.println(Arrays.toString(ids.toArray()));
        }
    }

}
