package com.tjetc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tjetc.entity.Question;
import com.tjetc.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionMapper extends BaseMapper<Question> {
    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    User selectByUsername(String username);
    /**
     * 返回所有信息
     * @return
     */
    List<Question> selectAll();
    /**
     * 根据用户名模糊匹配查询用户信息
     *
     * @param username
     * @return
     */
    List<Question> selectLikeUsername(String username);
}
