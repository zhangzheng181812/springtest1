package com.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collection;

public class ValidateUtil {

    private static final Logger log = LoggerFactory.getLogger(ValidateUtil.class);

    /**
     * 验证成功标志
     */
    public final static String  SUCCESS = "0000";

    /**
     * 实体类验证
     * @param obj  带有validation注解的实体类。
     * @return  true 成功， false失败
     */
    public static boolean validate(Object obj) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Collection<ConstraintViolation<Object>> collection = validator.validate(obj);
        if(collection.size()!=0){
            ConstraintViolation<Object> aa= collection.iterator().next();
            String errorField = aa.getPropertyPath().toString();
            String msg = aa.getMessage();

            StringBuffer sf = new StringBuffer();
            for (ConstraintViolation<Object> violation : collection) {
                sf.append("[errorField:" + violation.getPropertyPath().toString());
                sf.append(",errorMsg:" + violation.getMessage());
                sf.append("]");
            }
            log.error("数据验证失败{}",sf.toString());
            return false;
        }
        return true;
    }

    /**
     * 实体类验证
     * @param obj  带有validation注解的实体类。
     * @return  0000 成功， 其他失败，失败信息
     */
    public static String validateWithErrorMsg(Object obj) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Collection<ConstraintViolation<Object>> collection = validator.validate(obj);
        if (collection.size()==0){
            return SUCCESS;
        }
        else {
            StringBuffer sf = new StringBuffer();
            for (ConstraintViolation<Object> violation : collection) {
                sf.append("[errorField:" + violation.getPropertyPath().toString());
                sf.append(",errorMsg:" + violation.getMessage());
                sf.append("]");
            }
            return sf.toString();
//            ConstraintViolation<Object> aa= c ollection.iterator().next();
//            String column = aa.getPropertyPath().toString();
//            String msg = aa.getMessage();
//            return "column:"+column+",description:"+msg;

        }
    }

}
