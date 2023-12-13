package com.usst.usstcafeteriahub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usst.usstcafeteriahub.model.entity.Complaint;
import com.usst.usstcafeteriahub.service.ComplaintService;
import com.usst.usstcafeteriahub.mapper.ComplaintMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

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
}




