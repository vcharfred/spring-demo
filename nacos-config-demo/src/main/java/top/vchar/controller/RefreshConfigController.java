package top.vchar.controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> 刷新配置 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/6/16
 */
@Data
@RestController
@RefreshScope
public class RefreshConfigController {

    @Value("${customer.name}")
    private String name;

    @GetMapping("/config/value")
    public String getConfig() {
        return this.name;
    }

}
