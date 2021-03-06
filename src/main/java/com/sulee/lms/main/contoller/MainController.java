package com.sulee.lms.main.contoller;


import com.sulee.lms.admin.dto.BannerDto;
import com.sulee.lms.admin.service.BannerService;
import com.sulee.lms.components.MailComponents;
import com.sulee.lms.util.RequestUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.util.RequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MainController {
    private final MailComponents mailComponents;
    private final BannerService bannerService;


    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        List<BannerDto> bannerDtoList = bannerService.frontList();

        model.addAttribute("bannerDtoList", bannerDtoList);


        return "index";
    }

    @RequestMapping("/error/denied")
    public String errorDenied(){
        return "error/denied";
    }

}
