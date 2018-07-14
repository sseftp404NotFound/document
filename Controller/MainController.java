package com.slsnotfound.doucument.doucument.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
    @RequestMapping(path = "/main", method = RequestMethod.GET)
    public String login() {
        return "main";
    }
}
