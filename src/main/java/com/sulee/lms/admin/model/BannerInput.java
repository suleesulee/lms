package com.sulee.lms.admin.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BannerInput {

    long id;
    String bannerName;
    String filename;
    String urlFilename;
    String method;
    long sort;
    LocalDate regDt;
    boolean released;

    String idList;
}
