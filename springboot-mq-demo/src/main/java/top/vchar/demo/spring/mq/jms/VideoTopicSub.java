package top.vchar.demo.spring.mq.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * <p> 消息订阅发布：订阅者 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/26 22:45
 */
@Component
public class VideoTopicSub {
    @JmsListener(destination = "video.topic", containerFactory="jmsListenerContainerTopic")
    public void video(String text){
        System.out.println("video.topic接收到消息："+text);
    }
}
