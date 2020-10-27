package top.vchar.train;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 * <p> mq配置测试 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/27
 */
public class RocketMqConfigTest {

    @Test
    public void producerTest() throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {

        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("pay-msg");
        // Specify name server addresses.
        producer.setNamesrvAddr("192.168.111.63:9876");
        //Launch the instance.
        producer.start();
        Message msg = new Message("pay_server" /* Topic */,
                "TagA" /* Tag */,
                ("Hello RocketMQ 1" ).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
        );
        //Call send message to deliver message to one of brokers.
        SendResult sendResult = producer.send(msg);
        System.out.printf("%s%n", sendResult);

        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}
