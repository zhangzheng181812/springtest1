import com.Application;
import com.book.one.ConditionConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by admin on 2018/12/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT ,classes = Application.class)
public class TestCondition {

    @Test
    public void testCondition(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConditionConfig.class);
        String test = String.valueOf(context.getBean("test"));
        System.out.println(test);
    }
}
