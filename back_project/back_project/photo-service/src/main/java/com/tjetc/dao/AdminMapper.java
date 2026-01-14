package com.tjetc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tjetc.entity.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminMapper extends BaseMapper<Admin> {
    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    Admin selectByUsername(String username);

    /**
     * 根据用户名和密码查询用户
     *
     * @param username
     * @param password
     * @return
     */
    Admin selectByUsernameAndPassword(@Param("username") String username,
                                      @Param("password") String password);

    /**
     * 根据用户名模糊匹配查询用户信息
     *
     * @param username
     * @return
     */
    List<Admin> selectLikeUsername(String username);


}
