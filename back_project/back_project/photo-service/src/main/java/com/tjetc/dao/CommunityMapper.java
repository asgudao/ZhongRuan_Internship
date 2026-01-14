package com.tjetc.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tjetc.entity.Admin;
import com.tjetc.entity.Community;
import com.tjetc.entity.Question;
import com.tjetc.entity.User;

import java.util.List;

public interface CommunityMapper extends BaseMapper<Community>{
    /**
     * 返回所有信息
     * @return
     */
    List<Community> selectAll();
    /**
     * 根据用户名模糊匹配查询用户信息
     *
     * @param username
     * @return
     */
    List<Community> selectLikeUsername(String username);

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    List<Community> selectByUsername(String username);

}
