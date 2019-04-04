package com.hand.hcf.app.workflow.dto;

import com.hand.hcf.app.workflow.domain.ApprovalForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by caixiang on 2018/3/21.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ApprovalFormAllDTO {

    //表单相关
    private ApprovalForm approvalFormDTO;

}
