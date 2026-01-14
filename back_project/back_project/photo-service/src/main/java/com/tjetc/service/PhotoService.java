package com.tjetc.service;

import com.tjetc.common.JsonResult;
import com.tjetc.entity.Photo;
import org.apache.ibatis.annotations.Param;

public interface PhotoService {
    /**
     * 根据用户名和相册名进行查询
     * @param username
     * @param albumName
     * @return
     */
    JsonResult<Photo> selectByUsernameAndAlbumName(@Param("username") String username,
                                                   @Param("albumName")  String albumName);

    /**
     * 新增
     *
     * @param photo
     * @return
     */
    JsonResult add(Photo photo);



    /**
     * 根据id查询相片信息
     *
     * @param id
     * @return
     */
    JsonResult findById(Integer id);



    /**
     * 根据id删除
     * @param id
     * @return
     */
    JsonResult deleteById(Integer id);
}
