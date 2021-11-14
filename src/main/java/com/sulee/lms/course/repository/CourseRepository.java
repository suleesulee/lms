package com.sulee.lms.course.repository;

import com.sulee.lms.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CourseRepository extends JpaRepository<Course, Long> {
}
