package com.hand.hcf.app.expense.common.externalApi;

import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * </p>
 *
 * @Author: bin.xie
 * @Date: 2018/12/29
 */
@Service
public class ContractService {
    //jiu.zhao 合同
    /*@Autowired
    private ContractClient contractClient;


    public List<ContractHeaderCO> listContractHeadersByIds(List<Long> ids){
        List<ContractHeaderCO> contractHeaderCOS = contractClient.listContractHeadersByIds(ids);
        if (null == contractHeaderCOS){
            return new ArrayList<>();
        }
        return contractHeaderCOS;
    }

    *//**
     * 获取合同行信息
     * @param headerId
     * @param lineId
     * @return
     *//*
    public ContractHeaderLineCO getContractLine(Long headerId, Long lineId) {
        return contractClient.getContractLine(headerId, lineId);
    }

    *//**
     * 批量新增、批量更新 合同与业务单据关联
     * @param list
     * @return
     *//*
    public Boolean saveOrUpdateContractDocumentRelationBatch(List<ContractDocumentRelationCO> list){
        return contractClient.saveOrUpdateContractDocumentRelationBatch(list);
    }

    *//**
     * 工作流回退/手动撤回 合同与业务单据关联(逻辑删除)，需要记录日志
     * @param list
     * @return
     *//*
    public Boolean deleteContractDocumentRelationBatch(List<ContractDocumentRelationCO> list){
        return contractClient.deleteContractDocumentRelationBatch(list);
    }*/
}
