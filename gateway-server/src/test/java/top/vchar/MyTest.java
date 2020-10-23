package top.vchar;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.*;

/**
 * <p>  TODO 功能描述 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/21
 */
public class MyTest {

    @Test
    public void sort(){
        Map<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        map.put("age", 10);
        List<Map<String, Object>> mapList = new ArrayList<>();
        Map<String, Object> addr0 = new HashMap<>();
        addr0.put("city", "武汉");
        addr0.put("province", "湖北");
        addr0.put("code", 1000);
        mapList.add(addr0);
        Map<String, Object> addr1 = new HashMap<>();
        addr1.put("city", "成都");
        addr1.put("province", "四川");
        addr1.put("code", 1001);
        mapList.add(addr1);
        map.put("address", mapList);

        System.out.println(JSONObject.toJSONString(map));
    }


}
