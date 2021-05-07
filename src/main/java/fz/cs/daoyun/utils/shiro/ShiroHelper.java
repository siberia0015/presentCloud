package fz.cs.daoyun.utils.shiro;


import fz.cs.daoyun.annotation.Helper;
import fz.cs.daoyun.utils.shiro.realm.UserRealm;
import org.apache.shiro.authz.AuthorizationInfo;

/**
 * @author MrBird
 */
@Helper
public class ShiroHelper extends UserRealm {

    /**
     * 获取当前用户的角色和权限集合
     *
     * @return AuthorizationInfo
     */
    public AuthorizationInfo getCurrentUserAuthorizationInfo() {
        return super.doGetAuthorizationInfo(null);
    }
}
