package top.vchar.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

/**
 * <p> ApplicationArguments 使用 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/9/3
 */
@Component
public class ApplicationArgumentsDemo {

    public ApplicationArgumentsDemo(ApplicationArguments args){
        // TODO
        boolean debug = args.containsOption("debug");
        System.out.println(debug);
    }

}
