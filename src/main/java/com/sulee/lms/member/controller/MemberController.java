package com.sulee.lms.member.controller;

import com.sulee.lms.member.entity.Member;
import com.sulee.lms.member.model.MemberInput;
import com.sulee.lms.member.model.ResetPasswordInput;
import com.sulee.lms.member.repository.MemberRepository;
import com.sulee.lms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Controller
public class MemberController {
    //member/register

    private final MemberService memberService;

    @RequestMapping(value = "/member/login")
    public String login(){
        return "member/login";
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

    @GetMapping("member/info")
    public String memberInfo(){
        return "member/info";
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
}
