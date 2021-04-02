package fz.cs.daoyun;

import fz.cs.daoyun.domain.User;
import fz.cs.daoyun.utils.shiro.token.UserPhoneToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class LoginTest {
    @Test
    public void test(){
        System.out.println("xxx");
        try{
            Map<String,Object> map = new HashMap<>();
            Subject subject = SecurityUtils.getSubject();
            UserPhoneToken token = new UserPhoneToken("1234");
            try{
                subject.login(token);
                map.put("msg", "登录成功");
            }catch (Exception e){
                System.out.println(e);
                map.put("msg", "账号不存在");
                map.put("token",SecurityUtils.getSubject().getSession().getId().toString());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
