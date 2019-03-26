package com.hand.hcf.app.mdata.contact.persistence;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hand.hcf.app.mdata.contact.domain.ContactBankAccountTempDomain;
import com.hand.hcf.core.web.dto.ImportErrorDTO;
import com.hand.hcf.core.web.dto.ImportResultDTO;
import org.apache.ibatis.annotations.Param;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public interface ContactBankAccountTempMapper extends BaseMapper<ContactBankAccountTempDomain> {

    void confirmImport(@Param("transactionId") String transactionId,
                       @Param("userId") Long userId,
                       @Param("currentDate") ZonedDateTime currentDate);

    void updateBankAccountNoExists(@Param("batchNumber") String batchNumber);

    UUID selectUserOidByEmployeeIdAndTenantId(@Param("employeeId") String employeeId,
                                              @Param("tenantId") Long tenantId);

    ImportResultDTO queryInfo(@Param("transactionId") String transactionId);

    List<ImportErrorDTO> queryErrorData(@Param("transactionId") String transactionId);

    Boolean varifyBatchNumberExsits(@Param("transactionId") String transactionId);
}
