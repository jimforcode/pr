package cn.creatoo.service.system;

import cn.creatoo.model.system.dto.PermisionRolesDto;

import java.util.List;


public interface SysRoleMenuService {
	
	void saveOrUpdate(Long roleId, List<Long> menuIdList);
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> queryMenuIdList(Long roleId);

    /* 所有权限和角色关系*/
	List<PermisionRolesDto> queryMenuRoleList();


}
