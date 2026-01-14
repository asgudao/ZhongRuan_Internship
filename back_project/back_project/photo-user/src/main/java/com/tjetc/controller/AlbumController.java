package com.tjetc.controller;


import com.tjetc.common.JsonResult;
import com.tjetc.entity.Album;
import com.tjetc.service.AlbumService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("album")
@Slf4j
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    @RequestMapping("add")
    public JsonResult add(Album album) {
        return albumService.add(album);
    }

    @RequestMapping("update")
    //接收参数格式是json
    public JsonResult update(Album album) {
        return albumService.updateById(album);
    }

    @RequestMapping("delete/{id}")
    public JsonResult delete(@PathVariable("id") Integer id, HttpServletRequest request) {
        //request.getHeader("token")
        return albumService.deleteById(id);
    }

    @RequestMapping("detail/{id}")
    public JsonResult detail(@PathVariable("id") Integer id) {
        return albumService.findById(id);
    }
    
    @RequestMapping("check-exist")
    public JsonResult checkAlbumExist(@RequestParam("username") String username,
                                      @RequestParam("albumName")String albumName) {
        return albumService.checkExistByUsernameAndAlbumName(username,albumName);
    }

    @RequestMapping("select")
    public JsonResult selectByUsername(@RequestParam("username") String username) {
        return albumService.selectByUsername(username);
    }
}
