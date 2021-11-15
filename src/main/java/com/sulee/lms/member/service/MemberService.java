package com.sulee.lms.member.service;

import com.sulee.lms.admin.dto.AccessInfoDto;
import com.sulee.lms.admin.dto.MemberDto;
import com.sulee.lms.admin.model.MemberParam;
import com.sulee.lms.member.model.MemberInput;
import com.sulee.lms.member.model.ResetPasswordInput;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface MemberService extends UserDetailsService {
    boolean register(MemberInput parameter);

    //uuid에 해당하는 계정을 활성화 함.
    boolean emailAuth(String uuid);

    //입력한 이메일로 비밀번호 초기화 정보를 전송
    boolean sendResetPassword(ResetPasswordInput parameter);

    //입력한 uuid에 대해서 password로 초기화 함
    boolean resetPassword(String uuid, String password);

    //입력받은 uuid 값이 유효한지 확인
    boolean checkResetPassword(String uuid);
    
    //회원 목록 리턴(관리자에서만 사용 가능)
    List<MemberDto> list(MemberParam parameter);

    //회원 상세 정보
    MemberDto detail(String userId);

    //회원 상태 변경
    boolean updateStatus(String userId, String userStatus);

    //회원 비밀번호 초기화
    boolean updatePassword(String userId, String password);

    //접속 정보 저장
    boolean accessInfo(String userId, String userAgent, String userIp);

    //Id를 기반으로 접속 정보를 가져옴
    List<AccessInfoDto> getAccessInfo(String userId);
}
