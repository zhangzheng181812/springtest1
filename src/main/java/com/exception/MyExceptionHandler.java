package com.exception;

import org.apache.logging.log4j.core.util.JsonUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

/**
 * Created by Administrator on 2018/7/25 0025.
 */

@ControllerAdvice
public class MyExceptionHandler {

    private static HttpHeaders responseHeaders;
    {
        responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=utf-8");
    }

    @ExceptionHandler(value = Exception.class)
    public void defaultErrorHandler(HttpServletRequest request,Exception e){
        e.printStackTrace();
        System.out.println("全局异常处理handler");
    }

    /**
     * Validate 参数 异常处理
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = BindException.class)
    public Object bindExceptionErrorHandler(BindException e) throws Exception {
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();
        String msg = error.getDefaultMessage();
        System.out.println("请求参数错误,column:"+field+",description:"+msg);
        return new ResponseEntity(
                "123123",
                responseHeaders , HttpStatus.BAD_REQUEST);
    }

    /**
     * Validate 参数 异常处理; 验证方法参数（单个验证）
     * 备注：在方法上添加@NotBlank等验证注解，验证不通过会抛此异常。
     *       在方法上添加注解必须要在类上添加注解：@Validated （org.springframework.validation.annotation.Validated）
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public Object constraintViolationExceptionErrorHandler(ConstraintViolationException e) throws Exception {
        System.out.println(e.getMessage());
        return new ResponseEntity(
                "1231",
                responseHeaders , HttpStatus.BAD_REQUEST);
    }

}
