package com.tjetc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tjetc.common.JsonResult;
import com.tjetc.dao.QuestionMapper;

import com.tjetc.entity.Question;
import com.tjetc.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Slf4j
public class QuestionServicesImpl implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public JsonResult deleteById(Integer id) {
        if (id == null||id<0) {
            return JsonResult.fail("用户id不为空或者小于0");
        }
        int affectedRows = questionMapper.deleteById(id);
        if (affectedRows <= 0) {
            log.warn("userId={}，删除操作数据库，影响行数为0，所以失败",id);
            return JsonResult.fail("删除用户失败,因为用户不存在");
        }else{
            return JsonResult.success("删除用户成功");
        }
    }

    @Override
    @Transactional //有了事务 默认 @Transactional 对方法的运行时异常才会回滚，所以方法出现了异常一定要要以运行时异常抛出
    public JsonResult add(Question question) {

        if(question.getQuestion().length()>=255||question.getQuestion().length()<=0){
            return JsonResult.fail("问题长度不得大于255或小于0");
        }
        LocalDateTime now = LocalDateTime.now();
        question.setCreateTime(now);
        questionMapper.insert(question);
        return JsonResult.success("问题反馈成功");
    }

    @Override
    @Transactional //有了事务 默认 @Transactional 对方法的运行时异常才会回滚，所以方法出现了异常一定要要以运行时异常抛出
    public JsonResult<Question> findAll() {

        List<Question> questionList=questionMapper.selectAll();
        return JsonResult.success(questionList);
    }

    @Override
    public JsonResult findPage(Integer pageNo, Integer pageSize, String username) {
        PageHelper.startPage(pageNo, pageSize);
        //处理username两端有空格情况
        if (username != null) {
            username = username.trim();
        }
        List<Question> questions = questionMapper.selectLikeUsername(username);
        PageInfo<Question> questionPageInfo = new PageInfo<>(questions);
        return JsonResult.success(questionPageInfo);
    }
}
