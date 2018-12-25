import com.Application;
import com.book.three.Role;
import com.book.three.User;
import com.book.three.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/12/25.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
public class TestMongo {
    @Autowired
    private UserService userService;

    @Test
    public void save() {
        Role role = new Role();
        role.setNote("testNote");
        role.setRoleNamer("testNamers");
        List<Role> roles = new ArrayList<Role>();
        roles.add(role);

        User user = new User();
        user.setName("testName");
        user.setId(1L);
        user.setReles(roles);

//        userService.saveUser(user);

        User user1 = userService.findUser(1L);
//        System.out.println(user1);
//        System.out.println(user1.getReles());

        List<User> testName = userService.findUsers("testName", 1, 3);
//        System.out.println(testName);

    }


}
