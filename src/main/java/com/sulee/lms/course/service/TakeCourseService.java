package com.sulee.lms.course.service;

import com.sulee.lms.course.dto.TakeCourseDto;
import com.sulee.lms.course.model.ServiceResult;
import com.sulee.lms.course.model.TakeCourseParam;

import java.util.List;

public interface TakeCourseService {

    //수강 목록
    List<TakeCourseDto> list(TakeCourseParam parameter);

    //수강 상세 정보
    TakeCourseDto detail(long id);

    //수강내용 상태 변경
    ServiceResult updateStatus(long id, String status);

    //나의 수강내역 목록
    List<TakeCourseDto> myCourse(String userId);

    //내 수강내역 취소
    ServiceResult cancel(long id);
}
