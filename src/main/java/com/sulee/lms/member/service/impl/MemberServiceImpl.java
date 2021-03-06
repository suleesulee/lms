package com.sulee.lms.member.service.impl;

import com.sulee.lms.admin.dto.AccessInfoDto;
import com.sulee.lms.admin.dto.EmailTemplateDto;
import com.sulee.lms.admin.dto.MemberDto;
import com.sulee.lms.admin.mapper.AccessInfoMapper;
import com.sulee.lms.admin.mapper.MemberMapper;
import com.sulee.lms.admin.model.MemberParam;
import com.sulee.lms.components.MailComponents;
import com.sulee.lms.course.model.ServiceResult;
import com.sulee.lms.email.service.EmailTemplateService;
import com.sulee.lms.member.entity.AccessInfo;
import com.sulee.lms.member.entity.Member;
import com.sulee.lms.member.entity.MemberCode;
import com.sulee.lms.member.exception.MemberNotEmailAuthException;
import com.sulee.lms.member.exception.MemberStopUser;
import com.sulee.lms.member.model.MemberInput;
import com.sulee.lms.member.model.ResetPasswordInput;
import com.sulee.lms.member.repository.AccessInfoRepository;
import com.sulee.lms.member.repository.MemberRepository;
import com.sulee.lms.member.service.MemberService;
import com.sulee.lms.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final AccessInfoRepository accessInfoRepository;
    private final MailComponents mailComponents;

    private final MemberMapper memberMapper;
    private final AccessInfoMapper accessInfoMapper;

    private final EmailTemplateService emailTemplateService;

    @Override
    public boolean register(MemberInput parameter) {
        Optional<Member> optionalMember = memberRepository.findById(parameter.getUserId());
        if (optionalMember.isPresent()){
            //?????? userId??? ???????????? ????????? ??????
            return false;
        }

        String encPassword = BCrypt.hashpw(parameter.getPassword(), BCrypt.gensalt());

        String uuid = UUID.randomUUID().toString();

        Member member = Member.builder()
                .userId(parameter.getUserId())
                .userName(parameter.getUserName())
                .phone(parameter.getPhone())
                .password(encPassword)
                .regDt(LocalDateTime.now())
                .emailAuthYn(false)
                .emailAuthKey(uuid)
                .userStatus(Member.MEMBER_STATUS_REQ)
                .build();
        /*
        Member member = new Member();
        member.setUserId(parameter.getUserId());
        member.setUserName(parameter.getUserName());
        member.setPhone(parameter.getPhone());
        member.setPassword(parameter.getPassword());
        member.setRegDt(LocalDateTime.now());
        member.setEmailAuthYn(false);
        member.setEmailAuthKey(uuid);
        */

        memberRepository.save(member);

        String email = parameter.getUserId();

        EmailTemplateDto regEmail = emailTemplateService.getEmailTemplateDto("MEMBER_REGISTER");

        //String subject = "lms ????????? ????????? ??????????????????.";
        //String text = "<p>lms ???????????? ????????? ??????????????????.</p><p>?????? ????????? ???????????? ????????? ???????????????</p>"
        //        + "<div><a href='http://localhost:8080/member/email-auth?id=" + uuid + "'> ?????? ??????</a></div>";


        mailComponents.sendMail(email, regEmail);

        return true;
    }

    @Override
    public boolean emailAuth(String uuid) {
        Optional<Member> optionalMember = memberRepository.findByEmailAuthKey(uuid);
        if(!optionalMember.isPresent()){
            return false;
        }
        Member member = optionalMember.get();

        if(member.isEmailAuthYn()){
            return false;
        }

        member.setEmailAuthYn(true);
        member.setEmailAuthDt(LocalDateTime.now());
        member.setUserStatus(Member.MEMBER_STATUS_ING);
        memberRepository.save(member);

        return true;
    }

    @Override
    public boolean sendResetPassword(ResetPasswordInput parameter) {
        Optional<Member> optionalMember = memberRepository.findByUserIdAndUserName(parameter.getUserId(), parameter.getUserName());
        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("?????? ????????? ???????????? ????????????.");
        }
        Member member = optionalMember.get();
        String uuid = UUID.randomUUID().toString();

        member.setResetPasswordKey(uuid);
        member.setResetPasswordDt(LocalDateTime.now().plusDays(1));
        memberRepository.save(member);

        EmailTemplateDto resetPasswordEmail = emailTemplateService.getEmailTemplateDto("RESET_PASSWORD");


        String email = parameter.getUserId();
        String subject = "lms ????????? ???????????? ?????????";
        String text = "<p>lms ????????? ???????????? ????????? ?????? ?????????.</p><p>?????? ????????? ??????????????? ??????????????? ????????? ????????????.</p>"
                + "<div><a href='http://localhost:8080/member/reset/password?id=" + uuid + "'> ???????????? ????????? ??????</a></div>";
        mailComponents.sendMail(email, resetPasswordEmail);

        return true;
    }

    @Override
    public boolean resetPassword(String uuid, String password) {
        Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(uuid);
        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("?????? ????????? ???????????? ????????????.");
        }

        Member member = optionalMember.get();

        //????????? ????????? ???????????? ??????
        if (member.getResetPasswordDt() == null){
            throw new RuntimeException("????????? ????????? ????????????.");
        }

        if(member.getResetPasswordDt().isBefore((LocalDateTime.now()))){
            throw new RuntimeException("????????? ????????? ????????????.");
        }

        String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        member.setPassword(encPassword);
        member.setResetPasswordKey("");
        member.setResetPasswordDt(null);
        memberRepository.save(member);

        return true;
    }

    @Override
    public boolean checkResetPassword(String uuid) {
        Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(uuid);
        if(!optionalMember.isPresent()){
            return false;
        }
        Member member = optionalMember.get();

        //????????? ????????? ???????????? ??????
        if (member.getResetPasswordDt() == null){
            throw new RuntimeException("????????? ????????? ????????????.");
        }

        if(member.getResetPasswordDt().isBefore((LocalDateTime.now()))){
            throw new RuntimeException("????????? ????????? ????????????.");
        }

        return true;
    }

    @Override
    public List<MemberDto> list(MemberParam parameter) {

        long totalCount = memberMapper.selectListCount(parameter);

//        System.out.println(parameter.getUserId());
//        System.out.println(lastLoginDt);
        List<MemberDto> list = memberMapper.selectList(parameter);
        if (!CollectionUtils.isEmpty(list)){
            int i = 0;
            for(MemberDto x : list){
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - parameter.getPageStart() - i);
                i++;
                LocalDateTime lastLoginDt = accessInfoMapper.selectLastAccessTime(x.getUserId());
                x.setLastLoginDt(lastLoginDt);
            }
        }

        return list;
    }

    @Override
    public MemberDto detail(String userId) {
        Optional<Member> optionalMember = memberRepository.findById(userId);
        if(!optionalMember.isPresent()){
            return null;
        }
        Member member = optionalMember.get();
        return MemberDto.of(member);
    }

    @Override
    public boolean updateStatus(String userId, String userStatus) {

        Optional<Member> optionalMember = memberRepository.findById(userId);
        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("?????? ????????? ???????????? ????????????.");
        }

        Member member = optionalMember.get();

        member.setUserStatus(userStatus);
        memberRepository.save(member);

        return true;
    }

    @Override
    public boolean updatePassword(String userId, String password) {
        Optional<Member> optionalMember = memberRepository.findById(userId);
        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("?????? ????????? ???????????? ????????????.");
        }

        Member member = optionalMember.get();

        String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        member.setPassword(encPassword);
        memberRepository.save(member);

        return true;
    }

    @Override
    public boolean accessInfo(String userId, String userAgent, String userIp) {
        AccessInfo accessInfo = AccessInfo.builder()
                .userId(userId)
                .loginDt(LocalDateTime.now())
                .userAgent(userAgent)
                .userIp(userIp)
                .build();

        accessInfoRepository.save(accessInfo);

        return true;
    }

    @Override
    public List<AccessInfoDto> getAccessInfo(String userId) {

        long totalCount = accessInfoMapper.selectListCount(userId);

        List<AccessInfoDto> list = accessInfoMapper.selectList(userId);
        if (!CollectionUtils.isEmpty(list)){
            int i = 1;
            for(AccessInfoDto x : list){
                x.setTotalCount(totalCount);
                x.setSeq(i);
                i++;
            }
        }

        return list;
    }

    @Override
    public ServiceResult updateMemberPassword(MemberInput parameter) {
        String userId = parameter.getUserId();
        Optional<Member> optionalMember = memberRepository.findById(userId);
        if(!optionalMember.isPresent()){
            return new ServiceResult(false, "?????? ????????? ???????????? ????????????.");
        }

        Member member = optionalMember.get();

        if(!PasswordUtils.equals(parameter.getPassword(), BCrypt.gensalt())){
            return new ServiceResult(false,"??????????????? ???????????? ????????????.");
        }

        String encPassword = PasswordUtils.encPassword(parameter.getNewPassword());
        member.setPassword(encPassword);
        memberRepository.save(member);

        return new ServiceResult(true);
    }

    @Override
    public ServiceResult updateMember(MemberInput parameter) {
        String userId = parameter.getUserId();
        Optional<Member> optionalMember = memberRepository.findById(userId);
        if(!optionalMember.isPresent()){
            return new ServiceResult(false, "?????? ????????? ???????????? ????????????.");
        }

        Member member = optionalMember.get();
        member.setPhone(parameter.getPhone());
        member.setZipcode(parameter.getZipcode());
        member.setAddr(parameter.getAddr());
        member.setAddrDetail(parameter.getAddrDetail());
        member.setUdtDt(LocalDateTime.now());
        memberRepository.save(member);


        return new ServiceResult(true);
    }

    @Override
    public ServiceResult withdraw(String userId, String password) {
        Optional<Member> optionalMember = memberRepository.findById(userId);
        if(!optionalMember.isPresent()){
            return new ServiceResult(false, "?????? ????????? ???????????? ????????????.");
        }

        Member member = optionalMember.get();

        if(!PasswordUtils.equals(password, member.getPassword())){
            return new ServiceResult(false, "??????????????? ???????????? ????????????.");
        }

        member.setUserName("????????????");
        member.setPhone("");
        member.setPassword("");
        member.setRegDt(null);
        member.setUdtDt(null);
        member.setEmailAuthDt(null);
        member.setEmailAuthYn(false);
        member.setEmailAuthKey("");
        member.setResetPasswordKey("");
        member.setResetPasswordDt(null);
        member.setUserStatus(MemberCode.MEMBER_STATUS_WITHDRAW);

        member.setZipcode("");
        member.setAddr("");
        member.setAddrDetail("");
        memberRepository.save(member);


        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> optionalMember = memberRepository.findById(username);
        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("?????? ????????? ???????????? ????????????.");
        }

        Member member = optionalMember.get();


        if(Member.MEMBER_STATUS_REQ.equals(member.getUserStatus())){
            throw new MemberNotEmailAuthException("????????? ????????? ????????? ????????? ????????????");
        }

        if(Member.MEMBER_STATUS_STOP.equals(member.getUserStatus())){
            throw new MemberStopUser("????????? ?????? ?????????.");
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        if(member.isAdminYn()){
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return new User(member.getUserId(), member.getPassword(), grantedAuthorities);
    }
}
