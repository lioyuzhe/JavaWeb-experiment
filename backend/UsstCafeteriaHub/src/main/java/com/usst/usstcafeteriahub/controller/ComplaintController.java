package com.usst.usstcafeteriahub.controller;

import com.usst.usstcafeteriahub.common.BaseResponse;
import com.usst.usstcafeteriahub.common.Result;
import com.usst.usstcafeteriahub.model.entity.Complaint;
import com.usst.usstcafeteriahub.service.ComplaintService;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 投诉 接口
 * @author 黄泽旭
 */
@RestController
@Slf4j
@RequestMapping("/complaints/actions")
public class ComplaintController {
    @Resource
    private ComplaintService complaintService;

    @ApiOperation("获取投诉列表")
    @GetMapping("/getComplaint")
    public BaseResponse getComplaint() {
        List<Complaint> list = complaintService.list();
        log.info("获取投诉列表: {}", list);
        return Result.success(list);
    }

    @ApiOperation("添加投诉")
    @PostMapping("/addComplaint")
    public BaseResponse addComplaint(@RequestBody Complaint complaint) {
        if (complaint == null) {
            return Result.error("参数为空");
        }
        log.info("添加投诉: {}", complaint);
        complaint.setStatus(0);
        boolean save = complaintService.save(complaint);
        if(!save){
            return Result.error("添加失败");
        }
        return Result.success("添加成功");
    }

    @ApiOperation("删除投诉")
    @PostMapping("/deleteComplaint")
    public BaseResponse deleteComplaint(@RequestBody Complaint complaint) {
        if (complaint == null) {
            return Result.error("参数为空");
        }
        log.info("要删除投诉: {}", complaint);
        boolean remove = complaintService.removeById(complaint.getComplaintId());
        if (!remove) {
            return Result.error("删除失败");
        }
        return Result.success("删除成功");
    }

    // 废置
    // @ApiOperation("（废置）投诉设置已处理")
    // @PostMapping("/setReply")
    // public BaseResponse setReply(@RequestBody Complaint complaint) {
    //     if (complaint == null) {
    //         return Result.error("参数为空");
    //     }
    //     log.info("投诉设置已回复: {}", complaint);
    //     boolean ans = complaintService.setRead(complaint);
    //     if(!ans){
    //         return Result.error("设置失败");
    //     }
    //     return Result.success("设置成功");
    // }

    @ApiOperation("修改投诉")
    @PostMapping("/updateComplaint")
    public BaseResponse updateComplaint(@RequestBody Complaint complaint) {
        if (complaint == null) {
            return Result.error("参数为空");
        }
        log.info("修改投诉: {}", complaint);
        boolean update = complaintService.updateById(complaint);
        if(!update){
            return Result.error("修改失败");
        }
        return Result.success("修改成功");
    }

    @ApiOperation("食堂管理员根据食堂id获取投诉列表,且未处理的投诉优先显示")
    @GetMapping("/getComplaintByCafeteriaId")
    public BaseResponse getComplaintByCafeteriaId(@RequestParam("cafeteriaId") Long cafeteriaId) {
        if (cafeteriaId == null) {
            return Result.error("参数为空");
        }
        List<Complaint> list = complaintService.getComplaintByCafeteriaId(cafeteriaId);
        if (list == null) {
            return Result.error("查询失败");
        }
        log.info("根据食堂id获取投诉列表: {}", list);
        return Result.success(list);
    }

    @ApiOperation("食堂管理员根据食堂id获取投诉列表获取未处理的投诉数量")
    @GetMapping("/getComplaintNumByCafeteriaId")
    public BaseResponse getComplaintNumByCafeteriaId(@RequestParam("cafeteriaId") Long cafeteriaId) {
        if (cafeteriaId == null) {
            return Result.error("参数为空");
        }
        List<Complaint> list = complaintService.getComplaintByCafeteriaId(cafeteriaId);
        int num = 0;
        for (Complaint complaint : list) {
            if (complaint.getStatus() == 0) {
                num++;
            }
        }
        log.info("根据食堂id获取投诉列表: {}", list);
        return Result.success(num);
    }

    @ApiOperation("用户根据用户id获取已回复的投诉列表")
    @GetMapping("/getComplaintReplyByUserId")
    public BaseResponse getComplaintReplyByUserId(@RequestParam("userId") Long userId) {
        if (userId == null) {
            return Result.error("参数为空");
        }
        List<Complaint> list = complaintService.getComplaintReplyByUserId(userId);
        if (list == null) {
            return Result.error("查询失败");
        }
        log.info("根据用户id获取已回复的投诉列表: {}", list);
        return Result.success(list);
    }
}
