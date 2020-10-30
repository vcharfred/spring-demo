## 一、基础概念
- Producer:消息生产者
- Producer Group:消息生产者组，发送同类消息的一个消息生产组
- Consumer:消费者
- Consumer Group:消费同个消息的多个实例
- Tag:标签，子主题（二级分类）,用于区分同一个主题下的不同业务的消息
- Topic:主题
- Message：消息
- Broker：MQ程序，接收生产的消息，提供给消费者消费的程序
- Name Server：给生产和消费者提供路由信息，提供轻量级的服务发现和路由

关于其服务端的构建：

- 通过数据存储服务broker，这玩意只做数据存储处理相关，不接受对外收发请求，只和name server交互，为保证数据的可靠和稳定性，提供主从策略，多节点备份，当主节点挂了从节点继续提供服务；
- 服务发现和路由器name server，这个相当于微服务中的注册中心，它负责进行路由的分发，以及broker集群的管理，同时为了提供高可用，name server集群其实不叫集群，它们互不影响，任意一个挂了对整个集群依然正常工作。

![](https://upload-images.jianshu.io/upload_images/8021450-255054d171cb2156.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 相关文档资料

- 官网：[http://rocketmq.apache.org/](http://rocketmq.apache.org/)
- 顺序消息博客：[https://blog.csdn.net/hosaos/article/details/90675978](https://blog.csdn.net/hosaos/article/details/90675978)

## 二、简单使用示例
### 2.1 使用基础的 rocketmq-client包来实现


这个包中包含了封装的RocketMQ相关的TCP连接操作。


#### 2.1.1 添加maven依赖和配置
```xml
 <dependency>
   <groupId>org.apache.rocketmq</groupId>
   <artifactId>rocketmq-client</artifactId>
   <version>4.7.1</version>
 </dependency>
```
在application配置文件添加如下配置
```yaml
rocketmq:
  name-server: 192.168.111.63:9876
  producer:
    group: client-server
```


创建DefaultMQProducer构建工厂类
```java
@Slf4j
@Component
public class RocketProducerBuilder implements DisposableBean {

    /**
     * NameServer 地址
     */
    @Value(value = "${rocketmq.name-server}")
    private String nameServerAddr;

    /**
     * 生产者的组名
     */
    @Value(value = "${rocketmq.producer.group}")
    private String producerGroup;

    private DefaultMQProducer producer;

    /**
     * 初始化DefaultMQProducer
     *
     * 参考rocketmq-spring-boot包中的org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration类
     *
     * @throws MQClientException 启动消息生产者异常
     */
    @PostConstruct
    void init() throws MQClientException {
        //生产者的组名
        producer = new DefaultMQProducer(producerGroup);
        /// 指定NameServer地址，多个地址以 ; 隔开
        producer.setNamesrvAddr(nameServerAddr);
        // 关闭Channel通道
        producer.setVipChannelEnabled(false);
        // 发送消息超时时间，单位毫秒
        producer.setSendMsgTimeout(3000);
        // 在同步模式下，消息发送失败后重试次数，注意这个可能导致重复消息
        producer.setRetryTimesWhenSendFailed(2);
        // 在异步模式下，消息发送失败后重试次数，注意这个可能导致重复消息
        producer.setRetryTimesWhenSendAsyncFailed(2);
        // 发送消息的消息体网络包最大值
        producer.setMaxMessageSize(1024 * 1024 * 4);
        // 当消息体网络包大于4k时压缩消息
        producer.setCompressMsgBodyOverHowmuch(1024 * 4);
        // 当向一个broker发送消息失败了，是否重新尝试下一个
        producer.setRetryAnotherBrokerWhenNotStoreOK(false);
        // Producer对象在使用之前必须要调用start初始化，只能初始化一次
        producer.start();
    }

    /**
     * 获取DefaultMQProducer
     * @return  返回消息生产者DefaultMQProducer
     */
    public DefaultMQProducer build(){
        return this.producer;
    }

    @Override
    public void destroy() {
        if(null!=producer){
            producer.shutdown();
            log.info("Rocket Producer Destroyed");
        }
    }
}
```
#### 2.1.2 发送普通消息
普通消息分为：同步（Sync）发送、异步（Async）发送和单向（Oneway）发送。

- 发送同步消息示例；注意同步消息会阻塞等待消息发送结果，适用场景：重要通知邮件、报名短信通知、营销短信系统等。
```java
@Autowired
private RocketProducerBuilder producerBuilder;

/**
* 同步消息
*/
@PostMapping("/general")
public Mono<SendResult> sendMessage(@RequestBody OrderDTO orderDTO){

  DefaultMQProducer producer = producerBuilder.build();
  Message message = new Message();
  message.setTopic("demo-pay");
  message.setTags("train");
  message.setKeys(UUID.randomUUID().toString());
  message.setBody(JSONObject.toJSONString(orderDTO).getBytes(StandardCharsets.UTF_8));
  return Mono.defer(()-> {
    try {
    	// 注意这个方法在发送失败了会重试，消费者需要做好处理
      return Mono.just(producer.send(message, 3000));
    } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
      e.printStackTrace();
      return Mono.error(e);
    }
  });
}
```
> 普通消息应该是最常用的消息，需要注意的是DefaultMQProducer的send方法有重试机制，具体查看org.apache.rocketmq.client.impl.producer.DefaultMQProducerImpl类中的sendDefaultImpl()方法；
> 因此消费者需要处理注意接口的冥等性。



- 发送异步消息；异步发送是指发送方发出一条消息后，不等服务端返回响应，接着发送下一条消息的通讯方式。适用场景：异步发送一般用于链路耗时较长，对响应时间较为敏感的业务场景。
```java
/**
* 异步消息
*/
@PostMapping("/async")
public String asyncSend(@Validated @RequestBody OrderDTO orderDTO) {

  DefaultMQProducer producer = producerBuilder.build();
  Message message = new Message();
  message.setTopic("demo-pay");
  message.setTags("train");
  message.setKeys(UUID.randomUUID().toString());
  message.setBody(JSONObject.toJSONString(orderDTO).getBytes(StandardCharsets.UTF_8));
  try {
  	producer.send(message, new SendCallback() {
      @Override
      public void onSuccess(SendResult sendResult) {
      	log.info("异步消息发送结果：{}", sendResult);
      }
      @Override
      public void onException(Throwable e) {
      	log.error("异步消息发送异常：", e);
      }
    }, 3000);
  } catch (Exception e) {
  	log.error("异步消息发送异常：", e);
  }
  return "发送成功";
}
```

- 单向发送；发送方只负责发送消息，不等待服务端返回响应且没有回调函数触发，即只发送请求不等待应答。此方式发送消息的过程耗时非常短，一般在微秒级别。适用场景：适用于某些耗时非常短，但对可靠性要求并不高的场景，例如日志收集。
```java
/**
* 单向消息
*/
@PostMapping("/oneway")
public String sendOneway(@Validated @RequestBody OrderDTO orderDTO) {

  DefaultMQProducer producer = producerBuilder.build();
  Message message = new Message();
  message.setTopic("demo-pay");
  message.setTags("train");
  message.setKeys(UUID.randomUUID().toString());
  message.setBody(JSONObject.toJSONString(orderDTO).getBytes(StandardCharsets.UTF_8));
  try {
  	producer.sendOneway(message);
  } catch (Exception e) {
  	log.error("单向消息发送异常：", e);
  }
  return "发送成功";
}
```
#### 2.1.3 发送延时消息
延时消息用于指定消息发送后，延时一段时间才被投递到客户端进行消费（例如 3 秒后才被消费），适用于解决一些消息生产和消费有时间窗口要求的场景，或者通过消息触发延迟任务的场景，类似于延迟队列。

注意：开源版本的仅支持18个等级的延迟消息，阿里云官方的商业版支持任意时间的延时消息；
> 延时等级(delayLevel)对应的时间: 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h

```java
/**
* 延时消息
*/
@PostMapping("/delay")
public SendResult delayMessage(@Validated @RequestBody OrderDTO orderDTO){

	Message message = new Message();
	message.setTopic("demo-pay");
	message.setTags("train");
	message.setKeys(UUID.randomUUID().toString());
	message.setBody(JSONObject.toJSONString(orderDTO).getBytes(StandardCharsets.UTF_8));
	// 延时等级(delayLevel)对应的时间 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h，从1开始
	message.setDelayTimeLevel(3);
	try {
		return producerBuilder.build().send(message);
	} catch (Exception e) {
		e.printStackTrace();
		log.error("延时消息发送异常：", e);
	}
	return null;
}
```
#### 2.1.4 发送顺序消息
顺序消息（FIFO 消息）是消息队列 RocketMQ 版提供的一种严格按照顺序来发布和消费的消息类型。
其分为下面2类：

- 全局顺序：对于指定的一个 Topic，所有消息按照严格的先入先出 FIFO（First In First Out）的顺序进行发布和消费。
- 局部顺序：对于指定的一个 Topic，所有消息根据 Sharding Key 进行区块分区。同一个分区内的消息按照严格的 FIFO 顺序进行发布和消费。Sharding Key 是顺序消息中用来区分不同分区的关键字段，和普通消息的 Key 是完全不同的概念。

示例：用户A、B都下了订单，需要以顺序发送3条消息，


    A1 A2 A3 B1 B2 B3  全局顺序，但是系统性能很受影响。
    A1 B1 A2 A3 B2 B3  局部顺序，只需要保证A或B的消息顺序即可，中间可以穿插其他的消息
    A2 B2 A1 A3 B1 B3  这样的就不符合要求


为了实现消息的顺序消费，我们需要最生产者和消息者做特殊些要求；对应全局顺序，必须设置1个Topic下读写队列都为1，同时业务端还需要对消息的重试机制进行处理，性能自然也就差了，因此不建议使用。


对于局部顺序，需要保证消息的发送顺序、消息的存储顺序、消息的消费顺序；

- 消息的发送：多线程中需要保证同一个业务编号的消息在一个线程中完成，同时使用同步的消息；
- 消息的存储：mq的topic下会存在多个queue，要保证消息的顺序存储，同一个业务编号的消息需要被发送到一个queue中。对应到mq中，需要使用MessageQueueSelector来选择要发送的queue，即对业务编号进行hash，然后根据队列数量对hash值取余，将消息发送到一个queue中。
- 消息的消费：要保证消息顺序消费，同一个queue就只能被一个消费者所消费，因此对broker中消费队列加锁是无法避免的。同一时刻，一个消费队列只能被一个消费者消费，消费者内部，也只能有一个消费线程来消费该队列。即，同一时刻，一个消费队列只能被一个消费者中的一个线程消费。


全局顺序和局部顺序的代码实现几乎是一样。

```java
/**
* 顺序消息
*/
@PostMapping
public String sendMessage(@Validated @RequestBody OrderNotify orderNotify){

    Message message = new Message();
    message.setTopic("order-notify");
    message.setTags("train");
    message.setKeys(UUID.randomUUID().toString());
    message.setBody(JSONObject.toJSONString(orderNotify).getBytes(StandardCharsets.UTF_8));
    try {
        producerBuilder.build().send(message, (mqs, msg, arg) -> {
            // 这里就是进行队列的选择，这里的arg参数就是后面传入的那个参数
            int index = Math.abs(arg.hashCode()%mqs.size());
            return mqs.get(index);
        }, orderNotify.getOrderNo());
    } catch (Exception e) {
        log.error("顺序消息发送异常", e);
        return "顺序消息发送异常："+e.getMessage();
    }
    return "消息发送完成";
}
```
消费者实现，和这里需要选择有序的监听类实现，同时需要从队列开始处开始消费。（其他的是一样的，具体的见消费者那块的代码）

```java
@Override
public void init() {
    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(getConsumerGroup());
    consumer.setNamesrvAddr(getNameServer());
    try {
        // 设置consumer所订阅的Topic和Tag, *代表所有的Tag
        consumer.subscribe(this.topics, this.tags);
        // CONSUME_FROM_FIRST_OFFSET, 从队列最开始开始消费，即历史消息（还存在broker的）全部消费一遍
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        // 设置线程数，默认是20；这里先将其设置小点
        consumer.setConsumeThreadMin(3);
        consumer.setConsumeThreadMax(6);

        // MessageListenerOrderly 有序的
        consumer.registerMessageListener((MessageListenerOrderly) (list, context) -> {
            try{
                // 其实这里默认每次只会传入一条消息
                log.warn("本次消息数：{}", list.size());
                for(MessageExt messageExt:list){
                    //打印消息内容
                    log.info("messageExt: [{}]: {}", getNumber(), messageExt);
                    String messageBody = new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET);
                    ConsumerResult consumerResult = rocketConsumerHandler.handler(ConsumerMessage.builder()
                                                                                  .number(getNumber())
                                                                                  .message(messageBody)
                                                                                  .build());
                    if(!consumerResult.isSuccess() && consumerResult.isRetry()){
                        return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                    }
                }
            }catch (Exception e){
                log.error("顺序消息消费异常：", e);
                return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
            }
            return ConsumeOrderlyStatus.SUCCESS;
        });
        super.setMqPushConsumer(consumer);
        consumer.start();
        log.info("start rocketmq consumer success");
    }catch (Exception e){
        throw new BizRunTimeException("注册rocketmq消费者异常", e);
    }
}
```


#### 2.1.5 订阅消息发布

Rocket的消息订阅方式分为集群模式（默认的）和广播模式，这个只需要在消费端进行设置即可；广播模式下，同一个 Group ID 所标识的所有 Consumer 都会各自消费某条消息一次。

> 额外说明：同一个Group ID 所标识的消费者订阅的设置需要保持一致，即消费者分组A中所有消费者的topic和tag必须设置为一样的。

![](https://upload-images.jianshu.io/upload_images/8021450-d0101f82baf3f2a7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

下面这个就是不正确的，如果这样设置，那么消息消费的逻辑就会混乱，甚至导致消息丢失
![](https://upload-images.jianshu.io/upload_images/8021450-f083fd055b728d70.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


就是在创建消费者时增加下面这行代码即可

    consumer.setMessageModel(MessageModel.BROADCASTING);

#### 2.1.6 事务消息


#### 2.1.7 消费者

下面使用基于springboot的简单实现；application配置文件如下：
```yaml
rocketmq:
  name-server: 192.168.111.63:9876
  producer:
    group: client-server
  consumer:
    - consumerGroup: trian-order
      consumeFromWhere: CONSUME_FROM_LAST_OFFSET
      topics: demo-pay
      rocketConsumerHandler: "top.vchar.rocketmq.config.rocketmq.handler.SimpleRocketConsumerHandler"
```

消费者接口定义

```java
public interface RocketConsumer {

    /**
     * 初始化
     */
    void init();

}
```
```java
/**
 * <p> rocketmq 消费者基础信息 </p>
 *
 * @version 1.0
 * @create_date 2020/10/27
 */
@Slf4j
public abstract class AbstractRocketConsumer implements RocketConsumer, DisposableBean {

    protected MQPushConsumer consumer;

    @Getter
    private final String nameServer;

    @Getter
    private final String consumerGroup;

    @Getter
    private final ConsumeFromWhere consumeFromWhere;

    public AbstractRocketConsumer(String nameServer, String consumerGroup, ConsumeFromWhere consumeFromWhere){
        this.nameServer = nameServer;
        this.consumerGroup = consumerGroup;
        this.consumeFromWhere = Optional.ofNullable(consumeFromWhere).orElse(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
    }

    /**
     * 初始化
     */
    @Override
    public abstract void init();

    public void setMqPushConsumer(MQPushConsumer consumer){
        this.consumer = consumer;
    }

    /**
     * 销毁
     */
    @Override
    public void destroy() throws Exception {
        if (Objects.nonNull(consumer)) {
            consumer.shutdown();
        }
        log.info("container destroyed, {}", this.toString());
    }
}
```
```java
/**
 * <p> 普通消息，延时消息消费者 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/29
 */
@Slf4j
public class SimpleRocketConsumer extends AbstractRocketConsumer {

    private final String topics;

    private final String tags;

    private final RocketConsumerHandler rocketConsumerHandler;

    /**
     * 创建简单的消费者
     * @param nameServer name server
     * @param consumerGroup 消费者组
     * @param consumeFromWhere 消费策略
     *                         CONSUME_FROM_LAST_OFFSET 默认策略。从该队列最尾开始消费，跳过历史消息
     *                         CONSUME_FROM_FIRST_OFFSET, 从队列最开始开始消费，即历史消息（还存在broker的）全部消费一遍
     *                         CONSUME_FROM_TIMESTAMP; 根据时间消费
     * @param topics 主题
     * @param tags 标签，默认为*
     * @param rocketConsumerHandler  消息接收业务处理器
     */
    public SimpleRocketConsumer(String nameServer, String consumerGroup, ConsumeFromWhere consumeFromWhere, String topics, String tags, RocketConsumerHandler rocketConsumerHandler){
        super(nameServer, consumerGroup, consumeFromWhere);
        Assert.notNull(nameServer, "RocketMQ name server can't null");
        Assert.notNull(consumerGroup, "RocketMQ consumer group can't null");
        Assert.notNull(topics, "RocketMQ topics can't null");
        Assert.notNull(rocketConsumerHandler, "RocketMQ SimpleRocketConsumerHandler can't null");

        this.topics = topics;
        this.tags = Optional.ofNullable(tags).orElse("*");
        this.rocketConsumerHandler = rocketConsumerHandler;
    }

    @Override
    public void init() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(getConsumerGroup());
        consumer.setNamesrvAddr(getNameServer());
        try {
            //设置consumer所订阅的Topic和Tag, *代表所有的Tag
            consumer.subscribe(this.topics, this.tags);

            // CONSUME_FROM_LAST_OFFSET 默认策略。从该队列最尾开始消费，跳过历史消息
            // CONSUME_FROM_FIRST_OFFSET, 从队列最开始开始消费，即历史消息（还存在broker的）全部消费一遍
            // CONSUME_FROM_TIMESTAMP;//根据时间消费
            consumer.setConsumeFromWhere(getConsumeFromWhere());

            // MessageListenerOrderly 有序的，
            // 注意有序的在返回消费失败后，其会马上就将消息再次发过来，并且其消费次数不变，
            //    也就是其会永远的重试（因此建议不要把异常抛出，程序里面手动处理下）
            // MessageListenerConcurrently无序的，效率更高
            consumer.registerMessageListener((MessageListenerConcurrently)(list, context)->{
                try{
                    for(MessageExt messageExt:list){
                        //打印消息内容
                        log.info("messageExt: {}", messageExt);
                        String messageBody = new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET);
                        ConsumerResult consumerResult = rocketConsumerHandler.handler(messageBody);
                        if(!consumerResult.isSuccess() && consumerResult.isRetry()){
                            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                        }
                    }
                }catch (Exception e){
                    log.error("消息消费异常", e);
                    // 消费失败，稍后mq会再次将消息发过来，注意mq默认最大重试次数为16，可以修改。
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                // 消费成功
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });
            consumer.start();
            super.setMqPushConsumer(consumer);
            log.info("start rocketmq consumer success");
        }catch (Exception e){
            throw new BizRunTimeException("注册rocketmq消费者异常", e);
        }
    }

    public static SimpleRocketConsumerBuilder builder(){
        return new SimpleRocketConsumerBuilder();
    }

    static class SimpleRocketConsumerBuilder {

        private String nameServer;

        private String consumerGroup;

        private ConsumeFromWhere consumeFromWhere;

        private String topics;

        private String tags;

        private RocketConsumerHandler rocketConsumerHandler;

        public SimpleRocketConsumerBuilder(){

        }

        public SimpleRocketConsumer build(){
            return new SimpleRocketConsumer(this.nameServer, this.consumerGroup, this.consumeFromWhere, this.topics, this.tags, this.rocketConsumerHandler);
        }

        public SimpleRocketConsumerBuilder nameServer(String nameServer){
            this.nameServer = nameServer;
            return this;
        }

        public SimpleRocketConsumerBuilder consumerGroup(String consumerGroup){
            this.consumerGroup = consumerGroup;
            return this;
        }

        public SimpleRocketConsumerBuilder consumeFromWhere(ConsumeFromWhere consumeFromWhere){
            this.consumeFromWhere = consumeFromWhere;
            return this;
        }

        public SimpleRocketConsumerBuilder topics(String topics){
            this.topics = topics;
            return this;
        }

        public SimpleRocketConsumerBuilder tags(String tags){
            this.tags = tags;
            return this;
        }

        public SimpleRocketConsumerBuilder rocketConsumerHandler(RocketConsumerHandler rocketConsumerHandler){
            this.rocketConsumerHandler = rocketConsumerHandler;
            return this;
        }

    }

}
```
业务处理handler
```java
public interface RocketConsumerHandler {

    /**
     * 消息处理
     * @param message 消息内容
     * @return 返回处理结果
     */
    ConsumerResult handler(String message);

}
```
```java
/**
 * <p> 消息业务handler实现 </p>
 *
 * @version 1.0
 * @create_date 2020/10/29
 */
@Slf4j
@Component
public class SimpleRocketConsumerHandler implements RocketConsumerHandler {

    @Override
    public ConsumerResult handler(String message) {
        log.info("消费消息: {}", message);
        // TODO 这里是业务处理，ConsumerResult类就是个简单的处理结果类
        return new ConsumerResult(true);
    }
}
```
RocketMQ配置类

```java
@Data
@Component
@ConfigurationProperties(prefix = "rocketmq")
public class RocketMQProperties implements Serializable {

    /**
     * The name server for rocketMQ, formats: `host:port;host:port`.
     */
    private String nameServer;

    private Producer producer;

    private List<Consumer> consumer;

    @Data
    public static final class Producer {
        private String group;
    }

    @Data
    public static final class Consumer {
        private String consumerGroup;

        private ConsumeFromWhere consumeFromWhere;

        private String topics;

        private String tags;

        private String rocketConsumerHandler;
    }
}
```

注册消费者

```java
/**
 * <p> 消费者注册 </p>
 *
 * spring容器将所有的bean加载完毕后会执行run方法
 *
 * @version 1.0
 * @create_date 2020/10/29
 */
@Slf4j
@Component
public class RocketMQConsumerRegister implements CommandLineRunner {

    private final RocketMQProperties properties;

    public RocketMQConsumerRegister(RocketMQProperties properties) {
        this.properties = properties;
    }

    @Override
    public void run(String... args) throws Exception {
        List<RocketMQProperties.Consumer> consumers = properties.getConsumer();
        if(consumers!=null && !consumers.isEmpty()){
            for(RocketMQProperties.Consumer consumer:consumers){
                SimpleRocketConsumer.builder()
                        .nameServer(this.properties.getNameServer())
                        .consumerGroup(consumer.getConsumerGroup())
                        .consumeFromWhere(consumer.getConsumeFromWhere())
                        .topics(consumer.getTopics())
                        .tags(consumer.getTags())
                        .rocketConsumerHandler(getHandler(consumer.getRocketConsumerHandler()))
                        .build().init();
            }
        }
    }

    private RocketConsumerHandler getHandler(String handlerClass){
        try {
            // 由于这些业务处理handler可能依赖些基础组件，比如数据库等，因此这里从spring容器中获取bean
            return (RocketConsumerHandler) SpringBeanUtil.getBean(Class.forName(handlerClass));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
```
```java
/**
 * <p> spring bean 工具类 </p>
 *
 * @version 1.0
 * @create_date 2020/10/29
 */
@Component
public class SpringBeanUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (null == SpringBeanUtil.applicationContext) {
            SpringBeanUtil.applicationContext = applicationContext;
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过Bean名字获取Bean
     *
     * @param beanName bean 名称
     * @return 返回获取到的对象
     */
    public static Object getBean(String beanName) {
        return getApplicationContext().getBean(beanName);
    }

    /**
     * 通过Bean类型获取Bean
     *
     * @param beanClass bean class
     * @param <T>       beanClass
     * @return 返回对象
     */
    public static <T> T getBean(Class<T> beanClass) {
        return getApplicationContext().getBean(beanClass);
    }

    /**
     * 通过Bean名字和Bean类型获取Bean
     *
     * @param beanName  bean 名称
     * @param beanClass class
     * @param <T>       beanClass
     * @return 返回对象
     */
    public static <T> T getBean(String beanName, Class<T> beanClass) {
        return getApplicationContext().getBean(beanName, beanClass);
    }

}
```
