package com.sulee.lms.admin.mapper;

import com.sulee.lms.admin.dto.EmailTemplateDto;
import com.sulee.lms.admin.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmailTemplateMapper {
    List<EmailTemplateDto> selectList();
    EmailTemplateDto selectTemplateKey();
}
