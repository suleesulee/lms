package com.sulee.lms.email.service;


import com.sulee.lms.admin.dto.EmailTemplateDto;
import com.sulee.lms.email.entity.EmailTemplate;
import com.sulee.lms.email.model.EmailTemplateInput;

import java.util.List;

public interface EmailTemplateService {
    //이메일 템플릿 등록
    boolean emailTemplateCreate(EmailTemplateInput parameter);

    //이메일 템플릿 수정
    boolean emailTemplateUpdate(EmailTemplateInput parameter);

    //이메일템플릿 목록 리턴
    List<EmailTemplateDto> list();

    //템플릿 키로 이메일 템플릿 정보 가져오기
    public EmailTemplateDto getEmailTemplateDto(String templateKey);


}
