package fz.cs.daoyun.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import fz.cs.daoyun.domain.User;
import fz.cs.daoyun.service.*;
import fz.cs.daoyun.utils.shiro.spring.SpringCacheManagerWrapper;
import fz.cs.daoyun.utils.shiro.token.UserPhoneToken;
import fz.cs.daoyun.utils.tools.Md5Util;
import fz.cs.daoyun.utils.tools.Result;
import fz.cs.daoyun.utils.tools.ResultCodeEnum;
import fz.cs.daoyun.utils.tools.SmsUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Validated
@RestController
@RequiredArgsConstructor
public class LoginController  extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserService userService;

    @Autowired
    private IPassportService passportService;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IRolePermissionService rolePermissionService;

    @Autowired
    private PasswordHelper passwordHelper;

    @Autowired
    private ILoginLogService iLoginLogService;

    /**
     * test
     * @return
     */
    @ResponseBody
    @GetMapping("/test")
    public  String Test(){
        System.out.println(".............Login.........................");

        List<User> users = userService.findAll();
        System.out.println(".................Login success........................");
        return users.toString();
    }

    /**
     * 普通登录， 返回登录信息的map
     * @param username
     * @param password
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/login")
    public Map<String, Object> login(@RequestParam("account")String username,
                                     @RequestParam("password")String password
    ){
        logger.info("/login");
        Map<String,Object> map = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String em = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        String ph = "^[1][34578]\\d{9}$";
        String name = null;
        User user = null;
        if(username.matches(em)){
            /*邮箱登录*/
            try {
                user = userService.findByEmail(username);
                if (user == null) {
                    map.put("code",100);
                    map.put("msg","用户不存在");
                    logger.info(map.toString());
                    return map;
                }
            } catch (Exception e) {
                e.printStackTrace();
                map.put("code",400);
                map.put("msg","未知异常");
                logger.info(map.toString());
                return map;
            }
        }else if (username.matches(ph)){
            /*手机登录*/
            try {
                Long un = Long.parseLong(username);
                user = userService.findByPhone(un);
                if (user == null) {
                    map.put("code",100);
                    map.put("msg","用户不存在");
                    logger.info(map.toString());
                    return map;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                map.put("code",500);
                map.put("msg","未知异常");
                logger.info(map.toString());
                return map;
            }
        }else {
            /*用户名登录*/
            try {
                user = userService.findByName(username);
                if (user == null) {
                    map.put("code",100);
                    map.put("msg","用户不存在");
                    logger.info(map.toString());
                    return map;
                }
            } catch (Exception e) {
                e.printStackTrace();
                map.put("code",100);
                map.put("msg","用户不存在或者密码错误");
                logger.info(map.toString());
                return map;
            }
        }
        name = user.getName();
        try {
            String pwd= Md5Util.encrypt(name, password);
            UsernamePasswordToken token = new UsernamePasswordToken(name, pwd);
            token.setRememberMe(true);
            System.out.println("login: " + token);
            subject.login(token);
        }catch (IncorrectCredentialsException e){
            map.put("code",100);
            map.put("msg","用户不存在或者密码错误");
            logger.info(map.toString());
            return map;
        }catch (AuthenticationException e) {
            map.put("code",200);
            map.put("msg","该用户不存在");
            logger.info(map.toString());
            return map;
        } catch (Exception e) {
            map.put("code",300);
            map.put("msg","未知异常");
            logger.info(map.toString());
            return map;
        }
        map.put("code",0);
        map.put("msg","登录成功");
        map.put("token",SecurityUtils.getSubject().getSession().getId().toString());
        map.put("user", user);
        session.setAttribute("loginMap", map);
        logger.info(map.toString());
        return map;

    }

    /**
     * 用户未登录时的返回信息
     * @return
     */
    @ResponseBody
    @GetMapping("/unauth")
    public Map<String,Object> unauth(){
        Map<String,Object> map = new HashMap<>();
        map.put("code",500);
        map.put("msg","未登录");
        logger.info(map.toString());
        return map;
    }

    /**
     * 获取验证码
     * @param phone
     * @return
     */
    @ResponseBody
    @PostMapping("/getCode")
    public Map<String, Object> getCode(@RequestParam("phone")String phone){
        Map<String,Object> map = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
        SmsUtils smsUtils = new SmsUtils();
        Session session = subject.getSession();
        try {
            //随机生成验证码
            String checkNumber =  String.valueOf(new Random().nextInt(999999));
            //将验证码通过阿里云接口发送至手机
            SendSmsResponse sendSms =smsUtils.sendSms(phone,checkNumber);//填写你需要测试的手机号码

            System.out.println("短信接口返回的数据----------------");
            System.out.println("Code=" + sendSms.getCode());
            System.out.println("Message=" + sendSms.getMessage());
            System.out.println("RequestId=" + sendSms.getRequestId());
            System.out.println("BizId=" + sendSms.getBizId());

            if (!sendSms.getCode().equals("OK") || !sendSms.getMessage().equals("OK")) {
                map.put("code", 200);
                map.put("msg", sendSms.getMessage());
            } else {
                //将验证码存到传给前端中,同时存入创建时间
                map.put("phone", phone);
                map.put("checkNumber", checkNumber);
                map.put("createTime", System.currentTimeMillis());
                map.put("code", 0);
                map.put("msg", "发送验证码成功");
            }
        } catch (Exception e) {
            map.put("code", 200);
            map.put("msg", "发送验证码失败");
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 手机登陆
     * @param phone
     * @param checkNumber
     * @return
     */
    @PostMapping("/phoneLogin")
    public  Map<String,Object>  phonelogin(@RequestParam("phone") String phone,
                                           @RequestParam("checkNumber") String checkNumber
    ){
        Map<String,Object> map = new HashMap<>();
        try{
            Long un = Long.parseLong(phone);
            User user = userService.findByPhone(un);
            if (user == null) {
                map.put("code", 200);
                map.put("msg", "用户不存在");
            } else {
                map.put("code", 0);
                map.put("msg", "登录成功");
                map.put("token",SecurityUtils.getSubject().getSession().getId().toString());
                map.put("user", user);
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", 100);
            map.put("msg", "未知错误");
        }
        logger.info(map.toString());
        return map;
    }

    /**
     * 用户注册
     * @param phone
     * @param password
     * @param checkNumber
     * @param nickname
     * @param sex
     * @param school
     * @param classes
     * @param school_number
     * @param email
     * @return
     */
    @ResponseBody
    @PostMapping("/register")
    public  Map register(
            @RequestParam("phone") String phone,
            @RequestParam("password") String password,
            @RequestParam("checkNumber") String checkNumber,
            // String username,
            String nickname,
            String sex,
            String school,
            String classes,
            String school_number,
            String email,
            String college,
            Boolean identity
    ){
        Map<String,Object> map = new HashMap<>();
        User user = new User();
        // 设定默认用户名
        user.setName(phone);
        Long phone1 = Long.parseLong(phone);
        user.setPhone(phone1);
        user.setPassword(password);
        user.setNickname(nickname);
        user.setCollege(college);
        if (identity != null) user.setIdentity(identity);
        if(!StringUtils.isEmpty(sex)){
            user.setSex(sex);
        }
        user.setSchool(school);
        user.setClasses(classes);
        user.setEmail(email);
        user.setSchoolNumber(school_number);
        //  验证码正确性在前端验证
        try{
            if(checkNumber == null){
                map.put("code", 100);
                map.put("msg", "验证码为空");
            }else {
                System.out.println("新用户信息：" + user);
                try {
                    if(userService.findByName(user.getName())!=null){
                        map.put("code", 200);
                        map.put("msg", "用户已经存在");
                    } else {
                        userService.saveUser(user);
                        map.put("user", user);
                        map.put("code", 0);
                        map.put("msg", "注册成功");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            map.put("code", 300);
            map.put("msg", "其他错误");
            e.printStackTrace();
        }
        logger.info(map.toString());
        return map;
    }

    /**
     * 通过手机验证重置密码
     * @param phone
     * @param checkNumber
     * @param password
     * @return
     */
    @PostMapping("/resetPasswordWithCode")
    public Map resetPassword(@RequestParam("phone") String phone,
                             @RequestParam("checkNumber") String checkNumber,
                             @RequestParam("password")String password
    ){
        Map<String,Object> map = new HashMap<>();
        if (phone ==  null){
            map.put("code", 100);
            map.put("msg", "手机号码为空");
        } else {
            Long phone1 = Long.parseLong(phone);
            User user = userService.findByPhone(phone1);
            if (user == null) {
                map.put("code", 200);
                map.put("msg", "用户不存在");
            } else {
                user.setPassword(password);
                userService.savePwd(user);
                map.put("code", 0);
                map.put("msg", "修改成功");
            }
        }
        logger.info(map.toString());
        return map;
    }

    /**
     * 通过旧密码重置密码
     * @param account
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @PostMapping("/resetPasswordWithOldPassword")
    public Map forgetpasswordNoCode(
            @RequestParam("account")String account,
            @RequestParam("oldPassword")String oldPassword,
            @RequestParam("newPassword")String newPassword
    ){
        String em = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        String ph = "^[1][34578]\\d{9}$";
        User user = null;
        Map<String,Object> map = new HashMap<>();
        if(account.matches(em)){
            try {
                user = userService.findByEmail(account);
                if (user == null) {
                    map.put("code",100);
                    map.put("msg","用户不存在");
                    return map;
                }
            } catch (Exception e) {
                e.printStackTrace();
                map.put("code",400);
                map.put("msg","未知异常");
                return map;
            }
        }else if (account.matches(ph)){
            try {
                Long un = Long.parseLong(account);
                user = userService.findByPhone(un);
                if (user == null) {
                    map.put("code",100);
                    map.put("msg","用户不存在");
                    return map;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                map.put("code",500);
                map.put("msg","未知异常");
                return map;
            }
        }else {
            try {
                user = userService.findByName(account);
                if (user == null) {
                    map.put("code",100);
                    map.put("msg","用户不存在");
                    return map;
                }
            } catch (Exception e) {
                e.printStackTrace();
                map.put("code",100);
                map.put("msg","用户不存在或者密码错误");
                return map;
            }
        }

        String pwd= Md5Util.encrypt(user.getName(), oldPassword);
        if(pwd.equals(user.getPassword())){
            user.setPassword(newPassword);
            userService.savePwd(user);
            map.put("code", 0);
            map.put("msg", "修改成功");
        }else{
            map.put("code", 200);
            map.put("msg", "与原密码不符");
        }
        return map;
    }

    /**
     * 登出
     * @return
     */
    @PostMapping("/logout")
    public Result logout() {
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.NOT_IMPLEMENTED);
        }
    }
}
