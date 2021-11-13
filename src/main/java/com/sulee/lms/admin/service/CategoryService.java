package com.sulee.lms.admin.service;

import com.sulee.lms.admin.dto.CategoryDto;
import com.sulee.lms.admin.entity.Category;
import com.sulee.lms.admin.model.CategoryInput;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryService {


    //화면에 보일 리스트
    List<CategoryDto> list();

    //카테고리 신규 추가
    boolean add(String categoryName);

    //카테고리 수정
    boolean update(CategoryInput parameter);

    //카테고리 삭제
    boolean del(long id);
}
