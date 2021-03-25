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
    public void test(){
        System.out.println("xxx");
        try{
            User users = new User();
            users = userService.findByName("admin");
            System.out.println(users);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
