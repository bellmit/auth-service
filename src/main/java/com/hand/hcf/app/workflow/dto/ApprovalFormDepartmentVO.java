package com.hand.hcf.app.workflow.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Created by lichao on 2017/7/10.
 */
@Getter
@Setter
public class ApprovalFormDepartmentVO {
    private String path;
    private UUID departmentOID;
}