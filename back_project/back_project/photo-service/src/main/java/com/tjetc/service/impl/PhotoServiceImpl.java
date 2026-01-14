package com.tjetc.service.impl;

import com.tjetc.common.JsonResult;
import com.tjetc.dao.PhotoMapper;
import com.tjetc.entity.Admin;
import com.tjetc.entity.Photo;
import com.tjetc.entity.Photo;
import com.tjetc.entity.Photo;
import com.tjetc.service.PhotoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class PhotoServiceImpl implements PhotoService {
    @Autowired
    private PhotoMapper photoMapper;

    @Override
    @Transactional
    public JsonResult add(Photo photo) {
        try {
            LocalDateTime now = LocalDateTime.now();
            photo.setCreateTime(now);
            photoMapper.insert(photo);
            return JsonResult.success("新增相片成功");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JsonResult<Photo> selectByUsernameAndAlbumName(String username ,String albumName ) {
        //selectList 是mybatisplus提供的单表的查询数据
        List<Photo> photos = photoMapper.selectByUsernameAndAlbumName(username,albumName);
        return JsonResult.success(photos);
    }

    @Override
    public JsonResult findById(Integer id) {
        if (id == null || id <= 0) {
            return JsonResult.fail("照片id不为空或者小于0");
        }
        Photo photo = photoMapper.selectById(id);
        if (photo == null) {
            log.warn("photoId={}的照片不存在", id);
            return JsonResult.fail("照片不存在");
        }
        return JsonResult.success(photo);
    }



    @Override
    public JsonResult deleteById(Integer id) {
        if (id == null || id <= 0) {
            return JsonResult.fail("照片id不为空或者小于0");
        }
        int affectedRows = photoMapper.deleteById(id);
        if (affectedRows <= 0) {
            log.warn("photoId={},删除操作数据库行数为0，所以失败", id);
            return JsonResult.fail("删除照片失败,因为照片不存在");
        } else {
            return JsonResult.success("删除照片成功");
        }
    }

}
