package com.hand.hcf.app.base.system.persistence;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hand.hcf.app.base.system.domain.Interface;
import com.hand.hcf.app.base.system.dto.InterfaceTreeDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InterfaceMapper extends BaseMapper<Interface> {

    /**
     * 接口 模糊查询 查所有未删除的数据，按 module_id,req_url排序
     *
     * @param moduleId 不传则不控，传了则按其控制
     * @param keyword  模糊匹配 interfaceName或reqUrl字段
     * @return
     */
    List<Interface> getInterfacesByKeyword(@Param("appId") String appId,
                                           @Param("keyword") String keyword);

    /**
     * 查所有接口，且接模块分组进行返回
     * 接口 模糊查询 查所有未删除的数据，按 module_id,interface排序
     */
    List<InterfaceTreeDTO> getAllInterfaces();

    /**
     * 根据id查询接口
     * @param id id
     * @return Interface
     */
    Interface getById(@Param("id") Long id);

}
