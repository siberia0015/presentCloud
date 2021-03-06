package fz.cs.daoyun.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import fz.cs.daoyun.domain.User;
import fz.cs.daoyun.domain.UserAuths;
import fz.cs.daoyun.service.*;
import fz.cs.daoyun.utils.shiro.spring.SpringCacheManagerWrapper;
import fz.cs.daoyun.utils.shiro.token.UserPhoneToken;
import fz.cs.daoyun.utils.tools.Md5Util;
import fz.cs.daoyun.utils.tools.Result;
import fz.cs.daoyun.utils.tools.ResultCodeEnum;
import fz.cs.daoyun.utils.tools.SmsUtils;
import jdk.nashorn.internal.parser.Token;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
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
        Jedis jedis = new Jedis("127.0.0.1");
        System.out.println(".............Login.........................");
        System.out.println(jedis.get("checkNumber"));
        List<User> users = userService.findAll();
        System.out.println(".................Login success........................");
        return users.toString();
    }

    /**
     * ???????????????
     * @param phone
     * @return
     */
    @ResponseBody
    @PostMapping("/getCode")
    public Map<String, Object> getCode(@RequestParam("phone")String phone){
        logger.info("/getCode");
        Jedis jedis = new Jedis("127.0.0.1");
        Map<String,Object> map = new HashMap<>();
        SmsUtils smsUtils = new SmsUtils();
        Session session = SecurityUtils.getSubject().getSession();
        try {
            //?????????????????????
            String checkNumber =  String.valueOf(new Random().nextInt(999999));
            //????????????????????????????????????????????????
            SendSmsResponse sendSms =smsUtils.sendSms(phone,checkNumber);//????????????????????????????????????

            System.out.println("???????????????????????????----------------");
            System.out.println("Code=" + sendSms.getCode());
            System.out.println("Message=" + sendSms.getMessage());
            System.out.println("RequestId=" + sendSms.getRequestId());
            System.out.println("BizId=" + sendSms.getBizId());

            if (!sendSms.getCode().equals("OK") || !sendSms.getMessage().equals("OK")) {
                map.put("code", 200);
                map.put("msg", sendSms.getMessage());
            } else {
                //?????????????????????????????????,????????????????????????
                map.put("phone", phone);
                map.put("checkNumber", checkNumber);
                map.put("createTime", System.currentTimeMillis());
                map.put("code", 0);
                map.put("msg", "?????????????????????");
                SecurityUtils.getSubject().getSession().setAttribute("checkNumber", checkNumber);
                jedis.set("checkNumber", checkNumber);
                logger.info("checkNumber " + (String)session.getAttribute("checkNumber"));
            }
        } catch (Exception e) {
            map.put("code", 200);
            map.put("msg", "?????????????????????");
            e.printStackTrace();
        }
        return map;
    }

    /**
     * ?????????????????????????????????
     * @return
     */
    @ResponseBody
    @GetMapping("/unauth")
    public Map<String,Object> unauth(){
        logger.info("/unauth");
        Map<String,Object> map = new HashMap<>();
        map.put("code",500);
        map.put("msg","?????????");
        logger.info(map.toString());
        return map;
    }

    /**
     * ??????????????? ?????????????????????map
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
            /*????????????*/
            try {
                user = userService.findByEmail(username);
                if (user == null) {
                    map.put("code",100);
                    map.put("msg","???????????????");
                    logger.info(map.toString());
                    return map;
                }
            } catch (Exception e) {
                e.printStackTrace();
                map.put("code",400);
                map.put("msg","????????????");
                logger.info(map.toString());
                return map;
            }
        }else if (username.matches(ph)){
            /*????????????*/
            try {
                Long un = Long.parseLong(username);
                user = userService.findByPhone(un);
                if (user == null) {
                    map.put("code",100);
                    map.put("msg","???????????????");
                    logger.info(map.toString());
                    return map;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                map.put("code",500);
                map.put("msg","????????????");
                logger.info(map.toString());
                return map;
            }
        }else {
            /*???????????????*/
            try {
                user = userService.findByName(username);
                if (user == null) {
                    map.put("code",100);
                    map.put("msg","???????????????");
                    logger.info(map.toString());
                    return map;
                }
            } catch (Exception e) {
                e.printStackTrace();
                map.put("code",100);
                map.put("msg","?????????????????????????????????");
                logger.info(map.toString());
                return map;
            }
        }
        name = user.getName();
        try {
            String pwd= Md5Util.encrypt(name, password);
            UsernamePasswordToken token = new UsernamePasswordToken(name, pwd);
            token.setRememberMe(true);
            subject.login(token);
        }catch (IncorrectCredentialsException e){
            map.put("code",100);
            map.put("msg","?????????????????????????????????");
            logger.info(map.toString());
            return map;
        }catch (AuthenticationException e) {
            map.put("code",200);
            map.put("msg","??????????????????");
            logger.info(map.toString());
            return map;
        } catch (Exception e) {
            map.put("code",300);
            map.put("msg","????????????");
            logger.info(map.toString());
            return map;
        }
        map.put("code",0);
        map.put("msg","????????????");
        map.put("token",SecurityUtils.getSubject().getSession().getId().toString());
        map.put("user", user);
        session.setAttribute("user", user);
        session.setAttribute("loginMap", map);
        logger.info(map.toString());
        logger.info("???????????????" + (String)SecurityUtils.getSubject().getSession().getAttribute("user").toString());
        return map;
    }

    /**
     * ????????????
     * phoneLogin?????????????????????????????????token
     * @param phone
     * @param checkNumber
     * @return
     */
    @PostMapping("/phoneLogin")
    public Map<String,Object>  phonelogin(@RequestParam("phone") String phone,
                                           @RequestParam("checkNumber") String checkNumber
    ){
        logger.info("/phoneLogin");
        Map<String,Object> map = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        try{
/*            UsernamePasswordToken token = new UsernamePasswordToken(phone, checkNumber);
            token.setRememberMe(true);
            subject.login(token);*/
            Long un = Long.parseLong(phone);
            User user = userService.findByPhone(un);
            if (user == null) {
                map.put("code", 200);
                map.put("msg", "???????????????");
            } else {
                session.setAttribute("user",user);
                map.put("code", 0);
                map.put("msg", "????????????");
                map.put("token",SecurityUtils.getSubject().getSession().getId().toString());
                map.put("user", user);
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", 100);
            map.put("msg", "????????????");
        }
        session.setAttribute("loginMap", map);
        logger.info(map.toString());
        logger.info("???????????????" + SecurityUtils.getSubject().getSession().getAttribute("user").toString());
        return map;
    }

    /**
     * ????????????
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
            String username,
            String nickname,
            String sex,
            String school,
            String classes,
            String school_number,
            String email,
            String college,
            Integer identity
    ){
        Map<String,Object> map = new HashMap<>();
        Session session = SecurityUtils.getSubject().getSession();
        String correntCode = (String)session.getAttribute("checkNumber");
        if (!checkNumber.equals(correntCode)) {
            map.put("code", 300);
            map.put("msg", "???????????????");
            return map;
        }
        User user = new User();
        // ?????????????????????
        user.setName(phone);
        Long phone1 = Long.parseLong(phone);
        user.setPhone(phone1);
        user.setPassword(password);
        user.setNickname(nickname);
        user.setCollege(college);
        if (identity != null) user.setIdentity(identity);
        else user.setIdentity(2);
        if(!StringUtils.isEmpty(sex)){
            user.setSex(sex);
        }
        user.setSchool(school);
        user.setClasses(classes);
        user.setEmail(email);
        user.setSchoolNumber(school_number);
        //  ?????????????????????????????????
        try{
            if(checkNumber == null){
                map.put("code", 100);
                map.put("msg", "???????????????");
            }else {
                System.out.println("??????????????????" + user);
                try {
                    if(userService.findByName(user.getName())!=null){
                        map.put("code", 200);
                        map.put("msg", "??????????????????");
                    } else {
                        userService.saveUser(user);
                        map.put("user", user);
                        map.put("code", 0);
                        map.put("msg", "????????????");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            map.put("code", 400);
            map.put("msg", "????????????");
            e.printStackTrace();
        }
        logger.info(map.toString());
        return map;
    }

    /**
     * ??????????????????????????????
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
            map.put("msg", "??????????????????");
        } else {
            Long phone1 = Long.parseLong(phone);
            User user = userService.findByPhone(phone1);
            if (user == null) {
                map.put("code", 200);
                map.put("msg", "???????????????");
            } else {
                user.setPassword(password);
                userService.savePwd(user);
                map.put("code", 0);
                map.put("msg", "????????????");
            }
        }
        logger.info(map.toString());
        return map;
    }

    /**
     * ???????????????????????????
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
                    map.put("msg","???????????????");
                    return map;
                }
            } catch (Exception e) {
                e.printStackTrace();
                map.put("code",400);
                map.put("msg","????????????");
                return map;
            }
        }else if (account.matches(ph)){
            try {
                Long un = Long.parseLong(account);
                user = userService.findByPhone(un);
                if (user == null) {
                    map.put("code",100);
                    map.put("msg","???????????????");
                    return map;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                map.put("code",500);
                map.put("msg","????????????");
                return map;
            }
        }else {
            try {
                user = userService.findByName(account);
                if (user == null) {
                    map.put("code",100);
                    map.put("msg","???????????????");
                    return map;
                }
            } catch (Exception e) {
                e.printStackTrace();
                map.put("code",100);
                map.put("msg","?????????????????????????????????");
                return map;
            }
        }

        String pwd= Md5Util.encrypt(user.getName(), oldPassword);
        if(pwd.equals(user.getPassword())){
            user.setPassword(newPassword);
            userService.savePwd(user);
            map.put("code", 0);
            map.put("msg", "????????????");
        }else{
            map.put("code", 200);
            map.put("msg", "??????????????????");
        }
        return map;
    }

    /**
     * ??????
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

    @PostMapping("/oauthMobile")
    public Result oauthMobile(
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "identityType", required = true) String identityType,
            @RequestParam(value = "identifier", required = true) String identifier,
            @RequestParam(value = "credential", required = false) String credential
            ) {
        logger.info("/oauthMobile");
        Session session = SecurityUtils.getSubject().getSession();
        try {
            User user = new User();
            if (userId == null) {
                user.setName(identifier);
                user.setPassword("123456");
                if (userService.findByName(identifier) == null) {
                    userService.saveUser(user);
                }
            }
            user = userService.findByName(identifier);
            userId = user.getUserId();
            UserAuths userAuths = userService.oauthMobile(userId, identityType, identifier);
            session.setAttribute("user", user);
            return Result.success(userAuths);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
    }

    @PostMapping("/oauthWeb")
    public Result oauthWeb(@RequestParam(value = "clientId") String clientId,
                            @RequestParam(value = "clientSecret") String clientSecret,
                            @RequestParam(value = "code") String code,
                            @RequestParam(value = "identityType") String identityType,
                            @RequestParam(value = "credential", required = false) String credential

                           ) {
        logger.info("/oauthWeb");
        Session session = SecurityUtils.getSubject().getSession();
        try {
            String token_url = sendPost("https://github.com/login/oauth/access_token?client_id="+clientId+"&client_secret="+clientSecret+"&code="+code,null);
            String token = token_url.split("&")[0];
            String token1 = token.substring(13);
            logger.info("token= " + token1);
            // String res = httpGet("https://api.github.com/user?" + token + "", "token  " + token);
            String res = httpGet("https://api.github.com/user", "token  " + token1);
            JSONObject userInfo = (JSONObject) JSON.parse(res);
            String login = userInfo.getString("login");
            // ????????????
            if (userService.findByName(login) == null) {
                User user = new User();
                user.setName(login);
                user.setPassword("123456");
                userService.saveUser(user);
            }
            User user = userService.findByName(login);
            Long userId = user.getUserId();
            UserAuths userAuths = userService.oauthMobile(userId, identityType, login);
            session.setAttribute("user", user);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
    }

    /**
     * ????????? URL ??????POST???????????????
     *
     * @param url
     *            ??????????????? URL
     * @param param
     *            ???????????????????????????????????? name1=value1&name2=value2 ????????????
     * @return ????????????????????????????????????
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // ?????????URL???????????????
            URLConnection conn = realUrl.openConnection();
            // ???????????????????????????
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // ??????POST??????????????????????????????
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            // ??????URLConnection????????????????????????
            out = new PrintWriter(conn.getOutputStream());
            // ??????????????????
            out.print(param);
            // flush??????????????????
            out.flush();
            // ??????BufferedReader??????????????????URL?????????
            InputStream instream = conn.getInputStream();
            if(instream!=null){
                in = new BufferedReader( new InputStreamReader(instream));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //??????finally?????????????????????????????????
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * ????????? URL ??????GET???????????????
     * @param url
     * @param token
     * @return
     */
    public static String httpGet(String url, String token){
        System.out.println(token);
        // ???????????????????????????
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse=null;
        String finalString = null;
        HttpGet httpGet = new HttpGet(url);
        /**?????????????????????httpGet*/

        /*header???????????????*/
        httpGet.setHeader("Accept","*/*");
        httpGet.setHeader("Accept-Encoding","gzip, deflate");
        httpGet.setHeader("Cache-Control","no-cache");
        httpGet.setHeader("Connection", "keep-alive");
        httpGet.setHeader("Content-Type", "application/json;charset=UTF-8");
        /*????????????*/
        httpGet.setHeader("Content-Type","application/json");
        httpGet.setHeader("Authorization",token);

        try {
            httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            finalString= EntityUtils.toString(entity, "UTF-8");
            try {
                httpResponse.close();
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(finalString);
        return finalString;
    }
}
