package com.sulee.lms.course.model;

import com.sulee.lms.admin.model.CommonParam;
import lombok.Data;

@Data
public class CourseParam extends CommonParam {
    long id; //course.id
    long categoryId;

}
