package com.tjetc.service;

import com.tjetc.common.JsonResult;
import com.tjetc.entity.Question;
import com.tjetc.entity.User;

public interface QuestionService {

    /**
     * 根据id删除
     * @param id
     * @return
     */
    JsonResult deleteById(Integer id);

    /**
     * 新增
     *
     * @param question
     * @return
     */
    JsonResult add(Question question);

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
     * 查询所有数据
     *
     * @return
     */
    JsonResult<Question> findAll();
}
