package com.sulee.lms.course.model;

import com.sulee.lms.admin.model.CommonParam;
import lombok.Data;

@Data
public class TakeCourseParam extends CommonParam {

    long id;
    String status;

    String userId;
}
