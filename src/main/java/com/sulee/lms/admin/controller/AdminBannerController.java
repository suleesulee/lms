package com.sulee.lms.admin.controller;


import com.sulee.lms.admin.dto.BannerDto;
import com.sulee.lms.admin.dto.CategoryDto;
import com.sulee.lms.admin.entity.Banner;
import com.sulee.lms.admin.entity.BannerCode;
import com.sulee.lms.admin.model.BannerInput;
import com.sulee.lms.admin.model.BannerParam;
import com.sulee.lms.admin.model.CategoryInput;
import com.sulee.lms.admin.model.MemberParam;
import com.sulee.lms.admin.service.BannerService;
import com.sulee.lms.admin.service.CategoryService;
import com.sulee.lms.course.controller.BaseController;
import com.sulee.lms.course.dto.CourseDto;
import com.sulee.lms.course.entity.TakeCourse;
import com.sulee.lms.course.model.CourseInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminBannerController extends BaseController {

    private final BannerService bannerService;


    @GetMapping("/admin/banner/list.do")
    public String list(Model model, BannerParam parameter){

        parameter.init();

        List<BannerDto> list = bannerService.list(parameter);

        long totalCount = 0;

        if(!CollectionUtils.isEmpty(list)){
            totalCount = list.get(0).getTotalCount();
        }
        String queryString = parameter.getQueryString();
        String PagerHtml = super.getPagerHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);

        model.addAttribute("list", list);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", PagerHtml);
        return "admin/banner/list";
    }

    @GetMapping(value = {"/admin/banner/add.do", "/admin/banner/edit.do"})
    public String add(Model model, HttpServletRequest request, BannerInput parameter){

        boolean editMode = request.getRequestURI().contains("/edit.do");
        BannerDto detail = new BannerDto();

        String[] statusList = {BannerCode.OPEN_METHOD_CUR, BannerCode.OPEN_METHOD_NEW};

        if(editMode){
            long id = parameter.getId();

            BannerDto existBanner = bannerService.getById(id);

            if(existBanner == null){
                //error
                model.addAttribute("message","배너정보가 존재하지 않습니다.");
                return "common/error";
            }
            detail = existBanner;

        }

        model.addAttribute("statusList", statusList);
        model.addAttribute("editMode", editMode);
        model.addAttribute("detail", detail);

        return "admin/banner/add";
    }

    private String[] getNewSaveFile(String baseLocalPath, String baseUrlPath, String urlAdrr, String originalFilename){

//        LocalDate now = LocalDate.now();
//
//        String dirs[] = {String.format("%s/%d", baseLocalPath, now.getYear()),
//                String.format("%s/%d/%02d", baseLocalPath, now.getYear(), now.getMonthValue()),
//                String.format("%s/%d/%02d/%02d/", baseLocalPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth())
//        };

        //String urlDir = String.format("%s/%d/%02d/%02d/", baseUrlPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth());

        String dir = String.format("%s/%s/", baseLocalPath, urlAdrr);
        String urlDir = String.format("%s/%s/", baseUrlPath, urlAdrr);

        File file = new File(dir);
        if(!file.isDirectory()){
            file.mkdir();
        }


        String fileExtension = "";
        if(originalFilename != null) {
            int dotPos = originalFilename.lastIndexOf(".");
            if(dotPos > -1){
                fileExtension = originalFilename.substring(dotPos + 1);
            }
        }

        //String uuid = UUID.randomUUID().toString().replace("-", "");
        //String newFilename = String.format("%s%s", dirs[2], uuid);
        //String newUrlFilename =  String.format("%s%s", urlDir, uuid);
//        if(fileExtension.length() > 0){
//            newFilename += "." + fileExtension;
//            newUrlFilename += "." + fileExtension;
//        }
        dir += originalFilename;
        urlDir += originalFilename;

        return new String[]{dir, urlDir};
    }

    @PostMapping(value = {"/admin/banner/add.do", "/admin/banner/edit.do"})
    public String addSubmit(Model model, BannerInput parameter, HttpServletRequest request, MultipartFile file) throws IOException {

        String saveFilename = "";
        String urlFilename = "";

        if(file != null){
            String originalFilename = file.getOriginalFilename();

            String baseLocalPath = "C:\\Users\\seong\\OneDrive\\Lecture\\2. zerobase\\lms\\files";
            String baseUrlPath ="/files";

            //저장되는 로컬위치와, url의 시작점의 고정, 사용자가 입력한 링크주소는 urlPath의 뒷부분으로 붙는다.
            //이미지파일의 이름은 변경하지 않고 그냥 사용한다.

            String[] arrFilename = getNewSaveFile(baseLocalPath, baseUrlPath, parameter.getUrlFilename(), originalFilename);
            saveFilename = arrFilename[0];
            urlFilename = arrFilename[1];

            try {
                File newFile = new File(saveFilename);
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(newFile));
            } catch (IOException e){
                log.info(e.getMessage());
            }
        }

        parameter.setFilename(saveFilename);
        parameter.setUrlFilename(urlFilename);

        boolean editMode = request.getRequestURI().contains("/edit.do");

        if(editMode){
            long id = parameter.getId();

            BannerDto existBanner = bannerService.getById(id);

            if(existBanner == null){
                //error
                model.addAttribute("message","배너가 존재하지 않습니다.");
                return "common/error";
            }
            boolean result = bannerService.set(parameter);

        } else {
            boolean result = bannerService.add(parameter);
        }

        return "redirect:/admin/banner/list.do";
    }



    @PostMapping("/admin/banner/delete.do")
    public String del(Model model, BannerInput parameter){

        boolean result = bannerService.del(parameter.getIdList());

        return "redirect:/admin/banner/list.do";
    }
}
