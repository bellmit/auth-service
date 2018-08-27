package com.helioscloud.atlantis.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.helioscloud.atlantis.domain.Role;
import lombok.Data;

import java.util.List;

/**
 * Created by houyin.zhang@hand-china.com on 2018/8/15.
 */
@Data
public class UserRoleDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long roleId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Role role;

    private List<UserAssignRoleDTO> assignRoleList;//用于用户批量分配角色
}
