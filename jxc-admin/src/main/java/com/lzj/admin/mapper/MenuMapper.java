package com.lzj.admin.mapper;

import com.lzj.admin.dto.TreeDto;
import com.lzj.admin.pojo.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author 李
 * @since 2022-02-14
 */
public interface MenuMapper extends BaseMapper<Menu> {
    List<TreeDto> queryAllMenus(Integer roleId);
}
