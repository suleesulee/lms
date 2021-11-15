package com.sulee.lms.admin.mapper;

import com.sulee.lms.admin.dto.AccessInfoDto;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface AccessInfoMapper {

    LocalDateTime selectLastAccessTime(String userId);
    long selectListCount(String userId);
    List<AccessInfoDto> selectList(String userId);
}
