package top.vchar.demo.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.vchar.demo.spring.config.ServerConfig;
import top.vchar.demo.spring.config.ServerConfig2;

/**
 * <p> 注入配置 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/8 22:31
 */
@RestController
@PropertySource({"classpath:resource.properties"})
public class AutoConfigController {

    @Value("${soft.version}")
    private String version;

    @Autowired
    private ServerConfig serverConfig;
    @Autowired
    private ServerConfig2 serverConfig2;

    @GetMapping("/get/soft_version")
    public String getSoftVersion(){
        System.out.println(version);
        System.out.println(serverConfig.toString());
        System.out.println(serverConfig2.toString());
        return version;
    }
}
