package com.sulee.lms.course.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CourseDto {
    Long id;
    String imagePath;
    String keyword;
    String subject;
    String summary;
    String contents;
    long price;
    long salePrice;
    LocalDateTime saleEndDt;

    //등록일(추가날)
    LocalDateTime regDt;
    LocalDateTime uptDt;

    //추가 컬럼
    long totalCount;
    long seq;

}
