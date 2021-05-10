package top.vchar.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.vchar.service.NoRibbonDemoService;
import top.vchar.service.RibbonDemoService;

/**
 * <p> ribbon 测试 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/5/10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RibbonDemoTest {

    @Autowired
    private NoRibbonDemoService noRibbonDemoService;
    @Autowired
    private RibbonDemoService ribbonDemoService;

    @Test
    public void findGoodsByNoRibbon() {
        noRibbonDemoService.findGoods(1L);
    }

    @Test
    public void findGoodsByRibbon() {
        ribbonDemoService.findGoods(1L);
    }

}
