package top.vchar.demo.spring.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.vchar.demo.spring.pojo.Member;

import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> 测试 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/8/3 16:04
 */
@RestController
public class IndexController {

    @GetMapping("/demo1")
    public String demo1(){
        return "demo-1";
    }

    //Mono用于返回0或1个元素
    @GetMapping("/demo2")
    public Mono<String> demo2(){
        return Mono.just("demo2");
    }


    private static Map<String, Member> map = new HashMap<>();
    static {
        for(int i=0; i<10; i++){
            map.put(""+i, new Member(i, "demo-"+i));
        }
    }

    //Flux返回0或N个元素
    @GetMapping("/demo3")
    public Flux<Member> demo3(){
      Collection<Member> members = map.values();
      return Flux.fromIterable(members);
    }

    //Mono用于返回0或1个元素
    @GetMapping("/demo4")
    public Mono<Member> demo4(final String id){
        return Mono.justOrEmpty(map.get(id));
    }

    /**
     * 分批次返回
     *
     * 这里每隔2s返回一个对象；webflux是字符串，需要做特殊设置
     */
    @GetMapping(value = "/demo5", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Member> demo5(){
        Collection<Member> members = map.values();
        //每个延迟2秒返回
        return Flux.fromIterable(members).delayElements(Duration.ofSeconds(2));
    }
}
