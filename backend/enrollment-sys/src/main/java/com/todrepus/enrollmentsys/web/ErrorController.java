package com.todrepus.enrollmentsys.web;

import com.todrepus.enrollmentsys.domain.course.Course;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class ErrorController {
    @GetMapping("/error")
    public String redirectRoot() {
        log.debug("/error");
        return "index.html";
    }

    public String getErrorPath(){
        return "/error";
    }
}
