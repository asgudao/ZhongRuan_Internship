package com.tjetc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tjetc.common.JsonResult;
import com.tjetc.common.JwtTokenUtil;
import com.tjetc.dao.UserMapper;
import com.tjetc.entity.User;
import com.tjetc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    //token过期时间，单位毫秒
    @Value("${jwt.token.expired}")
    private Integer tokenExpired;
    @Autowired
    private UserMapper userMapper;

    @Override
    public JsonResult<User> findAll() {
        //selectList 是mybatisplus提供的单表的查询数据
        List<User> users = userMapper.selectList(null);
        return JsonResult.success(users);
    }

    //事务：数据库进行多表操作或者单表多次操作（多次发送sql语句给数据库），涉及多次操作有可能有一次失败，那么其他操作是成功情况
    //我们希望操作要么全部成功 要么全部失败（全部成功，就认为service操作成功了，有一个失败了，要让数据回滚，把操作成功的分体步骤进行回滚，
    // 就是回到原来初始状态）
    //例如：A和B各有500元， A转账100元给B
    //操作步骤：A-100=400（成功）   B+100（失败了） ，回滚 A+100=500
    @Override
    @Transactional //有了事务 默认 @Transactional 对方法的运行时异常才会回滚，所以方法出现了异常一定要要以运行时异常抛出
    public JsonResult add(User user) {
        try {
            //校验数据
            JsonResult jsonResult = checkUser(user);
            //jsonResult的state值为0才校验通过
            if (jsonResult.getState() != 0) {
                return jsonResult;
            }
            //insert操作
            LocalDateTime now = LocalDateTime.now();
            user.setCreateTime(now);
            user.setUpdateTime(now);
            userMapper.insert(user);
            //模拟一个异常
//            int i = 0;
//            if (i == 0) {
//                //throw new RuntimeException("模拟异常");
//                throw new IllegalAccessException("非法参数");
//            }
            return JsonResult.success("新增管理员用户成功");
        } catch (Exception e) {
            //log.error(e.toString());
            //要抛出运行时异常
            throw new RuntimeException(e);
            //return JsonResult.fail("出错了，请联管理员");
        }
    }

    /**
     * 独立校验方法 目的是添加用户的方法（add）体代码太多，所以要进行独立方法，以减少每个方法体的代码量，
     * 一般一个方法体建议200以内
     * 校验user对象数据
     *
     * @param user
     * @return
     */
    private JsonResult checkUser(User user) {
        //if (user.getUsername() == null || "".equals(user.getUsername())) {
//            return JsonResult.fail("用户名不能为空");
//        }
//        if (user.getPassword() == null || "".equals(user.getPassword())) {
//            return JsonResult.fail("密码不能为空");
//        }
//        if (StringUtils.isBlank(user.getUsername())) {
//            return JsonResult.fail("用户名不能为空");
//        }
//        if (StringUtils.isBlank(user.getPassword())) {
//            return JsonResult.fail("密码不能为空");
//        }
        String username = user.getUsername();
        String password = user.getPassword();
        //校验用户名和密码
        JsonResult jsonResult = checkUsernameAndPassword(username, password);
        if (jsonResult.getState() != 0) {
            return jsonResult;
        }
        //todo 用正则表达校验密码符号
        //校验用户名是否已经存在
        User existUser = userMapper.selectByUsername(username);
        //调试信息
        log.debug("existUser={}", existUser);
        if (existUser != null) {
            //开发中禁止使用打印System.out.println() 因为 控制台打印是单线程，带来性能问题
            //System.out.println("注册中使用存在的用户名为：" + username);
            //获取记录日志对象log
            //Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
            //可以{} 作为参数占位
            //日志信息
            log.info("注册中使用存在的用户名为：{},密码为：{}", username, password);
            return JsonResult.fail(1,"用户名已经存在");
        }
        return JsonResult.success("");
    }

    /**
     * 校验用户名和密码
     *
     * @param username
     * @param password
     * @return
     */
    private JsonResult checkUsernameAndPassword(String username, String password) {
        if (StringUtils.isAnyBlank(username, password)) {
            return JsonResult.fail("用户名或者密码不能为空");
        }
        //校验长度
        if (StringUtils.length(username) < 3 || StringUtils.length(username) > 10) {
            return JsonResult.fail("用户名的长度是3~10字符");
        }
        if (StringUtils.length(password) < 3 || StringUtils.length(password) > 20) {
            return JsonResult.fail("密码的长度是3~20字符");
        }
        return JsonResult.success("");
    }

    @Override
    public JsonResult login(String username, String password) {
        //校验数据
        JsonResult jsonResult = checkUsernameAndPassword(username, password);
        if (jsonResult.getState() != 0) {
            return jsonResult;
        }
        //根据用户名和密码查询用户，如果查询结果为null，说明用户名或者密码不正确，否则登录成功
        User user = userMapper.selectByUsernameAndPassword(username, password);
        if (user == null) {
            return JsonResult.fail("用户名或者密码不正确");
        } else {
            //登录成功，生成token 并把token返回给前端
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", user.getId());
            claims.put("username", user.getUsername());
            String token = JwtTokenUtil.generateToken(claims, "user", tokenExpired);
            //密码设置为null，不能给前端，属于敏感信息
            user.setPassword(null);
            //返回前端的message是token 同时还有user对象信息（不包括密码）
            return JsonResult.success(token, user);
        }
    }

    @Override
    public JsonResult findPage(Integer pageNo, Integer pageSize, String username) {
        PageHelper.startPage(pageNo, pageSize);
        //处理username两端有空格情况
        if (username != null) {
            username = username.trim();
        }
        List<User> users = userMapper.selectLikeUsername(username);
        PageInfo<User> userPageInfo = new PageInfo<>(users);
        return JsonResult.success(userPageInfo);
    }

    @Override
    public JsonResult checkExistByUsername(String username) {
        //检查用户名是否空
        if(StringUtils.isBlank(username)){
            return JsonResult.fail("用户名不能为空");
        }
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            return JsonResult.success(false);
        }else{
            return JsonResult.success(true);
        }
    }

    @Override
    public JsonResult findById(Integer id) {
        if (id == null||id<0) {
            return JsonResult.fail("用户id不能为空或者小于0");
        }
        User user = userMapper.selectById(id);
        if (user == null) {
            log.warn("userId={}的用户不存在",id);
            return JsonResult.fail("用户不存在");
        }
        user.setPassword(null);

        return JsonResult.success(user);
    }


    @Override
    public JsonResult updateById(User user) {
        if (user.getId() == null||user.getId()<=0) {
            log.warn("userId={},不正确 无法更新",user.getId());
            return JsonResult.fail("用户id不正确无法更新");
        }
        User existUser = userMapper.selectById(user.getId());
        if (existUser == null) {
            log.warn("userId={}无法查询到用户",user.getId());
            return JsonResult.fail("无法找到对应的用户");
        }
        user.setPassword(null);
        user.setCreateTime(null);
        user.setUpdateTime(LocalDateTime.now());
        int affectedRows = userMapper.updateById(user);
        if (affectedRows <= 0) {
            log.warn("更新user 操作影响行数为（），有问题",affectedRows);
            return JsonResult.fail("更新失败");

        }else {
            return JsonResult.success("更新用户信息成功");
        }
    }
    @Override
    public JsonResult deleteById(Integer id) {
        if (id == null||id<0) {
            return JsonResult.fail("用户id不为空或者小于0");
        }
        int affectedRows = userMapper.deleteById(id);
        if (affectedRows <= 0) {
            log.warn("userId={}，删除操作数据库，影响行数为0，所以失败",id);
            return JsonResult.fail("删除用户失败,因为用户不存在");
        }else{
            return JsonResult.success("删除用户成功");
        }
    }
}
