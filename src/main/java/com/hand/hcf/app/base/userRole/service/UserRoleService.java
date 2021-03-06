package com.hand.hcf.app.base.userRole.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hand.hcf.app.base.lov.service.LovService;
import com.hand.hcf.app.base.userRole.domain.Role;
import com.hand.hcf.app.base.userRole.domain.UserRole;
import com.hand.hcf.app.base.userRole.dto.UserAssignRoleDTO;
import com.hand.hcf.app.base.userRole.dto.UserAssignRoleDataAuthority;
import com.hand.hcf.app.base.userRole.dto.UserRoleDTO;
import com.hand.hcf.app.base.userRole.enums.FlagEnum;
import com.hand.hcf.app.base.userRole.persistence.UserRoleMapper;
import com.hand.hcf.app.base.util.RespCode;
import com.hand.hcf.app.core.exception.BizException;
import com.hand.hcf.app.core.service.BaseService;
import com.hand.hcf.app.core.util.LoginInformationUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by houyin.zhang@hand-china.com on 2018/8/13.
 * 用户角色Service
 */
@Service
public class UserRoleService extends BaseService<UserRoleMapper, UserRole> {

    private final UserRoleMapper userRoleMapper;

    private final RoleService roleService;

    @Autowired
    private LovService lovService;

    public UserRoleService(UserRoleMapper roleMapper, RoleService roleService) {
        this.userRoleMapper = roleMapper;
        this.roleService = roleService;
    }

    /**
     * 保存用户角色
     *
     * @param userRole
     * @return
     */
    @Transactional
    public UserRole createUserRole(UserRole userRole) {
        //校验
        if (userRole == null || userRole.getId() != null) {
            throw new BizException(RespCode.SYS_ID_NOT_NULL);
        }
        //检查用户角色数据权限组合
        Integer count = getUserRoleCountByUserIdAndRoleId(userRole.getUserId(), userRole.getRoleId(), userRole.getDataAuthorityId());
        if (count != null && count > 0) {
            throw new BizException(RespCode.USER_ROLE_EXISTS);
        }
        userRoleMapper.insert(userRole);
        return userRole;
    }

    /**
     * 更新用户角色
     *
     * @param userRole1
     * @return
     */
    @Transactional
    public UserRole updateRole(UserRole userRole1) {
        //校验
        if (userRole1 == null || userRole1.getId() == null) {
            throw new BizException(RespCode.SYS_ID_NULL);
        }
        //校验ID是否在数据库中存在
        UserRole userRole = userRoleMapper.selectById(userRole1.getId());
        if (userRole == null) {
            throw new BizException(RespCode.SYS_DATASOURCE_CANNOT_FIND_OBJECT);
        }
        if (userRole1.getEnabled() == null) {
            userRole1.setEnabled(userRole.getEnabled());
        }
        userRole1.setCreatedBy(userRole.getCreatedBy());
        userRole1.setCreatedDate(userRole.getCreatedDate());
        this.updateAllColumnById(userRole1);
        //检查用户角色数据权限组合
        Integer count = getUserRoleCountByUserIdAndRoleId(userRole1.getUserId(), userRole1.getRoleId(), userRole1.getDataAuthorityId());
        if (count != null && count > 1) {
            throw new BizException(RespCode.USER_ROLE_EXISTS);
        }
        return userRole1;
    }

    /**
     * 检查用户和角色的组合是否已经存在
     *
     * @param userId
     * @param roleId
     * @return
     */
    public Integer getUserRoleCountByUserIdAndRoleId(Long userId, Long roleId, Long dataAuthorityId) {
        return userRoleMapper.selectCount(new EntityWrapper<UserRole>()
                .eq("user_id", userId)
                .eq("role_id", roleId)
                .eq("data_authority_id", dataAuthorityId));
    }

    /**
     * @param id 删除用户角色（物理删除）
     * @return
     */
    @Transactional
    public void deleteUserRole(Long id) {
        if (id != null) {
            this.deleteById(id);
        }
    }

    /**
     * @param ids 批量删除用户角色（物理删除）
     * @return
     */
    @Transactional
    public void deleteBatchUserRole(List<Long> ids) {
        if (ids != null && CollectionUtils.isNotEmpty(ids)) {
            this.deleteBatchIds(ids);
        }
    }


    /**
     * 根据用户Id，获取分配的所有角色
     *
     * @param userId  用户ID
     * @param enabled 如果不传，则不控制，如果传了，则根据传的值控制
     * @param page
     * @return
     */
    public List<UserRoleDTO> getUserRolesByUserId(Long userId, Boolean enabled, Page page) {
        List<UserRoleDTO> result = new ArrayList<UserRoleDTO>();
        List<UserRole> list = userRoleMapper.selectPage(page, new EntityWrapper<UserRole>()
                .eq(enabled != null, "enabled", enabled)
                .eq("user_id", userId)
                .orderBy("last_updated_date"));
        if (CollectionUtils.isNotEmpty(list)) {
            list.stream().forEach(e -> {
                UserRoleDTO userRoleDTO = new UserRoleDTO();
                userRoleDTO.setId(e.getId());
                userRoleDTO.setUserId(e.getUserId());
                userRoleDTO.setRole(roleService.getRoleById(e.getRoleId()));
                result.add(userRoleDTO);
            });
        }
        return result;
    }

    /**
     * 根据用户Id，获取分配的所有角色
     *
     * @param userId 用户ID
     * @return
     */
    public List<UserRole> getUserRolesByUserId(Long userId, Boolean enabled) {
        List<UserRole> list = userRoleMapper.selectList(new EntityWrapper<UserRole>()
                .eq(enabled != null, "enabled", enabled)
                .eq("user_id", userId));
        return list;
    }

    /**
     * 根据ID，获取对应的用户角色信息
     *
     * @param id
     * @return
     */
    public UserRole getUserRoleById(Long id) {
        return userRoleMapper.selectById(id);
    }

    /**
     * 用户批量分配角色
     *
     * @param userRole
     * @return
     */
    @Transactional
    public void userAssignRole(UserRoleDTO userRole) {
        //校验
        if (userRole == null || userRole.getId() != null) {
            throw new BizException(RespCode.SYS_ID_NOT_NULL);
        }
        Long userId = null;
        List<UserAssignRoleDTO> assignRole = null;
        //需要删除的
        List<Long> deleteRoleIds = new ArrayList<>();
        //需要保存的
        List<UserRole> userRoleList = new ArrayList<>();
        List<Long> userRoleIdList = new ArrayList<>();
        userId = userRole.getUserId();
        assignRole = userRole.getAssignRoleList();
        if (assignRole != null) {
            //取所有需要删除的角色ID
            assignRole.stream().filter(m -> FlagEnum.DELETE.getId().toString().equals(m.getFlag())).forEach(d -> {
                deleteRoleIds.add(d.getRoleId());
            });
            // 所有需要新增的角色ID
            Long finalUserId = userId;
            assignRole.stream().filter(m -> FlagEnum.CREATE.getId().toString().equals(m.getFlag())).forEach(d -> {
                //检查是否已经存在，已经存在的，则不更新
                Integer exists = userRoleMapper.selectCount(new EntityWrapper<UserRole>().eq("user_id", finalUserId).eq("role_id", d.getRoleId()));
                if (exists == null || exists == 0) {
                    userRoleIdList.add(d.getRoleId());
                }
            });
            if (deleteRoleIds.size() > 0) {
                // 删除
                userRoleMapper.deleteUserRoleByUserIdAndRoleIds(userId, deleteRoleIds);
            }
            userRoleIdList.stream().forEach(roleId -> {
                UserRole ur = new UserRole();
                ur.setUserId(userRole.getUserId());
                ur.setRoleId(roleId);
                userRoleList.add(ur);
            });
            if (userRoleList.size() > 0) {
                //保存角色与菜单的关联
                this.insertBatch(userRoleList, 50);
            }
        }
    }

    /**
     * 用户分配角色查询[所有启用且未删除的]
     *
     * @param userId    用户ID
     * @param roleCode  角色代码
     * @param roleName  角色名称
     * @param queryFlag ：ALL 查当前租户下所有启用的角色，ASSIGNED查 当前租户下用户已分配的启用的角色
     * @param page
     * @return
     */
    public List<Role> getRolesByCond(Long userId, String roleCode, String roleName, String queryFlag, Page page) {
        List<Role> result = null;
        if ("ALL".equals(queryFlag)) {
            // 查当前租户下所有启用的角色
            result = userRoleMapper.getAllRolesByCond(LoginInformationUtil.getCurrentTenantId(), roleCode, roleName, page);
        } else if ("ASSIGNED".equals(queryFlag)) {
            //查当前租房下用户已分配的启用的角色
            result = userRoleMapper.getSelectedRolesByCond(userId, roleCode, roleName, page);
        }
        return result;
    }

    /**
     * 判断用户是否含有启用的角色
     *
     * @param userId
     * @return
     */
    public Boolean userHasRole(Long userId) {

        return retBool(userRoleMapper.userHasRole(userId));
    }

    /**
     * 用户分配角色数据权限查询[所有启用且未删除的]
     *
     * @param userId   用户ID
     * @param roleCode 角色代码
     * @param roleName 角色名称
     * @param page
     * @return
     */
    public List<UserAssignRoleDataAuthority> listSelectedUserRolesByCond(Long userId, String roleCode, String roleName,
                                                                         String dataAuthorityName,
                                                                         ZonedDateTime validDateFrom,
                                                                         ZonedDateTime validDateTo,
                                                                         Page page) {
        List<UserAssignRoleDataAuthority>  list =  userRoleMapper.listSelectedUserRolesByCond(userId, roleCode, roleName, dataAuthorityName, validDateFrom, validDateTo, page);
        list.parallelStream().forEach(t->{
            if (t.getDataAuthorityId() != null) {
                //从主数据模块查询数据权限信息
                //jiu.zhao TODO
                /*Map<String, String> m = (Map<String, String>) lovService.getObjectByLovCode(
                        "mdata_user_all", String.valueOf(t.getDataAuthorityId()));
                t.setDataAuthorityName(m.get("dataAuthorityName"));
                t.setDataAuthorityCode(m.get("dataAuthorityCode"));*/
            }
        });
        return list;
    }

    /**
     * 用户分配角色查询[所有启用且未删除的] 不分页
     *
     * @param roleCode 角色代码
     * @param roleName 角色名称
     * @return
     */
    public List<Role> listAllRolesByCond(String roleCode, String roleName) {
        List<Role> result = userRoleMapper.getAllRolesByCond(LoginInformationUtil.getCurrentTenantId(), roleCode, roleName);
        result.stream().map(e -> {
            e.setRoleName(e.getRoleCode() + "-" + e.getRoleName());
            return e;
        }).collect(Collectors.toList());
        return result;
    }

    /**
     * 批量保存用户角色
     *
     * @param list
     * @return
     */
    @Transactional
    public Boolean createUserRoleBatch(List<UserRole> list) {
        list.stream().forEach((UserRole userRole) -> {
            createUserRole(userRole);
        });
        //  返回成功标志
        return true;
    }

    /**
     * 批量更新用户角色
     *
     * @param list
     * @return
     */
    @Transactional
    public Boolean updateUserRoleBatch(List<UserRole> list) {
        list.stream().forEach((UserRole userRole) -> {
            updateRole(userRole);
        });
        //  返回成功标志
        return true;
    }

    /**
     * 根据功能ID返回数据权限信息
     *
     * @param functionId
     * @return
     */
    public List<Long> listDataAuthIdByFunctionId(Long functionId) {
        return userRoleMapper.listDataAuthIdByFunctionId(LoginInformationUtil.getCurrentUserId(), ZonedDateTime.now(), functionId);
    }

    /**
     * 校验数据权限规则是否被使用
     *
     * @param id 用户权限id
     * @return 是否被启用
     */
    public Boolean dataAuthHasUsed(Long id) {
        return userRoleMapper.dataAuthHasUsed(id) != 0;
    }
}
