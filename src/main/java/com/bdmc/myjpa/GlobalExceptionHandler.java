package com.bdmc.myjpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * User: Qiu Nick
 * Date: 2017-07-26
 * Time: 15:49
 * Description: 全局异常捕获
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    // 日志记录工具
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public void handleGlobalException(HttpServletRequest req, Exception ex) {

        //打印堆栈日志到日志文件中
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        ex.printStackTrace(new java.io.PrintWriter(buf, true));
        String  expMessage = buf.toString();
        try {
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //记录到日志
        log.error("GlobalExceptionHandler,捕获异常:"+ ex.getMessage() + ";eString:" + expMessage);
    }
}