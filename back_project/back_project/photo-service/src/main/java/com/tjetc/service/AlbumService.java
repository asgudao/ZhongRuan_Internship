package com.tjetc.service;

import com.tjetc.common.JsonResult;
import com.tjetc.entity.Album;
import org.apache.ibatis.annotations.Param;

public interface AlbumService {


    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    JsonResult<Album> selectByUsername(@Param("username") String username);
    /**
     * 用户新增文件夹
     * @param album
     * @return
     */
    JsonResult add(Album album);



    /**
     * 根据用户名和文件名检查对于用户的文件名是否存在
     * @param username
     * @param albumName
     * @return
     */
    JsonResult checkExistByUsernameAndAlbumName(String username,String albumName);


    /**
     * 根据id查询用户信息
     *
     * @param id
     * @return
     */
    JsonResult findById(Integer id);

    /**
     * 根据id进行更新
     * @param album
     * @return
     */
    JsonResult updateById(Album album);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    JsonResult deleteById(Integer id);
}
