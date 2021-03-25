package fz.cs.daoyun.utils.shiro.credentials;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.cache.Cache;

import java.util.concurrent.atomic.AtomicInteger;

public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {



    @Override
    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
        PasswordMatcher passwordMatcher = new PasswordMatcher();
        return passwordMatcher.doCredentialsMatch(authcToken, info);

    }
}
