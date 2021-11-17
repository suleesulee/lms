package com.sulee.lms.admin.mapper;

import com.sulee.lms.admin.dto.CategoryDto;
import com.sulee.lms.admin.dto.MemberDto;
import com.sulee.lms.admin.model.MemberParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    List<CategoryDto> select(CategoryDto parameter);
}
