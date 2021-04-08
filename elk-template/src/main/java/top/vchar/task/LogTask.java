package top.vchar.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * <p> log build info </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/4/8
 */
@Slf4j
@Component
public class LogTask {

    @Scheduled(fixedRate = 5*1000)
    public void buildLogs(){
        log.debug("this is debug.");
        log.info("this is info.");
        log.warn("this is warn.");
        log.error("this is error.");
    }
}
