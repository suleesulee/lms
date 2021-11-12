package com.sulee.lms.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Member implements MemberCode {
    @Id
    @Column(name = "user_id", nullable = false)
    private String userId;
    private String userName;
    private String phone;
    private String password;
    private LocalDateTime regDt;

    private boolean emailAuthYn;
    private LocalDateTime emailAuthDt;
    private String emailAuthKey;

    private String resetPasswordKey;
    private LocalDateTime resetPasswordDt;
    
    // 관리자여부 지정
    // 회원에 따른 ROLE이 있는가
    // 준회원, 정회원, 특별회원, 관리자

    private boolean adminYn;
    //이용가능상태, 정지상태
    private String userStatus;

}
