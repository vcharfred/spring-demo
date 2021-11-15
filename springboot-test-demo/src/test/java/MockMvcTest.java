import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import top.vchar.demo.spring.TestDemoApplication;

/**
 * <p> mockmvc 测试 </p>
 *
 * 相当于一个客户端发送请求
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/8 23:11
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestDemoApplication.class)
@AutoConfigureMockMvc
public class MockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void apiGETest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/hotel/detail?id=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

}
