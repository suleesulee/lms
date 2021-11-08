package com.sulee.lms.admin;

import com.sulee.lms.admin.dto.EmailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminEmailController {

    @GetMapping("/admin/email/list.do")
    public String list(Model model){
        List<EmailDto> emailtemplate =

        return "admin/email/list";
    }
}
