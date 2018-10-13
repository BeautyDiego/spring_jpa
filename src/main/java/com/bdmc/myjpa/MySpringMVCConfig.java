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
import io.jsonwebtoken.Claims;

import com.bdmc.myjpa.Utils.*;

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
            System.out.print("prehandle");
            /*
             * Object user = request.getSession().getAttribute("user"); if (user == null) {
             * try { response.sendRedirect("/login"); } catch (IOException e) {
             * e.printStackTrace(); } return false;
             * 
             * } return true;
             */
            boolean handleResult = false;

            String token = request.getHeader("token");
            if (token == null || token.isEmpty()) {
                PrintWriter writer = null;
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html; charset=utf-8");
                response.setStatus(response.SC_UNAUTHORIZED);
                
                try {
                    writer = response.getWriter();
                    String error = "token信息有误";
                    Msg m = ResultUtil.error(-1, error);
                    writer.print(m.toString());
                    return false;

                } catch (IOException e) {
                    logger.error("response error", e);
                } finally {
                    if (writer != null)
                        writer.close();
                }
            }

            boolean c = WebTokenUtil.validateJWT(token);
            return true;
        }

        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                ModelAndView modelAndView) {
            // controller方法处理完毕后，调用此方法
        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                Exception ex) {
            // 页面渲染完毕后调用此方法
        }

    }

}