package top.vchar.demo.spring.service.impl;

import org.springframework.stereotype.Service;
import top.vchar.demo.spring.service.DemoService;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>  TODO 功能描述 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/16 23:35
 */
@Service
public class DemoServiceImpl implements DemoService {

    @Override
    public List<Integer> demo(int pageIndex) {
        List<Integer> list = new ArrayList<>();
        for(int i=0; i<10; i++){

            System.out.println(System.currentTimeMillis()+" "+pageIndex+"--->>>"+i);
            try {
                Thread.sleep(5000);
                deal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        list.add(pageIndex);
        return list;
    }

    private void deal() throws InterruptedException {
        Thread.sleep(1000);
    }
}
