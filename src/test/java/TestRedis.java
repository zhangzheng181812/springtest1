import com.Application;
import com.util.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by admin on 2018/12/24.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT ,classes = Application.class)
public class TestRedis {

    @Autowired
    RedisUtils redisUtils ;

    @Test
    public void testRedis(){
        redisUtils.set("key1","value1");
        System.out.println(redisUtils.get("key1"));
    }

    @Test
    public void testRedisListener(){
        redisUtils.redisTemplate.convertAndSend("topic1","testMessage");
    }
}
