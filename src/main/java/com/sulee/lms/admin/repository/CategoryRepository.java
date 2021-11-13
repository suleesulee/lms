package com.sulee.lms.admin.repository;

import com.sulee.lms.admin.entity.Category;
import com.sulee.lms.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
