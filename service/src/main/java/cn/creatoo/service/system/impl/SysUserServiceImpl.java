package cn.creatoo.service.system.impl;


import cn.creatoo.common.utils.PRStrUtils;
import cn.creatoo.dao.system.SysUserDao;
import cn.creatoo.model.system.SysUserEntity;
import cn.creatoo.service.system.SysRoleService;
import cn.creatoo.service.system.SysUserRoleService;
import cn.creatoo.service.system.SysUserService;
import com.mysql.jdbc.StringUtils;
  import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mysql.jdbc.StringUtils.*;
import static org.apache.commons.lang3.StringUtils.isBlank;


@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysRoleService sysRoleService;

	@Override
	public List<String> queryAllPerms(Long userId) {
		return sysUserDao.queryAllPerms(userId);
	}

	@Override
	public List<Long> queryAllMenuId(Long userId) {
		return sysUserDao.queryAllMenuId(userId);
	}

	@Override
	public SysUserEntity queryByUserName(String username) {
		return sysUserDao.queryByUserName(username);
	}
	
	@Override
	public SysUserEntity queryObject(Long userId) {
		return sysUserDao.queryObject(userId);
	}

	@Override
	public List<SysUserEntity> queryList(Map<String, Object> map){
		return sysUserDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysUserDao.queryTotal(map);
	}

	@Override
	@Transactional
	public void save(SysUserEntity user) {
		user.setCreateTime(new Date());
		BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
		String hashed= encode.encode(user.getPassword());
 		user.setPassword(hashed);
		sysUserDao.save(user);
		
		//检查角色是否越权
		//checkRole(user);
		
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Override
	@Transactional
	public void update(SysUserEntity user) {

		if(!PRStrUtils.isEmpty(user.getPassword())){
			BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
			user.setPassword(encode.encode(user.getPassword()));
		}
		sysUserDao.update(user);
		
		//检查角色是否越权
		//checkRole(user);

		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Override
	@Transactional
	public void deleteBatch(Long[] userId) {
		sysUserDao.deleteBatch(userId);
	}

	@Override
	public int updatePassword(Long userId, String newPassword) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("newPassword", newPassword);
		return sysUserDao.updatePassword(map);
	}
	
	/**
	 * 检查角色是否越权
	 */
	private void checkRole(SysUserEntity user){
		//如果不是超级管理员，则需要判断用户的角色是否自己创建
	/*	if(user.getCreateUserId() == Constant.SUPER_ADMIN){
			return ;
		}*/
		
		//查询用户创建的角色列表
		List<Long> roleIdList = sysRoleService.queryRoleIdList(user.getCreateUserId());
		
		//判断是否越权
		if(!roleIdList.containsAll(user.getRoleIdList())){
			//throw new RRException("新增用户所选角色，不是本人创建");
		}
	}
}
