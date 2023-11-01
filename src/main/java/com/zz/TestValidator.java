package com.zz;

import com.entity.Demo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.util.ValidateUtil;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2018/8/23.
 */
@Validated
@Controller
@RequestMapping("/testValidator")
public class TestValidator {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/test/{num}")
    public String redirect(@PathVariable(value = "num") Integer i){
        return "redirect:http://www.baidu.com?query="+(i+1);
    }

    //处理异常的两种方式  1 全局异常处理 bindException       2 参数组添加BindingResult参数

    /**
     * 表单绑定到 java bean 出错时，会抛出 BindException 异常
     * 将请求体解析并绑定到 java bean 时，如果出错，则抛出 MethodArgumentNotValidException 异常
     * 普通参数(非 java bean)校验出错时，会抛出 ConstraintViolationException 异常
     * ————————————————
     * 版权声明：本文为CSDN博主「小晨想好好学习」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
     * 原文链接：https://blog.csdn.net/hc1285653662/article/details/126839237
     *
     *
     * @param demo
     * @param result
     * @throws JsonProcessingException
     */
    @RequestMapping("/test")
    public void test(@Validated Demo demo,BindingResult result) throws JsonProcessingException {
        if(result.hasErrors()){

            List<ObjectError> list = result.getAllErrors();

            for(ObjectError error:list){

                System.out.println(error.getCode()+"---"+error.getArguments()+"---"+error.getDefaultMessage());

            }

        }
        System.out.println(objectMapper.writeValueAsString(demo));
    }

    /**
     * 直接在参数钱加限制，需要在类上添加@Validated主页，并处理异常  ConstraintViolationException
     * @param password
     */
    @RequestMapping("/test2")
    @ResponseBody
    public void test(@Length(min=6,message="密码长度不能小于6位") String password)  {

    }

    @RequestMapping("/test3")
    @ResponseBody
    public void test2() throws IOException {
        HashMap hashMap = new HashMap();
        hashMap.put("id","1");
        hashMap.put("name","2");
        hashMap.put("password","3");
        String s = objectMapper.writeValueAsString(hashMap);
        Demo demo = objectMapper.readValue(s, Demo.class);
        boolean validate = ValidateUtil.validate(demo);
        String s1 = ValidateUtil.validateWithErrorMsg(demo);
        System.out.println(s1);
    }
}
