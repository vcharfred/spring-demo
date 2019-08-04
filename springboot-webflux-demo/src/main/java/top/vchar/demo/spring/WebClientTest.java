package top.vchar.demo.spring;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * <p> webflux客户端Webclient测试 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/8/4 9:48
 */
public class WebClientTest {

    @Test
    public void testBase(){
        Mono<String> result = WebClient.create().get()
                .uri("http://127.0.0.1:8080//demo4?id={id}", 2)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(String.class);
        System.out.println(result.block());
    }

}
