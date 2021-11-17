package com.sulee.lms.course.service;

import com.sulee.lms.course.dto.TakeCourseDto;
import com.sulee.lms.course.model.ServiceResult;
import com.sulee.lms.course.model.TakeCourseParam;

import java.util.List;

public interface TakeCourseService {

    //수강 목록
    List<TakeCourseDto> list(TakeCourseParam parameter);
    
    //수강내용 상태 변경
    ServiceResult updateStatus(long id, String status);
}
