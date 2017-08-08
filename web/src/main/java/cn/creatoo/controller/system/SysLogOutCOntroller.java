package cn.creatoo.controller.system;

import cn.creatoo.model.common.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/8/4 0004.
 */
@Controller
public class SysLogOutCOntroller {
    @Autowired
    HttpSession session;
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
             session.removeAttribute(Const.SPRING_SECURITY_CONTEXT);
        return "redirect:/login.html";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }
}
