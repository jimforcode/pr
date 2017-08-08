package cn.creatoo.controller.system;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/8/3 0003.
 */
@Controller
@RequestMapping("/sys")
public class SysPagesController {


    @RequestMapping("/user")
    public  String User(HttpServletResponse response){

        response.setHeader("X-Frame-Options", "SAMEORIGIN");
        return  "/sys/user";
    }


    @RequestMapping("/role")
    public  String role(HttpServletResponse response){

        response.setHeader("X-Frame-Options", "SAMEORIGIN");

        return  "/sys/role";
    }


    @RequestMapping("/main")
    public  String main(HttpServletResponse response){

        response.setHeader("X-Frame-Options", "SAMEORIGIN");
        return  "/sys/main";
    }


    @RequestMapping("/menu")
    public  String menu(HttpServletResponse response){

        response.setHeader("X-Frame-Options", "SAMEORIGIN");return  "/sys/menu";
    }


    @RequestMapping("/header")
    public  String header(HttpServletResponse response){

        response.setHeader("X-Frame-Options", "SAMEORIGIN");return  "/sys/header";
    }

    @RequestMapping("/test")
    public  String test(HttpServletResponse response){

        response.setHeader("X-Frame-Options", "SAMEORIGIN");return  "/sys/test";
    }

}
