package com.hand.hcf.app.mdata.responsibilityCenter.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hand.hcf.app.core.service.BaseService;
import com.hand.hcf.app.mdata.responsibilityCenter.domain.GroupCenterRelationship;
import com.hand.hcf.app.mdata.responsibilityCenter.persistence.GroupCenterRelationshipMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author rookie
 * @create 2019/1/29:00
 * remark:
 */
@Service
public class GroupCenterRelationshipService extends BaseService<GroupCenterRelationshipMapper,GroupCenterRelationship> {
    /**
     * 插入责任中心组关联责任中心 关联关系
     * @param centerGroupId 责任中心组id
     * @param id 责任中心id
     */
    @Transactional
    public void insertGroupCenterRelationship(Long centerGroupId, Long id) {
         GroupCenterRelationship centerRelationship = new GroupCenterRelationship();
         centerRelationship.setGroupId(centerGroupId);
         centerRelationship.setResponsibilityCenterId(id);
         this.insert(centerRelationship);
    }

    /**
     * 获取责任中心组关联的责任中心Ids
     * @param groupId
     * @return
     */
    public List<Long> listGroupCenterRelByGroupId(Long groupId){
        List<Long> resIdList = this.selectList(
                new EntityWrapper<GroupCenterRelationship>()
                        .eq("group_id",groupId))
                .stream()
                .map(GroupCenterRelationship::getResponsibilityCenterId)
                .collect(Collectors.toList());
        return resIdList;
    }
}
