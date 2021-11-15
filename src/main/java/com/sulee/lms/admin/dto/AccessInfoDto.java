package com.sulee.lms.admin.dto;

import com.sulee.lms.admin.entity.Category;
import com.sulee.lms.member.entity.AccessInfo;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessInfoDto {

    private String userId;

    LocalDateTime loginDt;
    String userIp;
    String userAgent;

    long totalCount;
    long seq;


    public static AccessInfoDto of(AccessInfo accessInfo){
        return AccessInfoDto.builder()
                .userId(accessInfo.getUserId())
                .loginDt(accessInfo.getLoginDt())
                .userAgent(accessInfo.getUserAgent())
                .userIp(accessInfo.getUserIp())
                .build();
    }
}
