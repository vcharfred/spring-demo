package top.vchar.security.bean;

import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;
import top.vchar.security.filter.AdditionalHeaderWebFilter;

import java.io.Serializable;

/**
 * <p> 请求信息 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/15
 */
@Data
public class AccessInfo implements Serializable {

    /**
     * 客户端请求IP
     */
    private String ip;

    /**
     * 请求ID
     */
    private String traceId;

    /**
     * 请求方式
     */
    private String method;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 说明信息
     */
    private String message;

    public AccessInfo(String ip, String url, String traceId, String method, String message) {
        this.ip = ip;
        this.url = url;
        this.method = method;
        this.traceId = traceId;
        this.message = message;
    }

    @Override
    public String toString() {
        return "traceId="+this.traceId+", [IP:"+this.ip+"], ["+this.method+":"+this.url+"], 异常信息: "+this.message;
    }

    public static AccessInfoBuilder builder(ServerWebExchange exchange){
        return new AccessInfoBuilder(exchange);
    }

    public static class AccessInfoBuilder {

        private final String ip;

        private final String traceId;

        private final String method;

        private final String url;

        private String message;

        public AccessInfoBuilder message(String message){
            this.message = message;
            return this;
        }

        public AccessInfoBuilder(ServerWebExchange exchange){
            this.method = exchange.getRequest().getMethodValue();
            this.url = exchange.getRequest().getURI().getPath();

            HttpHeaders headers = exchange.getRequest().getHeaders();
            this.traceId = headers.getFirst(AdditionalHeaderWebFilter.REQUEST_TRACE_ID);
            this.ip = headers.getFirst(AdditionalHeaderWebFilter.REQUEST_IP);
        }

        public AccessInfo build(){
            return new AccessInfo(this.ip, this.url, this.traceId, this.method, this.message);
        }

    }

}
