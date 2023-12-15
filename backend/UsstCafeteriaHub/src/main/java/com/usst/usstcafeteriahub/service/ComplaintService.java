package com.usst.usstcafeteriahub.service;

import com.usst.usstcafeteriahub.model.entity.Complaint;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Klein
* @description 针对表【complaint(投诉表)】的数据库操作Service
* @createDate 2023-12-10 15:33:55
*/
public interface ComplaintService extends IService<Complaint> {

    /**
     * 设置投诉为已读
     * @param complaint
     * @return
     */
    boolean setRead(Complaint complaint);

    /**
     * 食堂管理员根据食堂id获取投诉列表,且未处理的投诉优先显示
     * @param cafeteriaId
     * @return
     */
    List<Complaint> getComplaintByCafeteriaId(Long cafeteriaId);

    /**
     * 用户根据用户id获取已回复的投诉列表
     * @param userId
     * @return
     */
    List<Complaint> getComplaintReplyByUserId(Long userId);
}
