
package com.zz;

import com.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/test"})
public class TestController {
    public TestController() {
    }

    @RequestMapping({"/sayHello"})
    public String SayHello() {
        return "hello ~~";
    }

    @Autowired
    private AsyncService asyncService ;

    @RequestMapping("/testAsync")
    public void testAsync(){
        int i = 0;
        for (int j = 0; j<10 ;j=j+1){
            asyncService.doAsync(i);
        }
        System.out.println("123");
    }

    @RequestMapping({"/sayHello2"})
    @ResponseStatus(HttpStatus.CREATED)
    //HttpStatus.CREATED 常量值为201，标识 新增资源成功（insert）
    public String SayHello2() {
        return "hello ~~";
    }
}

