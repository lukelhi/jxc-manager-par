package com.lzj.admin.mapper;

import com.lzj.admin.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author 李
 * @since 2022-02-12
 */
public interface RoleMapper extends BaseMapper<Role> {
    List<Map<String, Object>> queryAllRoles(Integer userId);
}
