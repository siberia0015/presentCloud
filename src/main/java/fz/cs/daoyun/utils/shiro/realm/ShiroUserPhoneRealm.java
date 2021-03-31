package fz.cs.daoyun.utils.shiro.realm;

import com.alibaba.fastjson.JSONObject;
import fz.cs.daoyun.domain.User;
import fz.cs.daoyun.mapper.UserMapper;
import fz.cs.daoyun.service.IMenuService;
import fz.cs.daoyun.service.IPassportService;
import fz.cs.daoyun.service.IPermissionService;
import fz.cs.daoyun.service.IUserService;
import fz.cs.daoyun.utils.shiro.token.UserPhoneToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShiroUserPhoneRealm extends AuthorizingRealm {

    @Autowired
    private IUserService userService;

    @Autowired
    private IPassportService passportService;

    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private IMenuService menuService;

    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        //这儿的CredentialsMatcher的new的对象必须是AllowAllCredentialsMatcher
        CredentialsMatcher matcher = new AllowAllCredentialsMatcher();
        super.setCredentialsMatcher(matcher);
    }




    /**
     * 通过此方法完成认证数据的获取及封装，系统底层会将认证数据传递认证管理器，有认证管理器完成认证操作
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UserPhoneToken token = null;
        if (authenticationToken instanceof UserPhoneToken) {
            token = (UserPhoneToken) authenticationToken;
        }else {
            return null;
        }
        //获取我发送验证码是存入session中的验证码和手机号
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        JSONObject json = (JSONObject) session.getAttribute("checkNumberJson");
        String verificationCode = json.getString("checkNumber");
        String phone =json.getString("telephoneNumber");
        System.out.println(verificationCode);
        System.out.println(phone);
        //获取controller传过来的数据
        String code = (String) token.getPrincipal();

        //去数据库根据手机号查询用户信息
        User user = userService.findByTel(Long.parseLong(phone));
        if (StringUtils.isEmpty(verificationCode)) {
            try {
                throw new Exception("网络错误");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //比对手机号
        if (!verificationCode.equals(code)) {
            try {
                throw new Exception("验证码不正确");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (user == null) {
            throw new UnknownAccountException();
        }

        return new SimpleAuthenticationInfo(user,phone,getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        String username = (String) principals.getPrimaryPrincipal();
//        Set<String> permissions = permissionService.findPermissionsByUsername(username);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(userService.queryRoles(username));
        authorizationInfo.setStringPermissions(userService.queryPermissions(username));
        return authorizationInfo;
    }
}
