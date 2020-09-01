package top.vchar.predicate;

import lombok.Data;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * <p> 时间断言工厂，指定时间内禁止访问 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/9/1
 */
@Component
public class TimeRoutePredicateFactory extends AbstractRoutePredicateFactory<TimeRoutePredicateFactory.Config> {

    public TimeRoutePredicateFactory(){
        super(TimeRoutePredicateFactory.Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("startHour", "startMinute", "endHour", "endMinute");
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return exchange -> {
            LocalTime now = LocalTime.now();

            LocalTime start = config.getStart();
            LocalTime end = config.getEnd();

            if(start.compareTo(end)==0){
                return now.compareTo(start)==0;
            }else {
                if(start.compareTo(end)<0){
                    return start.compareTo(now)<1 && now.compareTo(end)<1;
                }
                return start.compareTo(now)<1 || now.compareTo(end)<1;
            }
        };
    }
    @Validated
    @Data
    public static class Config {

        /**
         * 开始时间
         */
        @Max(value = 23, message = "the 'startHour' max value is 23")
        @Min(value = 0, message = "the 'startHour' min value is 0")
        @NotNull(message = "the 'startHour' can not null")
        private Integer startHour;

        @Max(value = 59, message = "the 'startMinute' max value is 59")
        @Min(value = 0, message = "the 'startMinute' min value is 0")
        @NotNull(message = "the 'startMinute' can not null")
        private Integer startMinute;

        public LocalTime getStart(){
            return LocalTime.of(this.startHour, this.startMinute);
        }

        /**
         * 结束时间:hh:mm
         */
        @Max(value = 23, message = "the 'endHour' max value is 23")
        @Min(value = 0, message = "the 'endHour' min value is 0")
        @NotNull(message = "the 'endHour' can not null")
        private Integer endHour;

        @Max(value = 59, message = "the 'endMinute' max value is 59")
        @Min(value = 0, message = "the 'endMinute' min value is 0")
        @NotNull(message = "the 'endMinute' can not null")
        private Integer endMinute;

        public LocalTime getEnd(){
            return LocalTime.of(this.endHour, this.endMinute);
        }
    }
}
