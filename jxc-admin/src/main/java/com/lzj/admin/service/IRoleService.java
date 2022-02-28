package com.lzj.admin.service;

import com.lzj.admin.pojo.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzj.admin.query.RoleQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author 李
 * @since 2022-02-12
 */
public interface IRoleService extends IService<Role> {

    Map<String, Object> roleList(RoleQuery roleQuery);

    void updateRole(Role role);

    void saveRole(Role role);

    Role findRoleByRoleName(String roleName);

    void deleteRole(Integer id);

    List<Map<String, Object>> queryAllRoles(Integer userId);

    void addGrant(Integer roleId,Integer[] mids);
}
