package com.tjetc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tjetc.common.JsonResult;
import com.tjetc.dao.CommunityMapper;
import com.tjetc.entity.Album;
import com.tjetc.entity.Community;
import com.tjetc.entity.Community;
import com.tjetc.entity.User;
import com.tjetc.service.CommunityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
@Service
@Slf4j
public class CommunityServiceImpl implements CommunityService {

    @Autowired
    private CommunityMapper communityMapper;
    
    @Override
    public JsonResult deleteById(Integer id) {
        if (id == null||id<0) {
            return JsonResult.fail("用户id不为空或者小于0");
        }
        int affectedRows = communityMapper.deleteById(id);
        if (affectedRows <= 0) {
            log.warn("userId={}，删除操作数据库，影响行数为0，所以失败",id);
            return JsonResult.fail("删除用户失败,因为用户不存在");
        }else{
            return JsonResult.success("删除用户成功");
        }
    }

    @Override
    public JsonResult<Community> selectByUsername(String username){
        if (StringUtils.isBlank(username)) {
            return JsonResult.fail("用户名不能为空");
        }
        List<Community> albums = communityMapper.selectByUsername(username);
        return JsonResult.success(albums);
    }
    @Override
    @Transactional //有了事务 默认 @Transactional 对方法的运行时异常才会回滚，所以方法出现了异常一定要要以运行时异常抛出
    public JsonResult add(Community community) {
        if(StringUtils.isBlank(community.getPhotoPath())||community.getPhotoPath()==null){
            return JsonResult.fail("未选择照片");
        }
        if(community.getContent().length()>=255){
            return JsonResult.fail("文本长度不得大于255或小于0");
        }
        LocalDateTime now = LocalDateTime.now();
        community.setCreateTime(now);
        communityMapper.insert(community);
        return JsonResult.success("上传社区成功");
    }

    @Override
    @Transactional //有了事务 默认 @Transactional 对方法的运行时异常才会回滚，所以方法出现了异常一定要要以运行时异常抛出
    public JsonResult<Community> findAll() {
        List<Community> communityList=communityMapper.selectAll();
        if(communityList==null||communityList.size()==0){
            return JsonResult.fail("未查询到社区内容");
        }
        return JsonResult.success(communityList);
    }

    @Override
    public JsonResult findPage(Integer pageNo, Integer pageSize, String username) {
        PageHelper.startPage(pageNo, pageSize);
        //处理username两端有空格情况
        if (username != null) {
            username = username.trim();
        }
        List<Community> communitys = communityMapper.selectLikeUsername(username);
        PageInfo<Community> communityPageInfo = new PageInfo<>(communitys);
        return JsonResult.success(communityPageInfo);
    }

    @Override
    public JsonResult updateById(Community community) {
        if (community.getId() == null||community.getId()<=0) {
            log.warn("communityId={},不正确 无法更新",community.getId());
            return JsonResult.fail("id不正确无法更新");
        }
        Community existCommunity = communityMapper.selectById(community.getId());
        if (existCommunity == null) {
            log.warn("communityId={}无法查询到这条分享",community.getId());
            return JsonResult.fail("无法找到对应的内容");
        }
        community.setCreateTime(null);
        community.setUpdateTime(LocalDateTime.now());
        int affectedRows = communityMapper.updateById(community);
        if (affectedRows <= 0) {
            log.warn("更新community 操作影响行数为（），有问题",affectedRows);
            return JsonResult.fail("更新失败");

        }else {
            return JsonResult.success("更新点赞数成功");
        }
    }
}
