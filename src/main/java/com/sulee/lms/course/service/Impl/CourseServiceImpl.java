package com.sulee.lms.course.service.Impl;

import com.sulee.lms.admin.dto.MemberDto;
import com.sulee.lms.course.dto.CourseDto;
import com.sulee.lms.course.entity.Course;
import com.sulee.lms.course.mapper.CourseMapper;
import com.sulee.lms.course.model.CourseInput;
import com.sulee.lms.course.model.CourseParam;
import com.sulee.lms.course.repository.CourseRepository;
import com.sulee.lms.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public boolean add(CourseInput parameter) {
        Course course = Course.builder()
                .categoryId(parameter.getCategoryId())
                .subject(parameter.getSubject())
                .regDt(LocalDateTime.now())
                .build();

        courseRepository.save(course);

        return true;
    }

    @Override
    public boolean set(CourseInput parameter) {
        Optional<Course> optionalCourse = courseRepository.findById(parameter.getId());
        if(!optionalCourse.isPresent()){
            return false;
        }

        Course course = optionalCourse.get();
        course.setCategoryId(parameter.getCategoryId());
        course.setSubject(parameter.getSubject());
        course.setUptDt(LocalDateTime.now());

        courseRepository.save(course);

        return true;
    }

    @Override
    public List<CourseDto> list(CourseParam parameter) {

        long totalCount = courseMapper.selectListCount(parameter);

        List<CourseDto> list = courseMapper.selectList(parameter);
        if (!CollectionUtils.isEmpty(list)){
            int i = 0;
            for(CourseDto x : list){
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - parameter.getPageStart() - i);
                i++;
            }
        }

        return list;
    }

    @Override
    public CourseDto getById(long id) {
        return courseRepository.findById(id).map(CourseDto::of).orElse(null);
    }


}
