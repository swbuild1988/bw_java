package com.bandweaver.tunnel.controller.common;


import com.bandweaver.tunnel.common.platform.log.LogUtil;
import com.bandweaver.tunnel.common.platform.util.FileUtil;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HtmlController {

    Logger log = Logger.getLogger(HtmlController.class);

    @RequestMapping("/{page}")
    public String GetHtml(@PathVariable("page") String page){
       // 判断html文件是否存在，不存在就上index
       return FileUtil.isExit(FileUtil.htmlDir() + "/" + page + ".html") ? page : "index";
    }
}
