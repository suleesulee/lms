package com.sulee.lms.member.controller;

import com.sulee.lms.admin.dto.MemberDto;
import com.sulee.lms.course.dto.TakeCourseDto;
import com.sulee.lms.course.model.ServiceResult;
import com.sulee.lms.course.service.TakeCourseService;
import com.sulee.lms.member.entity.Member;
import com.sulee.lms.member.model.MemberInput;
import com.sulee.lms.member.model.ResetPasswordInput;
import com.sulee.lms.member.repository.MemberRepository;
import com.sulee.lms.member.service.MemberService;
import com.sulee.lms.util.RequestUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MemberController {
    //member/register

    private final MemberService memberService;
    private final TakeCourseService takeCourseService;

    @RequestMapping(value = "/member/login")
    public String login(){
        return "member/login";
    }

    @GetMapping("/member/loginSuccess")
    public String loginSuccess(@AuthenticationPrincipal User user, HttpServletRequest request){
        String userId = user.getUsername();
        String userAgent = RequestUtils.getUserAgent(request);
        String clientIp = RequestUtils.getClientIP(request);

        memberService.accessInfo(userId, userAgent, clientIp);

        return "member/loginSuccess";
    }

    @GetMapping("/member/find-password")
    public String findPassword(){
        return "member/find_password";
    }

    @PostMapping("/member/find-password")
    public String findPasswordSubmit(Model model, ResetPasswordInput parameter){

        boolean result = false;

        try {
            result = memberService.sendResetPassword(parameter);

        } catch(Exception e){

        }
        model.addAttribute("result", result);

        return "member/find_password_result";
    }

    @GetMapping(value = "/member/register")
    public String register(){
        return "member/register";
    }




    @PostMapping(value="/member/register")
    public String registerSubmit(Model model, HttpServletRequest request, MemberInput parameter){

        /*String userId = request.getParameter("userId");
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");

        System.out.println(userId);
        System.out.println(userName);
        System.out.println(password);
        System.out.println(phone);*/

        boolean result = memberService.register(parameter);
        model.addAttribute("result", result);

        return "member/register-complete";
    }

    @GetMapping("/member/email-auth")
    public String emailAuth(Model model, HttpServletRequest request) {
        String uuid = request.getParameter("id");
        System.out.println(uuid);

        boolean result = memberService.emailAuth(uuid);
        model.addAttribute("result", result);



        return "member/email-auth";
    }

    @GetMapping("/member/reset/password")
    public String resetPassword(Model model, HttpServletRequest request){
        String uuid = request.getParameter("id");

        boolean result = memberService.checkResetPassword(uuid);
        model.addAttribute("result", result);

        return "member/reset_password";
    }

    @PostMapping("/member/reset/password")
    public String resetPasswordSubmit(Model model, ResetPasswordInput parameter){
        boolean result = false;

        try {
            result = memberService.resetPassword(parameter.getId(), parameter.getPassword());
        } catch(Exception e){

        }

        model.addAttribute("result", result);

        return "member/reset_password_result";
    }

    @GetMapping("/member/info")
    public String memberInfo(Model model, Principal principal){
        String userId = principal.getName();
        MemberDto detail = memberService.detail(userId);

        model.addAttribute("detail", detail);

        return "member/info";
    }

    @PostMapping("/member/info")
    public String memberInfoSubmit(Model model, MemberInput parameter, Principal principal){
        String userId = principal.getName();
        parameter.setUserId(userId);

        ServiceResult result = memberService.updateMember(parameter);

        if(!result.isResult()){
            model.addAttribute("message", result.getMessage());
            return "common/error";
        }

        return "redirect:/member/info";
    }


    @GetMapping("/member/password")
    public String memberPassword(Model model, Principal principal){
        return "member/password";
    }

    @PostMapping("/member/password")
    public String memberPasswordSubmit(Model model, MemberInput parameter, Principal principal){
        String userId = principal.getName();
        parameter.setUserId(userId);

        ServiceResult result = memberService.updateMemberPassword(parameter);
        if(!result.isResult()){
            model.addAttribute("message", result.getMessage());
            return "common/error";
        }

        return "redirect:/member/info";
    }


    @GetMapping("/member/takecourse")
    public String memberTakeCourse(Model model, Principal principal){
        String userId = principal.getName();
        MemberDto detail = memberService.detail(userId);

        List<TakeCourseDto> list = takeCourseService.myCourse(userId);

        model.addAttribute("list", list);

        return "member/takecourse";
    }

    @GetMapping("/member/withdraw")
    public String memberWithdraw(Model model){
        return "member/withdraw";
    }

    @PostMapping("/member/withdraw")
    public String memberWithdrawSubmit(Model model, MemberInput parameter, Principal principal){
        String userId = principal.getName();
        MemberDto detail = memberService.detail(userId);

        //if(parameter.getPassword())

        return "member/withdraw";
    }
}
