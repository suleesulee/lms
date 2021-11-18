package com.sulee.lms.member.controller;

import com.sulee.lms.common.model.ResponseResult;
import com.sulee.lms.course.dto.TakeCourseDto;
import com.sulee.lms.course.model.ServiceResult;
import com.sulee.lms.course.model.TakeCourseInput;
import com.sulee.lms.course.service.TakeCourseService;
import com.sulee.lms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ApiMemberController {

    private final TakeCourseService takeCourseService;

    @PostMapping("/api/member/course/cancel.api")
    public ResponseEntity<?> cancelCourse(Model model, @RequestBody TakeCourseInput parameter, Principal principal) {
        //내 강좌인지 확인
        String userId = principal.getName();

        System.out.println(parameter.getTakeCourseId());

        TakeCourseDto detail = takeCourseService.detail(parameter.getTakeCourseId());
        if(detail == null){
            ResponseResult responseResult = new ResponseResult(false, "수상신청정보가 존재하지 않습니다.");
            return ResponseEntity.ok().body(responseResult);
        }

        if(userId == null || !userId.equals(detail.getUserId())){
            //내 수강신청 정보가 아닌것.
            ResponseResult responseResult = new ResponseResult(false, "본인의 수강 신청 정보만 취소할 수 있습니다.");
            return ResponseEntity.ok().body(responseResult);
        }

        ServiceResult result = takeCourseService.cancel(parameter.getTakeCourseId());
        if(!result.isResult()) {
            //내 수강신청 정보가 아닌것.
            ResponseResult responseResult = new ResponseResult(false, result.getMessage());
            return ResponseEntity.ok().body(responseResult);
        }

        ResponseResult responseResult = new ResponseResult(true);
        return ResponseEntity.ok().body(responseResult);
    }
}
