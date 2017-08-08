package cn.creatoo.security;

import cn.creatoo.model.system.SysUserEntity;
import cn.creatoo.service.system.SysUserRoleService;
import cn.creatoo.service.system.SysUserService;
import cn.creatoo.service.system.impl.SysUserRoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/17 0017.
 */
@Component
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private    SysUserRoleService sysUserRoleService;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserEntity sysUserEntity =sysUserService.queryByUserName(username);
        if (sysUserEntity==null){
            return null;
        }
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        List<String>  roleIds= sysUserRoleService.queryRoleIdList(username);
        for(String roleId:roleIds){
            if(roleId!=null && !roleId.trim().equals("")){
                authorities.add(new SimpleGrantedAuthority(roleId));
            }
        }
        return new MyUserDetail( sysUserEntity.getUserId(),sysUserEntity.getUsername(), sysUserEntity.getPassword(), sysUserEntity.getStatus()==null?false:sysUserEntity.getStatus()==1, authorities);
    }
    public static void main(String[] args){
         String pass = "admin";
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
        String hashPass = encode.encode(pass);
        ;
        System.out.println(encode.matches("admin",hashPass));
    }
}
