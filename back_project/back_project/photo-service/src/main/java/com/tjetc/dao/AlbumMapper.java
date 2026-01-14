package com.tjetc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tjetc.entity.Album;
import com.tjetc.entity.Album;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlbumMapper extends BaseMapper<Album> {

    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    List<Album> selectByUsername(@Param("username") String username);


    /**
     * 根据用户名和文件名查询用户
     * @param username
     * @param albumName
     * @return
     */
    Album selectByUsernameAndAlbumName(@Param("username") String username,
                                       @Param("albumName")String albumName);



}
