package com.sulee.lms.course.model;

import com.sulee.lms.admin.model.CommonParam;
import lombok.Data;

@Data
public class TakeCourseInput extends CommonParam {
    long courseId;
    String userId;

}
