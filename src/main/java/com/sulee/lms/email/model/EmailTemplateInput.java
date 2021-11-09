package com.sulee.lms.email.model;

import lombok.Data;

@Data
public class EmailTemplateInput {
    private String emailTemplateKey;
    private String emailTitle;
    private String emailContent;
    private String updateTemp;
}
