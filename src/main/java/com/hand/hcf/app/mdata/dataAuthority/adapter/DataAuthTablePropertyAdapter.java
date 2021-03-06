package com.hand.hcf.app.mdata.dataAuthority.adapter;

import com.hand.hcf.app.common.co.DataAuthTablePropertyCO;
import com.hand.hcf.app.core.enums.DataAuthFilterMethodEnum;
import com.hand.hcf.app.mdata.dataAuthority.domain.DataAuthTableProperty;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author kai.zhang05@hand-china.com
 * @create 2018/12/12 15:34
 * @remark 数据权限表配置适配器
 */
public final class DataAuthTablePropertyAdapter {

    public static DataAuthTablePropertyCO toDTO(DataAuthTableProperty entity){
        DataAuthTablePropertyCO dto = new DataAuthTablePropertyCO();
        BeanUtils.copyProperties(entity,dto);
        if(entity.getFilterMethod() != null){
            dto.setFilterMethod(DataAuthFilterMethodEnum.valueOf(entity.getFilterMethod()));
        }
        return dto;
    }

    public static List<DataAuthTablePropertyCO> toDTO(List<DataAuthTableProperty> entities){
        return entities.stream().map(e -> toDTO(e)).collect(Collectors.toList());
    }
}
