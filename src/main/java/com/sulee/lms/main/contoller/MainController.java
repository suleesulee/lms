package com.sulee.lms.main.contoller;


import com.sulee.lms.components.MailComponents;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RequiredArgsConstructor
@Controller
public class MainController {
    private final MailComponents mailComponents;


    @GetMapping("/")
    public String index() {
        return "index";
    }

    @ResponseBody
    @RequestMapping("/hello")
    public void hello(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("test/html;charset=UTF-8");
        PrintWriter printWriter = response.getWriter();

        String msg = "<html>" + "<meta charset=\"UTF-8\">" + "<head>" + "</head>" + "</html>";

        printWriter.write(msg);
        printWriter.close();

    }


}
