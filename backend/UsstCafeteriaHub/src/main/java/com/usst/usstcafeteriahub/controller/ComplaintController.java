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
 * Complaint接口
 * @author 黄泽旭
 */
@RestController
@Slf4j
@RequestMapping("/dishes/actions")
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

    @ApiOperation("投诉设置已读")
    @PostMapping("/setRead")
    public BaseResponse setRead(@RequestBody Complaint complaint) {
        if (complaint == null) {
            return Result.error("参数为空");
        }
        log.info("投诉设置已读: {}", complaint);
        boolean ans = complaintService.setRead(complaint);
        if(!ans){
            return Result.error("设置失败");
        }
        return Result.success("设置成功");
    }

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



}
