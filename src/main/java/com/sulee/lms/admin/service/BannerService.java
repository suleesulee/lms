package com.sulee.lms.admin.service;

import com.sulee.lms.admin.dto.BannerDto;
import com.sulee.lms.admin.model.BannerInput;
import com.sulee.lms.admin.model.BannerParam;
import com.sulee.lms.course.dto.CourseDto;

import java.util.List;

public interface BannerService {

    //화면에 보일 배너 리스트
    List<BannerDto> list(BannerParam parameter);

    //배너 신규 추가
    boolean add(BannerInput parameter);

    //배너 수정
    boolean set(BannerInput parameter);

    //배너 삭제
    boolean del(String idList);

    //배너 상세 정보
    BannerDto getById(long id);

    //프론트(인덱스) 표시 배너 정보
    List<BannerDto> frontList();
}
