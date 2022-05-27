/**
 * Created by admin on 2019/1/15.
 */

import com.Application;
import com.book.five.LeadClient;
import com.book.five.LeadSignQueryRequest;
import com.book.five.TopLeadClient;
import com.dao.CodeMapper;
import com.entity.Code;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.SocketUtils;

import java.io.File;
import java.io.InputStream;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
public class Test1 {

    @Autowired
    private LeadClient topLeadClient;

    @Autowired
    private CodeMapper codeMapper;

    @Test
    public void test() throws InstantiationException, IllegalAccessException {
        System.out.println(topLeadClient.getClass());
//        System.out.println(leadClient.getClass());
        LeadSignQueryRequest leadRequest = new LeadSignQueryRequest();
//        System.out.println(leadClient.execute(leadRequest).getResultCode());
//        leadClient.setHostAddress("111");
        System.out.println(topLeadClient.execute(leadRequest));

    }


    @Test
    public void test2() throws InstantiationException, IllegalAccessException {
        File file = new File("home/123.txt");
        String name = file.getName();
        String path = file.getPath();
        System.out.println(name);
        System.out.println(path);
    }

    @Test
    @Transactional
    public void testInsert(){
        String code = "test2020-04-16";
        int i = codeMapper.insertDode(code);
        System.out.println(i);
        List<Code> all = codeMapper.findAll();
        System.out.println(all);
    }
}
