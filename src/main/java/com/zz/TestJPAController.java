package com.zz;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/test"})
public class TestJPAController {
    @Autowired
    private CodeService codeService;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public TestJPAController() {
    }

    @RequestMapping({"/find"})
    public void find() throws JsonProcessingException {
//        System.out.println(objectMapper.writeValueAsString(this.codeService.findAll()));
//        System.out.println(objectMapper.writeValueAsString(this.codeService.findByCode("2")));
//        System.out.println(objectMapper.writeValueAsString(this.codeService.findOne(1)));
        System.out.println(objectMapper.writeValueAsString(this.codeService.findCodes1("1")));
    }

    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
}