package com.sulee.lms.admin.controller;

import com.sulee.lms.admin.dto.EmailTemplateDto;
import com.sulee.lms.email.entity.EmailTemplate;
import com.sulee.lms.email.model.EmailTemplateInput;
import com.sulee.lms.email.service.EmailTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminEmailTemplateController {

    private final EmailTemplateService emailTemplateService;

    @GetMapping("/admin/email-template/list.do")
    public String list(Model model){
        List<EmailTemplateDto> email = emailTemplateService.list();

        model.addAttribute("list", email);
        return "admin/email_template/list";
    }

    @GetMapping("/admin/email-template/create.do")
    public String create(){
        return "admin/email_template/create";
    }

    @PostMapping("/admin/email-template/create.do")
    public String createSubmit(EmailTemplateInput parameter, Model model){
        boolean result = emailTemplateService.emailTemplateCreate(parameter);
        model.addAttribute("result", result);

        return "admin/email_template/create-complete";
    }

    @GetMapping("/admin/email-template/update.do")
    public String update(Model model, HttpServletRequest request){
        String templateKey = request.getParameter("emailTemplateKey");
        //System.out.println(templateKey);

        EmailTemplateDto emailTemplateDto = emailTemplateService.getEmailTemplateDto(templateKey);

        //가져온 정보로 모델에 보냄
        //System.out.println(emailTemplateDto.getEmailTemplateKey());
        //System.out.println(emailTemplateDto.getEmailTitle());
        //System.out.println(emailTemplateDto.getEmailContent());

        model.addAttribute("emailTemplateKey", emailTemplateDto.getEmailTemplateKey());
        model.addAttribute("emailTitle", emailTemplateDto.getEmailTitle());
        model.addAttribute("emailContent", emailTemplateDto.getEmailContent());

        return "admin/email_template/update";
    }

    @PostMapping("/admin/email-template/update.do")
    public String updateSubmit(Model model, EmailTemplateInput parameter){

        boolean result = emailTemplateService.emailTemplateUpdate(parameter);

        model.addAttribute("result", result);

        return "admin/email_template/update-complete";
    }
}
