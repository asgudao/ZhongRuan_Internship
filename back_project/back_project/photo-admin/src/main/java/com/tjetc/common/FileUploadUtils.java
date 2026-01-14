package com.tjetc.common;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 文件上传工具类
 */
public class FileUploadUtils {
    /**
     * 上传文件
     *
     * @param multipartFile             文件对象
     * @param basePath         文件存储位置的基本路径
     * @param returnPathPrefix 文件返回url前缀
     * @return
     */
    public static JsonResult upload(MultipartFile multipartFile,
                                    String basePath,
                                    String returnPathPrefix) {
        //d:/temp/image/20220811/uuid（去掉-）.图片后缀
        //增加一个日期目录
        //日期的默认格式2022-08-11，转换成20220811
        LocalDate nowDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        //格式20220811
        String strNowDate = formatter.format(nowDate);
        //basePath增加returnPathPrefix和日期路径
        basePath = basePath + "/" + returnPathPrefix + "/" + strNowDate;
        //判断目录是否存在，不存在就创建目录
        File basePathFile = new File(basePath);
        if (!basePathFile.exists()) {
            //创建目录（父目录不存在，子父目录都创建）
            basePathFile.mkdirs();
        }
        //上传文件的原始名称 1.jpg
        String originalFilename = multipartFile.getOriginalFilename();
        //获取文件的后缀名称
        //获取文件名称最后一个点的索引
        int lastDotIndex = originalFilename.lastIndexOf(".");
        //上传文件的后缀名称
        String suffixName = originalFilename.substring(lastDotIndex + 1);
        //生成新的文件名称 1d1ac4-7830654-cad96b7e-b7f2edc-7c70
        String newFileName = UUID.randomUUID().toString().replaceAll("-", "");
        //文件的全路径：文件的磁盘路径+文件名称  例如 D:/temp/image/20220811/1d1ac47830654cad96b7eb7f2edc7c70.jpg
        String fileName = basePath + "/" + newFileName + "." + suffixName;
        //文件夹或者文件的File对象，也就是File可以表示文件夹或者文件
        //File表示文件件或者文件在磁盘不存在，所以File是一个对文件夹或者文件的抽象
        File newFile = new File(fileName);
        try {
            //把上传的MultipartFile对象保存磁盘
            multipartFile.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
            return JsonResult.fail( "服务器错误");
        }
        //返回给前端的路径  /image/20220811/1d1ac47830654cad96b7eb7f2edc7c70.jpg
        String returnUrl = "/"+returnPathPrefix + "/" + strNowDate + "/" + newFileName + "." + suffixName;
        return JsonResult.success("成功", returnUrl);
    }
}
