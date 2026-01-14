package com.tjetc.controller;


import com.tjetc.common.JsonResult;
import com.tjetc.entity.Photo;
import com.tjetc.service.PhotoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("photo")
@Slf4j
public class PhotoController {
    @Autowired
    private PhotoService photoService;

    @RequestMapping("add")
    public JsonResult add(Photo photo) {
        return photoService.add(photo);
    }

    @RequestMapping("delete/{id}")
    public JsonResult delete(@PathVariable("id")Integer id, HttpServletRequest request){
        return photoService.deleteById(id);
    }


    @RequestMapping("detail/{id}")
    public JsonResult detail(@PathVariable("id") Integer id) {
        return photoService.findById(id);
    }

    @RequestMapping("select")
    public JsonResult selectByUsernameAndAlbumName (@RequestParam("username") String username,
                                                    @RequestParam("albumName")String albumName) {
        return photoService.selectByUsernameAndAlbumName(username,albumName);
    }
}
