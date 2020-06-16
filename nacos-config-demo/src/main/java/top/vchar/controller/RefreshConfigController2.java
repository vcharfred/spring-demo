package top.vchar.controller;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.vchar.config.SystemConfig;

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
@ConfigurationProperties("customer")
public class RefreshConfigController2 {

    private SystemConfig systemConfig;

    @GetMapping("/config/properties")
    public String getConfig() {
        return systemConfig.getName();
    }

}
