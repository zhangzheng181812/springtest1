
package com.zz;

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
}

