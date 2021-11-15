package com.sulee.lms.main.contoller;


import com.sulee.lms.components.MailComponents;
import com.sulee.lms.util.RequestUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.util.RequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MainController {
    private final MailComponents mailComponents;


    @GetMapping("/")
    public String index(HttpServletRequest request) {

        String userAgent = RequestUtils.getUserAgent(request);
        String clientIp = RequestUtils.getClientIP(request);

        log.info(userAgent);
        log.info(clientIp);

        return "index";
    }

    @RequestMapping("/error/denied")
    public String errorDenied(){
        return "error/denied";
    }

}
