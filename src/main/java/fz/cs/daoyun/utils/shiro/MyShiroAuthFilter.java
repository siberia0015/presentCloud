package fz.cs.daoyun.utils.shiro;


import org.apache.shiro.web.filter.authc.PassThruAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyShiroAuthFilter extends PassThruAuthenticationFilter {


    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        HttpServletRequest req = (HttpServletRequest)request;
        if(req.getMethod().equals(RequestMethod.OPTIONS.name())){
            return true;
        }
        return super.onPreHandle(request, response, mappedValue);
    }
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpResp = WebUtils.toHttp(response);
        HttpServletRequest httpReq = WebUtils.toHttp(request);

        /**系统重定向会默认把请求头清空，这里通过拦截器重新设置请求头，解决跨域问题*/
        httpResp.addHeader("Access-Control-Allow-Origin", httpReq.getHeader("Origin"));
        httpResp.addHeader("Access-Control-Allow-Headers", "*");
        httpResp.addHeader("Access-Control-Allow-Methods", "*");
        httpResp.addHeader("Access-Control-Allow-Credentials", "true");

        WebUtils.toHttp(response).sendRedirect(httpReq.getContextPath()+"/user/unauth");
        return false;
    }

}
