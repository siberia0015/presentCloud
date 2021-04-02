package fz.cs.daoyun;

import fz.cs.daoyun.domain.User;
import fz.cs.daoyun.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@SpringBootTest
public class UserTest {
    @Autowired
    IUserService userService;

    @Test
    public void findByName() {
        System.out.println("findByName");
        try {
            User user = new User();
            user = userService.findByName("admin");
            System.out.println(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findAll() {
        System.out.println("findAll");
        try {
            List<User> users = userService.findAll();
            System.out.println(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
