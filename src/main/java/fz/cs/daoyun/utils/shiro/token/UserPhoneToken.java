package fz.cs.daoyun.utils.shiro.token;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class UserPhoneToken extends UsernamePasswordToken implements Serializable {

    private static final long serialVersionUID = 6293390033867929958L;
    // 验证码
    private String checkNumber;
    //无参构造
    public UserPhoneToken(){}

    //获取存入的值
    @Override
    public Object getPrincipal() {
        if (checkNumber == null) {
            return getUsername();
        } else {
            return checkNumber;
        }
    }

    @Override
    public Object getCredentials() {
        if (checkNumber == null) {
            return getPassword();
        }else {
            return "ok";
        }

    }

    public UserPhoneToken(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public UserPhoneToken(final String userName, final String password) {
        super(userName, password);
    }

    public String getcheckNumber() {
        return checkNumber;
    }

    public void setcheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }
    @Override
    public String toString() {
        return "PhoneToken [PhoneNum=" + checkNumber + "]";
    }

}
