package cn.creatoo.controller.system;



import cn.creatoo.common.utils.Assert;
import cn.creatoo.common.utils.PageUtils;
import cn.creatoo.common.utils.Query;
import cn.creatoo.model.common.Const;
import cn.creatoo.model.common.R;
import cn.creatoo.model.system.SysUserEntity;
import cn.creatoo.security.MyUserDetail;
import cn.creatoo.service.common.Constant;
import cn.creatoo.service.system.SysUserRoleService;
import cn.creatoo.service.system.SysUserService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月31日 上午10:40:10
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	HttpSession session;

	/**
	 * 所有用户列表
	 */
	@RequestMapping("/list")
 	public R list(@RequestParam Map<String, Object> params){
		//只有超级管理员，才能查看所有管理员列表
//		if(1L != Constant.SUPER_ADMIN){
//			params.put("createUserId", 1L);
//		}
		
		//查询列表数据
		Query query = new Query(params);
		List<SysUserEntity> userList = sysUserService.queryList(query);
		int total = sysUserService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(userList, total, query.getLimit(), query.getPage());
		
		return R.ok().setPageVal(pageUtil);
	}
	
	/**
	 * 获取登录的用户信息
	 */
	@RequestMapping("/info")
	public R info(){
		SecurityContext securityContext = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		 securityContext.getAuthentication().getPrincipal();
		SysUserEntity userEntity = getUser();
		return R.ok().setDataVal(sysUserService.queryObject(userEntity.getUserId()));
	}
	
	/**
	 * 修改登录用户密码
	 */
 	@RequestMapping("/password")
	public R password(String password, String newPassword){
		Assert.notEmpty(password, "旧密码不为能空");
 	    Assert.notEmpty(newPassword, "新密码不为能空");
 	    if(password==newPassword){
 	    	return R.error().setMsgVal("密码不能和老密码一至");
		}
		BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
	    String hashed= encode.encode(newPassword);
	    SysUserEntity userEntity= this.sysUserService.queryObject(getUserId());
	    if(encode.matches(newPassword,userEntity.getPassword())){
			return R.error().setMsgVal("密码不能和老密码一至");
		}
	//更新密码
   	int count = sysUserService.updatePassword(getUserId(), hashed);
		return R.ok();
	}
	
	/**
	 * 用户信息
	 */
	@RequestMapping("/info/{userId}")
 	public R info(@PathVariable("userId") Long userId){
		SysUserEntity user = sysUserService.queryObject(userId);
		
		//获取用户所属的角色列表
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);
		
		return R.ok().setDataVal(user);
	}
	
	/**
	 * 保存用户
	 */
 	@RequestMapping("/save")
 	public R save(@RequestBody SysUserEntity user){
//		ValidatorUtils.validateEntity(user, AddGroup.class);
		
		user.setCreateUserId(getUserId());

		sysUserService.save(user);

		return R.ok();
	}
	
	/**
	 * 修改用户
	 */
 	@RequestMapping("/update")
 	public R update(@RequestBody SysUserEntity user){
//		ValidatorUtils.validateEntity(user, UpdateGroup.class);
		
		user.setCreateUserId(getUserId());
		sysUserService.update(user);


		return R.ok();
	}
	
	/**
	 * 删除用户
	 */
 	@RequestMapping("/delete")
 	public R delete(@RequestBody Long[] userIds){
//		if(ArrayUtils.contains(userIds, 1L)){
//			return R.error().setMsgVal("系统管理员不能删除");
//		}
		
		if(ArrayUtils.contains(userIds, getUserId())){
			return R.error().setMsgVal("当前用户不能删除");
		}
		
		sysUserService.deleteBatch(userIds);



		return R.ok();
	}



}
