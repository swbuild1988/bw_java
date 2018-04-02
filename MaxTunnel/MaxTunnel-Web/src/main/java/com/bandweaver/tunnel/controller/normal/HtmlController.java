package com.bandweaver.tunnel.controller.normal;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HtmlController {

    @RequestMapping("/{page}")
    public String GetHtml(@PathVariable String page){
        return page;
    }
}
