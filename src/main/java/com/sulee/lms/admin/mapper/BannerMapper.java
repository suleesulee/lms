package com.sulee.lms.admin.mapper;

import com.sulee.lms.admin.dto.BannerDto;
import com.sulee.lms.admin.model.BannerParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BannerMapper {
    List<BannerDto> select();
    long selectListCount(BannerParam parameter);
    List<BannerDto> selectList(BannerParam parameter);
}

