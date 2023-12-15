package com.usst.usstcafeteriahub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usst.usstcafeteriahub.model.entity.Complaint;
import com.usst.usstcafeteriahub.service.ComplaintService;
import com.usst.usstcafeteriahub.mapper.ComplaintMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Klein
* @description 针对表【complaint(投诉表)】的数据库操作Service实现
* @createDate 2023-12-10 15:33:55
*/
@Service
public class ComplaintServiceImpl extends ServiceImpl<ComplaintMapper, Complaint>
    implements ComplaintService{

    @Resource
    private ComplaintMapper complaintMapper;

    /**
     * 设置投诉为已读
     * @param complaint
     * @return
     */
    @Override
    public boolean setRead(Complaint complaint) {
        return complaintMapper.setRead(complaint);
    }

    /**
     * 食堂管理员根据食堂id获取投诉列表,且未处理的投诉优先显示
     * @param cafeteriaId
     * @return
     */
    @Override
    public List<Complaint> getComplaintByCafeteriaId(Long cafeteriaId) {
        return complaintMapper.getComplaintByCafeteriaId(cafeteriaId);
    }

    /**
     * 用户根据用户id获取已回复的投诉列表
     * @param userId
     * @return
     */
    @Override
    public List<Complaint> getComplaintReplyByUserId(Long userId) {
        return complaintMapper.getComplaintReplyByUserId(userId);
    }
}




