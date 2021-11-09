package com.sulee.lms.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailTemplateDto {
    public String emailTemplateKey;
    public String emailTitle;
    public String emailContent;
}
