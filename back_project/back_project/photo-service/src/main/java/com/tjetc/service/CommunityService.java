package com.tjetc.service;

import com.tjetc.common.JsonResult;
import com.tjetc.entity.Community;
import com.tjetc.entity.User;
import org.apache.ibatis.annotations.Param;

public interface CommunityService {

    /**
     * 查询所有数据
     *
     * @return
     */
    JsonResult<Community> findAll();

    /**
     * 根据用户名和相册名进行查询
     * @param username
     * @return
     */
    JsonResult<Community> selectByUsername(@Param("username") String username);
    /**
     * 用户分享照片
     * @param community
     * @return
     */
    JsonResult add(Community community);

    /**
     * 根据用户名模糊分页查询用户信息
     *
     * @param pageNo    页面
     * @param pageSize  每页数量
     * @param username  用户名
     * @return
     */
    JsonResult findPage(Integer pageNo, Integer pageSize, String username);



    /**
     * 根据id删除
     * @param id
     * @return
     */
    JsonResult deleteById(Integer id);

    /**
     * 根据id进行更新
     * @param community
     * @return
     */
    JsonResult updateById(Community community);

}
