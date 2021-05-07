package fz.cs.daoyun.controller;


import com.github.pagehelper.PageHelper;
import fz.cs.daoyun.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author siberia
 */
public class BaseController {

    private Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    protected User getCurrentUser() {
        return (User) getSubject().getPrincipal();
    }

    protected Session getSession() {
        return getSubject().getSession();
    }

    protected Session getSession(Boolean flag) {
        return getSubject().getSession(flag);
    }

    protected void login(AuthenticationToken token) {
        getSubject().login(token);
    }
//
//    protected Map<String, Object> getDataTable(PageHelper<?> pageInfo) {
//        return getDataTable(pageInfo, 2);
//    }
//
//    protected Map<String, Object> getDataTable(IPage<?> pageInfo, int dataMapInitialCapacity) {
//        Map<String, Object> data = new HashMap<>(dataMapInitialCapacity);
//        data.put("rows", pageInfo.getRecords());
//        data.put("total", pageInfo.getTotal());
//        return data;
//    }

}
