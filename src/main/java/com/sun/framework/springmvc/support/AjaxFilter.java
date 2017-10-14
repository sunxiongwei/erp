package com.ris.framework.springmvc.support;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class AjaxFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        // ShiroHttpServletRequest
        HttpServletRequest hr = (HttpServletRequest) request;
        // X-Requested-With XMLHttpRequest
        // 未登录切是ajax时
        if (hr.getRemoteUser() == null && hr.getHeader("X-Requested-With") != null
                && "XMLHttpRequest".equals(hr.getHeader("X-Requested-With").toString())) {
            response.getWriter().print("{\"success\":false,\"logout\":true}");
            return;
        }
        chain.doFilter(request, response);

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

}
