package cn.creatoo.controller.system;

import cn.creatoo.model.common.Const;
import cn.creatoo.model.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@Controller
public class SysLoginController {
	@Autowired
	private AuthenticationManager myAuthenticationManager;
	@ResponseBody
	@RequestMapping(value = "/sys/login", method = RequestMethod.POST)
	public R login(@RequestParam(defaultValue="") String username, @RequestParam(defaultValue="")  String password, String captcha, HttpServletRequest request)throws IOException {
		username = username.trim();
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
		Authentication authentication= null;
		try {
			authentication= myAuthenticationManager.authenticate(authRequest); //调用loadUserByUsername
		}catch (Exception e){
			e.printStackTrace();

			if (e instanceof UsernameNotFoundException){

				return R.error().setMsgVal("账号或密码不对");
			}
			if(e instanceof BadCredentialsException){
				return R.error().setMsgVal("账号或密码不对");
			}
			if(e instanceof DisabledException){
				return R.error().setMsgVal("用户已被禁用");
			}
			return R.error().setMsgVal("用户验证失败！请联系管理员");
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		HttpSession session = request.getSession();
		session.setAttribute(Const.SPRING_SECURITY_CONTEXT, SecurityContextHolder.getContext()); // 这个非常重要，否则验证后将无法登陆
		session.setAttribute("user",SecurityContextHolder.getContext().getAuthentication().getDetails());
		return R.ok();
	}
}
