package com.sulee.lms.email.repository;


import com.sulee.lms.email.entity.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, String> {
}
