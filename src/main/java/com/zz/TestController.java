
package com.zz;

import com.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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
}

