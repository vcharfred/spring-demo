package top.vchar.demo.spring.mq.service;

import javax.jms.Destination;

/**
 * <p> 消息生产者 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/25 21:02
 */
public interface ProducerService {

    /**
     * 指定消息队列和消息
     * @param destination
     * @param message
     */
    void sendMessage(Destination destination, final String message);

    /**
     * 使用默认消息队列发送消息
     * @param message
     */
    void sendMessage(final String message);

    //发布消息
    void publish(String msg);
}
