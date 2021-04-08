package fz.cs.daoyun.web.controller;


import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.zhenzi.sms.ZhenziSmsClient;
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
    public Map<String, Object> login(@RequestParam("account") String username, @RequestParam("password")String password){
        System.out.println("login");
        Map<String,Object> map = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String em = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        String ph = "^[1][34578]\\d{9}$";
        String name = null;
        if(username.matches(em)){
            /*邮箱登录*/
            User user = null;
            try {
                user = userService.findByEmail(username);
            } catch (Exception e) {
                e.printStackTrace();
                map.put("code",100);
                map.put("msg","用户不存在或者密码错误");
                return map;
            }
            name = user.getName();
        }else if (username.matches(ph)){
            /*手机登录*/
            User user = null;
            try {
                Long un = Long.parseLong(username);
                user = userService.findByTel(un);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                map.put("code",100);
                map.put("msg","用户不存在或者密码错误");
                return map;
            }
            name = user.getName();
        }else {
            /*用户名登录*/
            User user = null;
            try {
                user = userService.findByName(username);
            } catch (Exception e) {
                e.printStackTrace();
                map.put("code",100);
                map.put("msg","用户不存在或者密码错误");
                return map;
            }
            name = user.getName();
        }

        try {
            String pwd= Md5Util.encrypt(name, password);
            UsernamePasswordToken token = new UsernamePasswordToken(name, pwd);
            token.setRememberMe(true);
            System.out.println("login: " + token);
            subject.login(token);
        }catch (IncorrectCredentialsException e){
            map.put("code",100);
            map.put("msg","用户不存在或者密码错误");
            return map;
        }catch (AuthenticationException e) {
            map.put("code",200);
            map.put("msg","该用户不存在");
            return map;
        } catch (Exception e) {
            map.put("code",300);
            map.put("msg","未知异常");
            return map;
        }
        map.put("code",0);
        map.put("msg","登录成功");
        map.put("token",SecurityUtils.getSubject().getSession().getId().toString());
        map.put("username", username);
        session.setAttribute("loginMap", map);
        System.out.println(map);
        return map;

    }

    /**
     * 用户未登录时的返回信息
     * @return
     */
    @ResponseBody
    @RequestMapping("/unauth")
    public Map<String,Object> unauth(){
        Map<String,Object> map = new HashMap<>();
        map.put("code",500);
        map.put("msg","未登录");
        return map;
    }

    /**
     * 获取验证码
     * @param telephoneNumber
     * @return
     */
    @ResponseBody
    @PostMapping("/getCode")
    public Map<String, Object> getCode(@RequestParam("telephoneNumber")String telephoneNumber){
        Map<String,Object> map = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
        SmsUtils smsUtils = new SmsUtils();
        Session session = subject.getSession();
        try {
            //随机生成验证码
            String checkNumber =  String.valueOf(new Random().nextInt(999999));
            //将验证码通过阿里云接口发送至手机
            SendSmsResponse sendSms =smsUtils.sendSms(telephoneNumber,checkNumber);//填写你需要测试的手机号码

            System.out.println("短信接口返回的数据----------------");
            System.out.println("Code=" + sendSms.getCode());
            System.out.println("Message=" + sendSms.getMessage());
            System.out.println("RequestId=" + sendSms.getRequestId());
            System.out.println("BizId=" + sendSms.getBizId());

            //将验证码存到传给前端中,同时存入创建时间
            map.put("phone", telephoneNumber);
            map.put("checkNumber", checkNumber);
            map.put("createTime", System.currentTimeMillis());
            map.put("code", 0);
            map.put("msg", "发送验证码成功");
            return map;
        } catch (Exception e) {
            map.put("code", 100);
            map.put("msg", "发送验证码失败");
            e.printStackTrace();
            return map;
        }
    }

    /**
     * 手机登陆
     * @param telephoneNumber
     * @param checkNumber
     * @return
     */
    @PostMapping("/phoneLogin")
    public  Map<String,Object>  phonelogin(@RequestParam("telephoneNumber") String telephoneNumber, @RequestParam("checkNumber") String checkNumber){
        Map<String,Object> map = new HashMap<>();
        try{
            Long un = Long.parseLong(telephoneNumber);
            User user = userService.findByTel(un);
            System.out.println(user);
            map.put("code", 0);
            map.put("msg", "登录成功");
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", 100);
            map.put("msg", "用户不存在");
        }
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
            String email
    ){
        System.out.println(phone);
        System.out.println(password);
        System.out.println(checkNumber);
        Map<String,Object> map = new HashMap<>();
        User user = new User();
        Long tel = Long.parseLong(phone);
        user.setTel(tel);
        user.setPassword(password);
        user.setNickname(nickname);
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
                System.out.println("验证码为空");
                map.put("code", 100);
                map.put("msg", "验证码为空");
            }else { //
                // 设定默认用户名
                user.setName("用户" + checkNumber);
                System.out.println("新用户信息：" + user);
                try {
                    if(userService.findByName(user.getName())!=null){
                        System.out.println("用户已经存在");
                        map.put("code", 200);
                        map.put("msg", "用户已经存在");
                    } else {
                        userService.saveUser(user);
                        System.out.println("注册成功");
                        map.put("code", 0);
                        map.put("msg", "注册成功");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.out.println("其他错误");
            map.put("code", 300);
            map.put("msg", "其他错误");
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 忘记密码
     * @param telephoneNumber
     * @param checkNumber
     * @param password1
     * @return
     */
    @GetMapping("/forgetPassword")
    public Map  forgetPassword(@RequestParam("telephoneNumber") String telephoneNumber, @RequestParam("checkNumber") String checkNumber,@RequestParam("password1")String password1){
        Map<String,Object> map = new HashMap<>();
        if (telephoneNumber ==  null  ){
            map.put("tel","电话号码为空");
        }
        Long tel = Long.parseLong(telephoneNumber);

        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        JSONObject json = (JSONObject) session.getAttribute("checkNumberJson");
        String code = json.getString("checkNumber");
        if(code == null ||code.length() == 0){
            map.put("code","验证码为空");
        }else if(code.equals(checkNumber)){
            User user = userService.findByTel(tel);
            user.setPassword(password1);
            user = passwordHelper.encryptPassword(user);
            map.put("succes","修改成功");
        }else
            map.put("code", "验证码有误");
        return map;
    }

    /**
     * 忘记密码2
     * @param user
     * @param passwordold
     * @param passwordnew
     * @return
     */
    @PostMapping("/forgetPasswordNoCode")
    public Result  forgetpasswordNoCode(
            @RequestBody User user,
            @RequestParam("passwordold")String passwordold,
            @RequestParam("passwordnew")String passwordnew
    ){
        String pwd= Md5Util.encrypt(user.getName(), passwordold);

        if(pwd.equals(user.getPassword())){
            user.setPassword(passwordnew);
            userService.savePwd(user);
            return Result.success();
        }else{
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
    }

    /**
     * 登出
     * @return
     */
    @RequestMapping("/logout")
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
