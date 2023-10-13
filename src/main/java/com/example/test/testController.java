package com.example.test;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {

    @Value("${test}")
    private String myVariable;

    @Value("${data}")
    private String data;
    @GetMapping("/test1")

    public String test() {
        return "test: "+myVariable + " data : "+data;
    }
}
