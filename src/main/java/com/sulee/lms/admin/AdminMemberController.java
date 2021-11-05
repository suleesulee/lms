package com.sulee.lms.admin;


import com.sulee.lms.admin.dto.MemberDto;
import com.sulee.lms.member.entity.Member;
import com.sulee.lms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminMemberController {

    private final MemberService memberService;

    @GetMapping("/admin/member/list.do")
    public String list(Model model){

        List<MemberDto> members = memberService.list();
        model.addAttribute("list", members);

        return "admin/member/list";
    }






}
