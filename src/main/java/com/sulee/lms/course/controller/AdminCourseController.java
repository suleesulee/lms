package com.sulee.lms.course.controller;


import com.sulee.lms.course.dto.CourseDto;
import com.sulee.lms.course.model.CourseInput;
import com.sulee.lms.course.model.CourseParam;
import com.sulee.lms.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminCourseController extends BaseController {

    private final CourseService courseService;

    @GetMapping("/admin/course/list.do")
    public String list(Model model, CourseParam parameter){

        parameter.init();

        List<CourseDto> courseList = courseService.list(parameter);

        long totalCount = 0;

        if(!CollectionUtils.isEmpty(courseList)){
            totalCount = courseList.get(0).getTotalCount();
        }
        String queryString = parameter.getQueryString();
        String PagerHtml = super.getPagerHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);

        model.addAttribute("list", courseList);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", PagerHtml);
        return "admin/course/list";
    }

    @GetMapping("/admin/course/add.do")
    public String add(Model model){


        return "admin/course/add";
    }

    @PostMapping("/admin/course/add.do")
    public String addSubmit(Model model, CourseInput parameter){
        boolean result = courseService.add(parameter);

        return "redirect:/admin/course/list.do";
    }


}
