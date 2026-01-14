package com.tjetc.service.impl;

import com.tjetc.common.JsonResult;
import com.tjetc.dao.AlbumMapper;
import com.tjetc.entity.Album;
import com.tjetc.entity.Album;
import com.tjetc.service.AlbumService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumMapper albumMapper;

    @Override
    public JsonResult<Album> selectByUsername(String username){
        if (StringUtils.isBlank(username)) {
            return JsonResult.fail("用户名不能为空");
        }
        List<Album> albums = albumMapper.selectByUsername(username);
        return JsonResult.success(albums);
    }
    @Override
    public JsonResult checkExistByUsernameAndAlbumName(String username,String albumName){
        //检查用户名是否空
        if (StringUtils.isBlank(username)) {
            return JsonResult.fail("用户名不能为空");
        }
        if (StringUtils.isBlank(albumName)) {
            return JsonResult.fail("文件名不能为空");
        }
        Album album = albumMapper.selectByUsernameAndAlbumName(username, albumName);
        if (album == null) {
            return JsonResult.success(false);
        } else {
            return JsonResult.success(true);
        }
    }


    @Override
    @Transactional
    public JsonResult add(Album album){
        try{
            JsonResult jsonResult = checkAlbum(album);
            if (jsonResult.getState() != 0) {
                return jsonResult;
            }
            //insert操作
            LocalDateTime now = LocalDateTime.now();
            album.setUpdateTime(now);
            albumMapper.insert(album);
            return JsonResult.success("新增相册成功");
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private JsonResult checkAlbum(Album album){
        String username=album.getUsername();
        String albumName=album.getAlbumName();
        Album existAlbum = albumMapper.selectByUsernameAndAlbumName(username, albumName);
        log.debug("existAlbum={}", existAlbum);
        if (existAlbum != null) {
            log.info("添加中使用存在的用户名为：{},相册名为：{}", username, albumName);
            return JsonResult.fail("该用户名下的相册已经存在");
        }
        return JsonResult.success("成功添加相册");
    }


    @Override
    public JsonResult findById(Integer id) {
        if (id == null || id <= 0) {
            return JsonResult.fail("文件夹id不为空或者小于0");
        }
        Album album = albumMapper.selectById(id);
        if (album == null) {
            log.warn("albumId={}的用户不存在", id);
            return JsonResult.fail("用户不存在");
        }
        return JsonResult.success(album);
    }


    @Override
    public JsonResult updateById(Album album) {
        if (album.getId() == null || album.getId() <= 0) {
            log.warn("albumId={},不正确，无法更新", album.getId());
            return JsonResult.fail("相册的id不正确，无法更新");
        }
        Album existAlbum = albumMapper.selectById(album.getId());
        if (existAlbum == null) {
            log.warn("albumId={}，无法查询到相册", album.getId());
            return JsonResult.fail("无法找对应的相册，无法更新");
        }
        if (StringUtils.isNotBlank(album.getUsername())) {
            if (!existAlbum.getUsername().equals(album.getUsername())) {
                return JsonResult.fail("用户名有问题，无法更新");
            }
        }
        album.setUpdateTime(LocalDateTime.now());
        int affectedRows = albumMapper.updateById(album);
        if (affectedRows <= 0) {
            log.warn("albumId={}更新Album操作行数为{},有问题", album.getId(), affectedRows);
            return JsonResult.fail("更新失败");
        } else {
            return JsonResult.success("更新相册成功");
        }
    }

    @Override
    public JsonResult deleteById(Integer id) {
        if (id == null || id <= 0) {
            return JsonResult.fail("照片id不为空或者小于0");
        }
        int affectedRows = albumMapper.deleteById(id);
        if (affectedRows <= 0) {
            log.warn("albumId={},删除操作数据库行数为0，所以失败", id);
            return JsonResult.fail("删除相册失败,因为相册不存在");
        } else {
            return JsonResult.success("删除相册成功");
        }
    }
}
