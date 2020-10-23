package top.vchar.train;

import org.junit.Test;

import java.util.Arrays;

/**
 * <p> 站点信息测试 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/21
 */
public class StationNameTest {

    @Test
    public void stationNameJsTest(){
        String str = "var station_names ='@bjb|北京北|VAP|beijingbei|bjb|0@bjd|北京东|BOP|beijingdong|bjd|1';";
        String[] arr = str.split("\\|");
        // 5个
        System.out.println(Arrays.toString(arr));
    }
}
