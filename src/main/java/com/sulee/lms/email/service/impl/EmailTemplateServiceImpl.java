package com.sulee.lms.email.service.impl;

import com.sulee.lms.admin.dto.EmailTemplateDto;
import com.sulee.lms.email.entity.EmailTemplate;
import com.sulee.lms.email.model.EmailTemplateInput;
import com.sulee.lms.email.service.EmailTemplateService;
import com.sulee.lms.admin.mapper.EmailTemplateMapper;
import com.sulee.lms.email.repository.EmailTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailTemplateServiceImpl implements EmailTemplateService {

    private final EmailTemplateRepository emailTemplateRepository;
    private final EmailTemplateMapper emailTemplateMapper;

    @Override
    public boolean emailTemplateCreate(EmailTemplateInput parameter) {
        //동일한 템플릿 키가 있으면 생성할 수 없다.
        Optional<EmailTemplate> optionalEmailTemplate = emailTemplateRepository.findById(parameter.getEmailTemplateKey());
        if(optionalEmailTemplate.isPresent()){
            return false;
        }

        EmailTemplate template = EmailTemplate.builder()
                .emailTemplateKey(parameter.getEmailTemplateKey())
                .emailTitle(parameter.getEmailTitle())
                .emailContent(parameter.getEmailContent())
                .build();

        emailTemplateRepository.save(template);

        return true;
    }

    //동일한 템플릿 키가 있다면 바꿀 수 없다
    //템플릿 키가 없어도 수정할 수 없다.
    @Override
    public boolean emailTemplateUpdate(EmailTemplateInput parameter) {

        //System.out.println(parameter.getEmailTemplateKey());
        String[] splitTemplateKey = parameter.getEmailTemplateKey().split(",");

        //System.out.println(splitTemplateKey[0]);
        //System.out.println(splitTemplateKey[1]);

        Optional<EmailTemplate> optionalEmailTemplate = emailTemplateRepository.findById(splitTemplateKey[0]);
        Optional<EmailTemplate> newOptionalEmailTemplate = emailTemplateRepository.findById(splitTemplateKey[1]);
        EmailTemplate template = optionalEmailTemplate.get();
        EmailTemplate newTemplate = new EmailTemplate();
        // 템플릿키가 바뀌고 바뀐 키가 다른 키와 같은경우 -> 실패
        // 템플릿키가 안바뀌는 경우-> 성공
        // 템플릿 키가 바뀌고 같은 키가 없음 ->성공

        // 템플릿 키가 같음 (내용만 바뀜)
        if(splitTemplateKey[0].equals(splitTemplateKey[1])){
            template.setEmailTitle(parameter.getEmailTitle());
            template.setEmailContent(parameter.getEmailContent());
            emailTemplateRepository.save(template);
        //템플릿 키가 바뀜
        } else {
            //바꾸려하는 키가 이미 존재함
            if(newOptionalEmailTemplate.isPresent()){
                return false;

            } else {    //바꾸려하는 키가 없고 기존의 값을 지우고 덮어야함 (PK라서 JPA에서 대체못한다고 에러 나는것 같음)
                newTemplate.setEmailTemplateKey(splitTemplateKey[1]);
                newTemplate.setEmailTitle(parameter.getEmailTitle());
                newTemplate.setEmailContent(parameter.getEmailContent());
                emailTemplateRepository.delete(template);
                emailTemplateRepository.save(newTemplate);
            }
        }

        return true;
    }

    public EmailTemplateDto getEmailTemplateDto(String templateKey) {
        Optional<EmailTemplate> optionalEmailTemplate = emailTemplateRepository.findById(templateKey);

        EmailTemplate template = optionalEmailTemplate.get();

        EmailTemplateDto templateDto = new EmailTemplateDto();
        templateDto.setEmailTemplateKey(template.getEmailTemplateKey());
        templateDto.setEmailTitle(template.getEmailTitle());
        templateDto.setEmailContent(template.getEmailContent());

        return templateDto;
    }


        @Override
    public List<EmailTemplateDto> list() {
        List<EmailTemplateDto> list = emailTemplateMapper.selectList();

        return list;
    }
}
