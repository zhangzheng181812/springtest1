package com.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by admin on 2018/8/23.
 */
public class Demo {



    private long id;

    @NotEmpty(message="姓名不能为空")
    @NotNull
    private String name;

    @NotEmpty(message="密码不能为空")
    @Length(min=6,message="密码长度不能小于6位")
    @JsonProperty("pass")
    private String password;



    public long getId() {
        return id;

    }



    public void setId(long id) {

        this.id = id;

    }



    public String getName() {

        return name;

    }



    public void setName(String name) {

        this.name = name;

    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override

    public String toString() {
        return "Demo [id=" + id + ", name=" + name + ", password=" + password + "]";

    }

}
