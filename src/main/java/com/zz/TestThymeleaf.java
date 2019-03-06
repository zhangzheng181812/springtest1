package com.zz;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping({"/testThymeleaf"})
public class TestThymeleaf {
    @RequestMapping("/helloHtml")
    public ModelAndView helloHtml(ModelAndView modelAndView ){
        modelAndView.addObject("hello","from TemplateController.helloHtml");
        modelAndView.setViewName("Hello");
        return modelAndView;
    }
}
