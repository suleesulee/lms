package com.sulee.lms.components;

import com.sulee.lms.admin.dto.EmailTemplateDto;
import com.sulee.lms.email.entity.EmailTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@RequiredArgsConstructor
@Component
public class MailComponents {

    private final JavaMailSender javaMailSender;

    boolean result = false;

    public boolean sendMail(String mail, EmailTemplateDto regEmail){
        MimeMessagePreparator msg = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                mimeMessageHelper.setTo(mail);
                mimeMessageHelper.setSubject(regEmail.emailTitle);
                mimeMessageHelper.setText(regEmail.emailContent, true);
            }
        };

        try {
            javaMailSender.send(msg);
            result = true;
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        return result;
    }
}
