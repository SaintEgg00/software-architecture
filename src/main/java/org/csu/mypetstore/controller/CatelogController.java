package org.csu.mypetstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/catelog")
public class CatelogController {
    @GetMapping("/view")
    public String view(){
        return "catelog/main";
    }
}
