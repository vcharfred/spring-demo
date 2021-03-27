# Eureka 注册中心

### 一、心跳检测

eureka客户端，默认会每隔30秒发送一次心跳的eureka注册中心，可以通过如下2个参数设置心跳间隔时间：

```text
eureka.instance.leaseRenewallIntervalInSeconds
eureka.instance.leaseExpirationDurationInSeconds
```

默认在90秒内没收到一个eureka客户端的心跳，那么就摘除这个服务实例，其他服务就访问不到这个服务实例了。但是一般这俩参数建议不要修改。
另外这个心跳检测的机制其实叫做renew机制，也可以叫做服务续约。
如果一个服务被关闭了，那么会走cancel机制，类似服务下线。
如果90秒内没收到一个client的服务续约，即心跳，但是这里叫做服务续约，那么就会走eviction，将服务实例从注册表里给摘除掉。

### 二、注册表抓取

默认情况下，客户端每隔30秒去服务器抓取最新的注册表，然后缓存在本地，通过下面的参数可以修改。

```text
eureka.client.registryFetchIntervalSeconds
```

### 三、自定义元数据

```yaml
eureka:
    instance:
    hostname: localhost
    metadata-map:
        company-name: cs
```

可以通过上面的metadata-map定义服务的元数据，也就是自定义直接需要的配置信息，不过一般挺少使用。

### 四、自我保护模式

如果在eureka控制台看到如下提示：

```text
EMERGENCY! EUREKA MAY BE INCORRECTLY CLAIMING INSTANCES ARE UP WHEN THEY'RE NOT. RENEWALS ARE LESSER THAN THRESHOLD AND HENCE THE INSTANCES ARE NOT BEING EXPIRED JUST TO BE SAFE.
```

这就是eureka进入了自我保护模式，如果客户端的心跳失败了超过一定的比例，或者在一定时间内（15分钟）接收到的服务续约低于85%，那么就会认为是自己网络故障了，导致其他client无法发送心跳。
这个时候eureka注册中心会先给保护起来，不会立即把失效的服务实例摘除，在测试的时候一般都会关闭这个自我保护模式：

```text
eureka.server.enable-self-preservation: false
```

在生产环境里面，eureka注册中心由于担心网络问题，导致其他服务无法给它发心跳，为了防止由于网络问题导致错误的将服务移除，此时它就进入保护模式，不再移除任何服务实例，等到它网络环境恢复为止。
