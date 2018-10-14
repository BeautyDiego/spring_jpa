package com.bdmc.myjpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.bdmc.myjpa.Utils.*;
import com.alibaba.fastjson.JSON;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class MySpringMVCConfig implements WebMvcConfigurer {

    public static final Logger logger = LoggerFactory.getLogger((WebTokenUtil.class));

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        InterceptorRegistration interceptorRegistration = registry.addInterceptor(new SessionHandlerInterceptor());
        // interceptorRegistration.excludePathPatterns("/user/*");
        interceptorRegistration.excludePathPatterns("/static/**");
        interceptorRegistration.excludePathPatterns("/login");

        interceptorRegistration.addPathPatterns("/**");

    }

    private class SessionHandlerInterceptor implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
           
            boolean handleResult = false;

            String token = request.getHeader("token");
            if (token == null || token.isEmpty()) {//   fanhui 401
                PrintWriter writer = null;
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html; charset=utf-8");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                
                try {
                    writer = response.getWriter();
                    String error = "token信息有误";
                    Msg m = ResultUtil.error(-1, error);
                    writer.print(JSON.toJSONString(m));
                    return handleResult;
                } catch (IOException e) {
                    logger.error("response error", e);
                } finally {
                    if (writer != null)
                        writer.close();
                }
            }

            handleResult = WebTokenUtil.validateJWT(token);
            return handleResult;
        }

        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                ModelAndView modelAndView) {
            // controller方法处理完毕后，调用此方法
        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                Exception ex) {
            // 页面渲染完毕后调用此方法_
            //request.getAttribute(LOGGER_)
        }

    }

}