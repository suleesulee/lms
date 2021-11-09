package com.sulee.lms.email.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailTemplate {
    @Id
    @Column(name = "email_template_key", nullable = false)
    public String emailTemplateKey;
    public String emailTitle;
    public String emailContent;
}

