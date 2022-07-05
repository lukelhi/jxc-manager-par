package com.lzj.admin.service;

import java.util.List;

/**
 * 进销存
 *
 * @author 进销存--lhy
 * @version 1.0
 */
public interface IRbacService {
    /**
     * 根据登录用户名查询分配的角色
     * @param userName
     * @return
     */
    List<String> findRolesByUserName(String userName);

    List<String> findAuthoritiesByRoleName(List<String> roleNames);
}
