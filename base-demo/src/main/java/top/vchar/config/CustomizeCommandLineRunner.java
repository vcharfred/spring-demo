package top.vchar.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * <p> SpringApplication.run()开始运行，但是完成应用启动之前，同时并行运行某些代码，比如一些系统初始化的工作  </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/9/3
 */
@Component
public class CustomizeCommandLineRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        // TODO SpringApplication.run()开始运行，但是完成应用启动之前执行的代码

    }
}
