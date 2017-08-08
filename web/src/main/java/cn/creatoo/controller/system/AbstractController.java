package cn.creatoo.controller.system;


import cn.creatoo.model.common.Const;
import cn.creatoo.model.system.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;

import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller公共组件
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月9日 下午9:42:26
 */
public abstract class AbstractController {
    @Autowired
    HttpSession session;
	protected Logger logger = LoggerFactory.getLogger(getClass());




	protected SysUserEntity getUser(){
        SecurityContext securityContext = (SecurityContext) session.getAttribute(Const.SPRING_SECURITY_CONTEXT);
        securityContext.getAuthentication().getPrincipal();
	    return  (SysUserEntity)securityContext.getAuthentication().getPrincipal();
    }
    protected Long getUserId(){
	    return getUser().getUserId();
    }
}
