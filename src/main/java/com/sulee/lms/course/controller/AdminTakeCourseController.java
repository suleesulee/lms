package com.sulee.lms.course.controller;


import com.sulee.lms.course.dto.CourseDto;
import com.sulee.lms.course.dto.TakeCourseDto;
import com.sulee.lms.course.model.ServiceResult;
import com.sulee.lms.course.model.TakeCourseParam;
import com.sulee.lms.course.service.CourseService;
import com.sulee.lms.course.service.TakeCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminTakeCourseController extends BaseController {

    private final TakeCourseService takeCourseService;
    private final CourseService courseService;

    @GetMapping("/admin/takecourse/list.do")
    public String list(Model model, TakeCourseParam parameter, BindingResult bindingResult){
        if(bindingResult.hasErrors()){

        }

        parameter.init();
        List<TakeCourseDto> list = takeCourseService.list(parameter);

        long totalCount = 0;

        if(!CollectionUtils.isEmpty(list)){
            totalCount = list.get(0).getTotalCount();
        }
        String queryString = parameter.getQueryString();
        String PagerHtml = super.getPagerHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);

        model.addAttribute("list", list);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", PagerHtml);


        List<CourseDto> courseList = courseService.listAll();
        model.addAttribute("courseList", courseList);


        return "admin/takecourse/list";
    }

    @PostMapping("/admin/takecourse/status.do")
    public String status(Model model, TakeCourseParam parameter){

        parameter.init();

        ServiceResult result = takeCourseService.updateStatus(parameter.getId(), parameter.getStatus());

        if(!result.isResult()){
            model.addAttribute("message", result.getMessage());
            return "common/error";
        }

        return "redirect:/admin/takecourse/list.do";
    }

}
