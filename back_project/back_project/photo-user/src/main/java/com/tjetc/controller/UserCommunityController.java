package com.tjetc.controller;

import com.tjetc.common.JsonResult;
import com.tjetc.entity.Community;
import com.tjetc.service.CommunityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("community")
@Slf4j
public class UserCommunityController {

    @Autowired
    private CommunityService communityService;

    @RequestMapping("add")
    public JsonResult add(Community community) {
        return communityService.add(community);
    }

    @RequestMapping("delete/{id}")
    public JsonResult delete(@PathVariable("id") Integer id) {
        return communityService.deleteById(id);
    }

    @RequestMapping("list")
    public JsonResult list() {return communityService.findAll();}

    @RequestMapping("select")
    public JsonResult selectByUsername(@RequestParam("username") String username) {
        return communityService.selectByUsername(username);
    }

    @RequestMapping("update")
    public JsonResult update(Community community)
    {
        return communityService.updateById(community);
    }

}
