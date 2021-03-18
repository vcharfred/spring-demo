package top.vchar.redis.geohash;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import top.vchar.RedisApplication;

import java.util.Iterator;

/**
 * <p> 坐标距离计算 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/3/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApplication.class)
public class GeoHashTemplate {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * GeoHash 示例; redis版本必须大于3.2
     */
    @Test
    public void geoHashDemo() {
        GeoOperations<String, String> opsForGeo = this.redisTemplate.opsForGeo();

        // 北京位于东经115.7°—117.4°，北纬39.4°—41.6°，中心位于北纬39°54′20″，东经116°25′29″
        Point point = new Point(116.25, 39.54);
        opsForGeo.add("city", point, "北京");
        // 成都, 东经104.06, 北纬30.67
        point = new Point(104.06, 30.67);
        opsForGeo.add("city", point, "成都");

        // 东经 139.75,北纬 35.68
        point = new Point(139.75, 35.68);
        opsForGeo.add("city", point, "东京");

        // 计算2地之间的距离
        Distance distance = opsForGeo.distance("city", "北京", "成都", RedisGeoCommands.DistanceUnit.KILOMETERS);
        System.out.println("成都到北京的距离：" + distance.getValue() + "km");

        // 查询指定距离2000km的城市
        GeoResults<RedisGeoCommands.GeoLocation<String>> radius = opsForGeo.radius("city", "成都"
                , new Distance(2000, RedisGeoCommands.DistanceUnit.KILOMETERS));
        Iterator<GeoResult<RedisGeoCommands.GeoLocation<String>>> iterator = radius.iterator();
        while (iterator.hasNext()) {
            GeoResult<RedisGeoCommands.GeoLocation<String>> next = iterator.next();
            System.out.println(next.getContent().getName());
        }
    }


}
