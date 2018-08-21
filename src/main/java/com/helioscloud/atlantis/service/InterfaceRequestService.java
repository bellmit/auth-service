package com.helioscloud.atlantis.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloudhelios.atlantis.exception.BizException;
import com.cloudhelios.atlantis.service.BaseService;
import com.helioscloud.atlantis.domain.InterfaceRequest;
import com.helioscloud.atlantis.persistence.InterfaceRequestMapper;
import com.helioscloud.atlantis.util.RespCode;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by houyin.zhang@hand-china.com on 2018/8/20.
 * 接口请求service
 */
@Service
public class InterfaceRequestService extends BaseService<InterfaceRequestMapper, InterfaceRequest> {

    private final InterfaceRequestMapper interfaceRequestMapper;

    public InterfaceRequestService(InterfaceRequestMapper moduleMapper) {
        this.interfaceRequestMapper = moduleMapper;
    }

    /**
     * 创建接口请求
     *
     * @param interfaceRequest
     * @return
     */
    @Transactional
    public InterfaceRequest createInterfaceRequest(InterfaceRequest interfaceRequest) {
        //校验
        if (interfaceRequest == null || interfaceRequest.getId() != null) {
            throw new BizException(RespCode.ID_NOT_NULL);
        }
        if (interfaceRequest.getName() == null || "".equals(interfaceRequest.getName())) {
            throw new BizException(RespCode.REQUEST_NAME_NULL);
        }
        if (interfaceRequest.getInterfaceId() == null || "".equals(interfaceRequest.getInterfaceId())) {
            throw new BizException(RespCode.REQUEST_INTERFACE_NULL);
        }
        if(interfaceRequest.getParentId() == null || "".equals(interfaceRequest.getParentId())){
            interfaceRequest.setParentId(0L);//如果没有上级，则默认为0
        }
        interfaceRequestMapper.insert(interfaceRequest);
        return interfaceRequest;
    }

    /**
     * 更新接口请求
     *
     * @param interfaceRequest
     * @return
     */
    @Transactional
    public InterfaceRequest updateInterfaceRequest(InterfaceRequest interfaceRequest) {
        //校验
        if (interfaceRequest == null || interfaceRequest.getId() == null) {
            throw new BizException(RespCode.ID_NULL);
        }
        if (interfaceRequest.getName() == null || "".equals(interfaceRequest.getName())) {
            throw new BizException(RespCode.REQUEST_NAME_NULL);
        }
        if (interfaceRequest.getInterfaceId() == null || "".equals(interfaceRequest.getInterfaceId())) {
            throw new BizException(RespCode.REQUEST_INTERFACE_NULL);
        }
        //校验ID是否在数据库中存在
        InterfaceRequest rr = interfaceRequestMapper.selectById(interfaceRequest.getId());
        if (rr == null) {
            throw new BizException(RespCode.DB_NOT_EXISTS);
        }
        if (interfaceRequest.getIsEnabled() == null || "".equals(interfaceRequest.getIsEnabled())) {
            interfaceRequest.setIsEnabled(rr.getIsEnabled());
        }
        if (interfaceRequest.getIsDeleted() == null || "".equals(interfaceRequest.getIsDeleted())) {
            interfaceRequest.setIsDeleted(rr.getIsDeleted());
        }
        if (interfaceRequest.getInterfaceId() == null || "".equals(interfaceRequest.getInterfaceId())) {
            interfaceRequest.setInterfaceId(rr.getInterfaceId());
        }
        interfaceRequest.setCreatedBy(rr.getCreatedBy());
        interfaceRequest.setCreatedDate(rr.getCreatedDate());
        this.updateById(interfaceRequest);
        return interfaceRequest;
    }

    /**
     * @param id 删除接口请求（逻辑删除）
     * @return
     */
    @Transactional
    public void deleteInterfaceRequest(Long id) {
        if (id != null) {
            this.deleteById(id);
        }
    }

    /**
     * @param ids 批量删除接口请求（逻辑删除）
     * @return
     */
    @Transactional
    public void deleteBatchInterfaceRequest(List<Long> ids) {
        if (ids != null && CollectionUtils.isNotEmpty(ids)) {
            this.deleteBatchIds(ids);
        }
    }


    /**
     * 根据接口ID，取所有接口的接口请求 分页
     *
     * @param interfaceId
     * @param page
     * @param isEnabled   如果不传，则不控制，如果传了，则根据传的值控制
     * @return
     */
    public List<InterfaceRequest> getInterfaceRequestsByInterfaceId(Long interfaceId, Boolean isEnabled, Page page) {
        return interfaceRequestMapper.selectPage(page, new EntityWrapper<InterfaceRequest>()
                .eq(isEnabled != null, "is_enabled", isEnabled)
                .eq("interface_id", interfaceId)
                .orderBy("id"));
    }

    /**
     * 根据parent，取所有子接口请求 分页
     *
     * @param parentId
     * @param page
     * @param isEnabled 如果不传，则不控制，如果传了，则根据传的值控制
     * @return
     */
    public List<InterfaceRequest> getInterfaceRequestsByParentId(Long parentId, Boolean isEnabled, Page page) {
        return interfaceRequestMapper.selectPage(page, new EntityWrapper<InterfaceRequest>()
                .eq(isEnabled != null, "is_enabled", isEnabled)
                .eq("parent_id", parentId)
                .orderBy("id"));
    }


    /**
     * 根据ID，获取对应的接口请求信息
     *
     * @param id
     * @return
     */
    public InterfaceRequest getInterfaceRequestById(Long id) {
        return interfaceRequestMapper.selectById(id);
    }
}
