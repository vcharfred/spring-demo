package top.vchar.hystrix;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import top.vchar.entity.Train;
import top.vchar.feign.TrainFeignClient;

/**
 * <p>  线程池资源隔离示例2：返回多条数据 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2022/1/4
 */
public class TrainHystrixObservableCommand extends HystrixObservableCommand<Train> {

    private final String name;

    @Autowired
    private TrainFeignClient trainFeignClient;

    public TrainHystrixObservableCommand() {
        this("TRAIN_HYSTRIX_OBSERVABLE_COMMAND");
    }

    public TrainHystrixObservableCommand(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("group_batch"));
        this.name = name;
    }
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
}
