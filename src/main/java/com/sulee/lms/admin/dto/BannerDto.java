package com.sulee.lms.admin.dto;

import com.sulee.lms.admin.entity.Banner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BannerDto {

    Long id;

    String bannerName;
    String filename;
    String urlFilename;
    String method;

    long sort;
    LocalDateTime regDt;
    boolean released;

    //ADD COLUMNS
    int bannerCount;

    long totalCount;
    long seq;


    public static List<BannerDto> of(List<Banner> Banners){
        if(Banners != null){
            List<BannerDto> bannerList = new ArrayList<>();
            for(Banner x : Banners){
                bannerList.add(of(x));
            }
            return bannerList;
        }
        return null;
    }

    public static BannerDto of(Banner banner){
        return BannerDto.builder()
                .id(banner.getId())
                .bannerName(banner.getBannerName())
                .filename(banner.getFilename())
                .urlFilename(banner.getUrlFilename())
                .method(banner.getMethod())
                .sort(banner.getSort())
                .regDt(banner.getRegDt())
                .released(banner.isReleased())
                .build();
    }

    public String getRegDtText(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        return regDt != null ? regDt.format(formatter) : "";
    }
}
