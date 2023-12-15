package com.usst.usstcafeteriahub.controller;

import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.model.entity.CommunityUser;
import com.usst.usstcafeteriahub.service.CommunityUserService;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * communityUser接口
 * @author 黄泽旭
 */
@RestController
@Slf4j
@RequestMapping("/communityUsers/actions")
public class CommunityUserController {
    @Resource
    private CommunityUserService communityUserService;

    @ApiOperation("根据用户名进行模糊查询 返回社区User列表")
    @GetMapping("/getCommunityUserByCommunityUserName")
    public BaseResponse getCommunityUserByCommunityUserName(@RequestParam String communityUserName) {
        if (communityUserName == null) {
            return Result.error("参数为空");
        }
        List<CommunityUser> list = communityUserService.getCommunityUserByCommunityUserName(communityUserName);
        if (list == null) {
            return Result.error("查询失败");
        }
        log.info("根据社区用户名进行模糊查询: {}", list);
        return Result.success(list);
    }

    @ApiOperation("根据user_id获取社区User信息")
    @GetMapping("/getCommunityUserByUserId")
    public BaseResponse getCommunityUserByUserId(@RequestParam Long userId) {
        if (userId == null) {
            return Result.error("参数为空");
        }
        CommunityUser communityUser = communityUserService.getCommunityUserByUserId(userId);
        if (communityUser == null) {
            return Result.error("查询失败");
        }
        log.info("根据user_id查找社区User: {}", communityUser);
        return Result.success(communityUser);
    }

    @ApiOperation("删除社区User")
    @PostMapping("/deleteCommunityUser")
    public BaseResponse deleteCommunityUser(@RequestBody CommunityUser communityUser) {
        if (communityUser == null) {
            return Result.error("参数为空");
        }
        log.info("要删除社区User: {}", communityUser);
        boolean remove = communityUserService.removeById(communityUser.getCommunityUserId());
        if (!remove) {
            return Result.error("删除失败");
        }
        return Result.success("删除成功");
    }

    @ApiOperation("添加社区User")
    @PostMapping("/addCommunityUser")
    public BaseResponse addCommunityUser(@RequestBody CommunityUser communityUser) {
        if (communityUser == null) {
            return Result.error("参数为空");
        }
        log.info("添加社区User: {}", communityUser);
        boolean save = communityUserService.save(communityUser);
        if(!save){
            return Result.error("添加失败");
        }
        return Result.success("添加成功");
    }

    @ApiOperation("修改社区User")
    @PostMapping("/updateCommunityUser")
    public BaseResponse updateCommunityUser(@RequestBody CommunityUser communityUser) {
        if (communityUser == null) {
            return Result.error("参数为空");
        }
        log.info("修改社区User: {}", communityUser);
        boolean update = communityUserService.updateById(communityUser);
        if(!update){
            return Result.error("修改失败");
        }
        return Result.success("修改成功");
    }


}
