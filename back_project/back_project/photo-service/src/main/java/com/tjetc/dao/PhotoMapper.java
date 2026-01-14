package com.tjetc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tjetc.entity.Album;
import com.tjetc.entity.Photo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PhotoMapper  extends BaseMapper<Photo> {
    /**
     * 通过用户名和相册名查询
     * @param username
     * @param albumName
     * @return
     */
    List<Photo> selectByUsernameAndAlbumName(String username, String albumName);
}
