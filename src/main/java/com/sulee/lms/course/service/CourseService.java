package com.sulee.lms.course.service;

import com.sulee.lms.course.dto.CourseDto;
import com.sulee.lms.course.model.CourseInput;
import com.sulee.lms.course.model.CourseParam;
import com.sulee.lms.course.model.ServiceResult;
import com.sulee.lms.course.model.TakeCourseInput;

import java.util.List;

public interface CourseService {

    //강좌 등록
    boolean add(CourseInput parameter);


    //강좌 목록
    List<CourseDto> list(CourseParam param);


    //강좌 상세 정보
    CourseDto getById(long id);


    //강좌 정보 수정
    boolean set(CourseInput parameter);


    //강좌 삭제
    boolean del(String idList);


    //프론트 강좌 목록
    List<CourseDto> frontList(CourseParam parameter);

    //프론트 강좌 상세 정보
    CourseDto frontDetail(long id);

    //수강신청
    ServiceResult req(TakeCourseInput parameter);

    //전체 강좌 목록
    List<CourseDto> listAll();
}
