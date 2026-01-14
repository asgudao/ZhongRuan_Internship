package com.tjetc.controller;

import com.tjetc.common.FileUploadUtils;
import com.tjetc.common.JsonResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "upload", method = {RequestMethod.POST})
public class UploadController {
    @Value("${file.basePath}")
    private String fileBasePath;


    /**
     * 上传图片
     *
     * @param multipartFile
     * @return
     */
    @PostMapping("image")
    public JsonResult uploadImage(@RequestParam("image") MultipartFile multipartFile) {
        JsonResult jsonResult = FileUploadUtils.upload(multipartFile, fileBasePath, "image");
        return jsonResult;
    }

    /**
     * 上传视频
     *
     * @param multipartFile
     * @return
     */
    @PostMapping("video")
    public JsonResult uploadVideo(@RequestParam("video") MultipartFile multipartFile) {
        JsonResult jsonResult = FileUploadUtils.upload(multipartFile, fileBasePath, "video");
        return jsonResult;
    }
}
