import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.vchar.demo.spring.StartApplication;

/**
 * <p>  TODO 功能描述 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/8 23:01
 */
@RunWith(SpringRunner.class)  //底层用junit  SpringJUnit4ClassRunner
@SpringBootTest(classes={StartApplication.class})//启动整个springboot工程
public class TestDemo {

    @Before
    public void beforeTest(){
        System.out.println("=======before====");
    }

    @Test
    public void demo(){
        System.out.println("========OK=====");
        TestCase.assertEquals(1, 1);
    }

    @After
    public void afterTest(){
        System.out.println("=======after====");
    }
}
