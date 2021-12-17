package com.sulee.lms.admin.service.impl;

import com.sulee.lms.admin.dto.BannerDto;
import com.sulee.lms.admin.dto.CategoryDto;
import com.sulee.lms.admin.entity.Banner;
import com.sulee.lms.admin.entity.Category;
import com.sulee.lms.admin.mapper.BannerMapper;
import com.sulee.lms.admin.mapper.CategoryMapper;
import com.sulee.lms.admin.model.BannerInput;
import com.sulee.lms.admin.model.BannerParam;
import com.sulee.lms.admin.model.CategoryInput;
import com.sulee.lms.admin.repository.BannerRepository;
import com.sulee.lms.admin.repository.CategoryRepository;
import com.sulee.lms.admin.service.BannerService;
import com.sulee.lms.admin.service.CategoryService;
import com.sulee.lms.course.dto.CourseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepository;
    private final BannerMapper bannerMapper;

    private Sort getSortBySortValueDesc(){
        return Sort.by(Sort.Direction.DESC,"sortValue");
    }

    private LocalDate getLocalDate(String value){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM--dd");
        try {
            return LocalDate.parse(value, formatter);
        } catch (Exception e){

        }
        return null;
    }

    @Override
    public List<BannerDto> list(BannerParam parameter) {
        long totalCount = bannerMapper.selectListCount(parameter);

        List<BannerDto> list = bannerMapper.selectList(parameter);
        if (!CollectionUtils.isEmpty(list)){
            int i = 0;
            for(BannerDto x : list){
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - parameter.getPageStart() - i);
                i++;
            }
        }

        return list;
    }

    @Override
    public boolean add(BannerInput parameter) {
        
        //배너명 중복인 것 체크

        Banner banner = Banner.builder()
                .bannerName(parameter.getBannerName())
                .filename(parameter.getFilename())
                .urlFilename(parameter.getUrlFilename())
                .method(parameter.getMethod())
                .sort(parameter.getSort())
                .regDt(LocalDateTime.now())
                .released(parameter.isReleased())
                .build();

        bannerRepository.save(banner);

        return true;
    }

    @Override
    public boolean set(BannerInput parameter) {
        Optional<Banner> optionalBanner = bannerRepository.findById(parameter.getId());

        if(optionalBanner.isPresent()){
            Banner banner = optionalBanner.get();
            banner.setBannerName(parameter.getBannerName());
            banner.setFilename(parameter.getFilename());
            banner.setUrlFilename(parameter.getUrlFilename());
            banner.setSort(parameter.getSort());
            banner.setMethod(parameter.getMethod());
            banner.setReleased(parameter.isReleased());

            bannerRepository.save(banner);
        }

        return true;
    }

    @Override
    public boolean del(String idList) {
        if(idList != null && idList.length() > 0){
            String[] ids = idList.split(",");
            for(String x : ids){
                long id = 0L;
                try {
                    id = Long.parseLong(x);
                }catch (Exception e){

                }
                if (id > 0) {
                    bannerRepository.deleteById(id);
                }
            }
        }
        return true;
    }

    @Override
    public BannerDto getById(long id) {
        return bannerRepository.findById(id).map(BannerDto::of).orElse(null);
    }

    @Override
    public List<BannerDto> frontList() {
        return bannerMapper.select();
    }
}
