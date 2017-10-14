package com.sun.framework.springmvc.support;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 基础controller
 * 
 * @author sunxiongwei
 * 
 */
public abstract class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    public Object result() {
        return this.result(null);
    }

    /**
     * 如果有callback参数，自动转为jsonp方式，约定
     * 
     * @param ob
     * @return
     */
    public Object result(Object ob) {
        ResultHolder rh = null;
        if (ob == null) {
            rh = new ResultHolder(null);
        } else if (ob instanceof ResultHolder) {
            rh = (ResultHolder) ob;
        } else {
            rh = new ResultHolder(ob);
        }
        /**
         * String jsonp = request.getParameter("callback"); if (jsonp != null) { MappingJacksonValue value = new
         * MappingJacksonValue(rh); value.setJsonpFunction(jsonp); return value; }
         **/
        return rh;
    }

    public Object result(boolean success) {
        ResultHolder rh = new ResultHolder();
        rh.setSuccess(success);
        return rh;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public String getRemoteAdrr() {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotBlank(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotBlank(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("Proxy-Client-IP");
        if (StringUtils.isNotBlank(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("WL-Proxy-Client-IP");
        if (StringUtils.isNotBlank(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();

    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object allException(Exception ex, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // ex.printStackTrace();
        // 设置状态500
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        if (ex instanceof SQLException) {
            request.setAttribute("EMSG", "SQLException");
        } else {
            request.setAttribute("EMSG", ex.getMessage());
        }
        return this.ajaxException(ex, request, response);
    }

    public Object ajaxException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        ResultHolder rh = new ResultHolder();
        rh.setSuccess(false);
        logger.warn(request.getPathInfo(), ex);
        rh.setMessage(ex.getMessage());
        return rh;
    }

    /**
     * 组合返回结果
     * 
     * @author sunxiongwei
     * 
     */
    public class ResultHolder {

        public ResultHolder() {
        }

        public ResultHolder(Object data) {
            this.data = data;
        }

        public ResultHolder(boolean success, String msg) {
            this.message = msg;
        }

        public ResultHolder(boolean success, String msg, Object data) {
            this.message = msg;
            this.data = data;
        }

        public ResultHolder(boolean success, String msg, Object data, boolean hitCache) {
            this.message = msg;
            this.data = data;
        }

        // 返回数据
        private Object data;
        // 错误信息
        private String message;
        private boolean success = true;

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        // 对于需要转换为Date类型的属性，使用DateEditor进行处理
        binder.registerCustomEditor(Date.class, new DateEditorSupport());
    }

}
