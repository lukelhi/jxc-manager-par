package com.lzj.admin;

import com.lzj.admin.exceptions.ParamsException;
import com.lzj.admin.model.RespBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author 李
 * @version 1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {


    /*
     如果发生异常首先检查参数异常，如果不是参数异常，为普通 异常
     */
    @ExceptionHandler(ParamsException.class) //指定只能处理参数异常
    @ResponseBody
    public RespBean paramsExceptionHandler(ParamsException e){
        return  RespBean.error(e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RespBean exceptionHandler(Exception e){
        return  RespBean.error(e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String accessDeniedException(AccessDeniedException e){
        return "403";
    }
}
