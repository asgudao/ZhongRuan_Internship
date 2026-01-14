package com.tjetc.service;

import com.tjetc.common.JsonResult;
import com.tjetc.entity.User;

public interface UserService {
    /**
     * 查询所有数据
     *
     * @return
     */
    JsonResult<User> findAll();

    /**
     * 新增
     *
     * @param user
     * @return
     */
    JsonResult add(User user);

    /**
     * 根据用户名和密码查询
     *
     * @param username
     * @param password
     * @return
     */
    JsonResult login(String username, String password);

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
     * 根据用户名检查用户是否存在
     * @param username
     * @return
     */
    JsonResult checkExistByUsername(String username);

    /**
     * 根据id查询用户信息
     *
     * @param id
     * @return
     */
    JsonResult findById(Integer id);

    /**
     * 根据id进行更新
     * @param user
     * @return
     */
    JsonResult updateById(User user);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    JsonResult deleteById(Integer id);


}
