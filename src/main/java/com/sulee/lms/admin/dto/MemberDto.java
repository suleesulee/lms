package com.sulee.lms.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MemberDto {
    String userId;
    String userName;
    String phone;
    String password;
    LocalDateTime regDt;
    boolean emailAuthYn;
    LocalDateTime emailAuthFt;
    String emailAuthKey;
    String resetPasswordKey;
    LocalDateTime resetPasswordListDt;
    boolean adminYn;
}
