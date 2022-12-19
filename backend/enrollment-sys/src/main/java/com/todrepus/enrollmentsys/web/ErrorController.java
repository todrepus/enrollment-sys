package com.todrepus.enrollmentsys.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class ErrorController {
    @GetMapping("/error")
    public String redirectRoot(){
        return "index.html";
    }

    public String getErrorPath(){
        return "/error";
    }
}
