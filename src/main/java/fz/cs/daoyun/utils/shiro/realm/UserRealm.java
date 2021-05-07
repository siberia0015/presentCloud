package fz.cs.daoyun.utils.shiro.realm;


import fz.cs.daoyun.domain.User;
import fz.cs.daoyun.service.*;
import fz.cs.daoyun.utils.shiro.spring.SpringCacheManagerWrapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Set;

@Component
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private IUserService userService;


    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private IMenuService menuService;


    /*
    * 本函数用于执行授权逻辑
    * 授权模块，获取用户角色和权限
    * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
//        Set<String> permissions = permissionService.findPermissionsByUsername(username);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(userService.queryRoles(username));
        authorizationInfo.setStringPermissions(userService.queryPermissions(username));
        return authorizationInfo;
    }


    /*
    * 本函数用于实现认证逻辑
    * @param token AuthenticationToken 身份认证 token
    * @return AuthenticationInfo 身份认证信息
    * @throws AuthenticationException 认证相关异常
    * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

        String username = (String) token.getPrincipal();
        User user = null;
        if (!StringUtils.isEmpty(username)) {
            user = userService.findByName(username);
        }
        if (user == null) {
            throw new UnknownAccountException();//没找到帐号
        }
        String password = user.getPassword();

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getName(), //用户名
                password, //密码
                getName()  //realm name
        );

        return authenticationInfo;
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }


    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

//    @Override
//    public void clearCache(PrincipalCollection principals) {
//        super.clearCache(principals);
//    }

//    public void clearCachedAuthorizationInfo() {
//        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
//        super.clearCachedAuthorizationInfo(principals);
//    }

//
//    public void clearCachedAuthenticationInfo() {
//        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
//        super.clearCachedAuthenticationInfo(principals);
//    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        System.out.println("调用cache清理操作");
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        User user = userService.findByName(username);
        PrincipalCollection principals2 = new SimplePrincipalCollection(
                username, getName());
        System.out.println(principals2);
        super.clearCache(principals);

    }



    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}
