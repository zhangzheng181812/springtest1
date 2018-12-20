package com.zz;

import com.alibaba.druid.support.json.JSONUtils;
import com.dao.CodeMapper;
import com.entity.Code;
import com.github.pagehelper.PageHelper;
import com.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/12/20.
 */
@RestController
@RequestMapping
public class TestMybatisController {

    @Autowired
    CodeMapper codeMapper ;

    @RequestMapping(value = "findAll")
    public void findAll(){
        PageHelper.startPage(1, 2);
        List<Code> all = codeMapper.findAll();
        System.out.println(JsonUtils.toJsonNf(all));
        System.out.println(JsonUtils.toJson(all));
    }

    @RequestMapping(value = "findOne")
    public void findOne(){
        Code one = codeMapper.findOne("820", "2");
        System.out.println(JsonUtils.toJson(one));
    }

}
