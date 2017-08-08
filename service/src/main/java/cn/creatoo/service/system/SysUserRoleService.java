package cn.creatoo.service.system;

import java.util.List;


public interface SysUserRoleService {
	
	void saveOrUpdate(Long userId, List<Long> roleIdList);
	
	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<Long> queryRoleIdList(Long userId);

	void delete(Long userId);

	List<String> queryRoleIdList(String userName);

}
