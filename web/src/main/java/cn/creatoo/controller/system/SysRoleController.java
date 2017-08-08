package cn.creatoo.controller.system;


import cn.creatoo.common.utils.PageUtils;
import cn.creatoo.common.utils.Query;
import cn.creatoo.common.utils.SpringContextUtil;
import cn.creatoo.model.common.R;
import cn.creatoo.model.system.SysRoleEntity;
import cn.creatoo.security.CustomSecurityMetadataSource;
import cn.creatoo.service.common.Constant;
import cn.creatoo.service.system.SysRoleMenuService;
import cn.creatoo.service.system.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月8日 下午2:18:33
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	
	/**
	 * 角色列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		//如果不是超级管理员，则只查询自己创建的角色列表
//		if(1L!= Constant.SUPER_ADMIN){
//			params.put("createUserId", 1L);
//		}
		
		//查询列表数据
		Query query = new Query(params);
		List<SysRoleEntity> list = sysRoleService.queryList(query);
		int total = sysRoleService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
		
		return R.ok().setPageVal(pageUtil);
	}
	
	/**
	 * 角色列表
	 */
	@RequestMapping("/select")
 	public R select(){
		Map<String, Object> map = new HashMap<>();
		
		//如果不是超级管理员，则只查询自己所拥有的角色列表
//		if(1 != Constant.SUPER_ADMIN){
//			map.put("createUserId", 1L);
//		}
		List<SysRoleEntity> list = sysRoleService.queryList(map);
		
		return R.ok().setDataVal(list);
	}
	
	/**
	 * 角色信息
	 */
	@RequestMapping("/info/{roleId}")
 	public R info(@PathVariable("roleId") Long roleId){
		SysRoleEntity role = sysRoleService.queryObject(roleId);
		
		//查询角色对应的菜单
		List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
		role.setMenuIdList(menuIdList);
		
		return R.ok().setDataVal(role);
	}
	
	/**
	 * 保存角色
	 */
 	@RequestMapping("/save")
 	public R save(@RequestBody SysRoleEntity role){
//		ValidatorUtils.validateEntity(role);
		
		role.setCreateUserId(getUserId());
		sysRoleService.save(role);
		//同步角色权限关系
		this.syncMemoryPermissionRoles();
		return R.ok();
	}
	
	/**
	 * 修改角色
	 */
 	@RequestMapping("/update")
 	public R update(@RequestBody SysRoleEntity role){
		//ValidatorUtils.validateEntity(role);
		
		role.setCreateUserId(getUserId());
		sysRoleService.update(role);

		this.syncMemoryPermissionRoles();
		return R.ok();
	}
	
	/**
	 * 删除角色
	 */
 	@RequestMapping("/delete")
 	public R delete(@RequestBody Long[] roleIds){
		sysRoleService.deleteBatch(roleIds);
		this.syncMemoryPermissionRoles();
		return R.ok();
	}


	public   void syncMemoryPermissionRoles(){
		//同步角色权限关系
		CustomSecurityMetadataSource customSecurityMetadataSource = (CustomSecurityMetadataSource) SpringContextUtil.getBean("customSecurityMetadataSource");
		customSecurityMetadataSource.reloadMetadataSource();
	}
}
