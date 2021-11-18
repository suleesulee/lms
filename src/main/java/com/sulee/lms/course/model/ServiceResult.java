package com.sulee.lms.course.model;

import com.sulee.lms.admin.model.CommonParam;
import lombok.Data;

@Data
public class ServiceResult extends CommonParam {
    boolean result;
    String message;

    public ServiceResult(){
        result = true;
    }

    public ServiceResult(boolean result, String message){
        this.result = result;
        this.message = message;
    }

    public ServiceResult(boolean result){
        this.result = result;
    }


}
