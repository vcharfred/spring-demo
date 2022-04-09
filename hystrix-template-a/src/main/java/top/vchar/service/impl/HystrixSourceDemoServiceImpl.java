package top.vchar.service.impl;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixObservableCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;
import rx.schedulers.Schedulers;
import top.vchar.entity.Train;
import top.vchar.feign.TrainFeignClient;
import top.vchar.hystrix.TrainHystrixCommand;
import top.vchar.service.HystrixSourceDemoService;

import java.util.concurrent.ExecutionException;

/**
 * <p> 资源隔离示例 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2022/2/16
 */
@Service
public class HystrixSourceDemoServiceImpl implements HystrixSourceDemoService {

    @Autowired
    private TrainFeignClient trainFeignClient;

    @Override
    public Train findOne1() {

        return new HystrixCommand<Train>(HystrixCommandGroupKey.Factory.asKey("group_name")) {
           @Override
           protected Train run() throws Exception {
               return trainFeignClient.findTrain();
           }
       }.execute();
        //return new TrainHystrixCommand(trainFeignClient).execute();
    }

    public Train findOne1_2() {

        return new HystrixCommand<Train>(HystrixCommand.Setter
                // 设置线程组名称
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("group_name"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        // 设置隔离方式
                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD))
        ) {
            @Override
            protected Train run() throws Exception {
                return trainFeignClient.findTrain();
            }
        }.execute();
        //return new TrainHystrixCommand(trainFeignClient).execute();
    }

    @Override
    public Train findOne2() throws ExecutionException, InterruptedException {
        return new HystrixObservableCommand<Train>(HystrixCommandGroupKey.Factory.asKey("group_batch")) {
            @Override
            protected Observable<Train> construct() {
                return Observable.unsafeCreate((Observable.OnSubscribe<Train>) subscriber -> {
                    try {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(trainFeignClient.findTrain());
                            subscriber.onCompleted();
                        }
                    } catch (Exception e) {
                        subscriber.onError(e);
                    }
                }).subscribeOn(Schedulers.io());
            }
        }.observe().toBlocking().toFuture().get();
    }
}
