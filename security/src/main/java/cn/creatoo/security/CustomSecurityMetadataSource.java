package cn.creatoo.security;

import cn.creatoo.common.utils.PRStrUtils;
import cn.creatoo.model.system.dto.PermisionRolesDto;
import cn.creatoo.service.system.SysRoleMenuService;
import cn.creatoo.service.system.impl.SysRoleMenuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.*;

/**
 * Created by Administrator on 2017/7/19 0019.
 */
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

   private SysRoleMenuService  sysRoleMenuService;


    Map<String, Collection<ConfigAttribute>> metadataSource  = new HashMap<String, Collection<ConfigAttribute>>();
    public CustomSecurityMetadataSource(SysRoleMenuService  sysRoleMenuService){
        this.sysRoleMenuService=sysRoleMenuService;
        List<PermisionRolesDto>  permisionRoless= sysRoleMenuService.queryMenuRoleList();
        for(PermisionRolesDto permisionRolesDto:permisionRoless){
            if(permisionRolesDto!=null && !PRStrUtils.isEmpty(permisionRolesDto.getPermission()) &&!PRStrUtils.isEmpty(permisionRolesDto.getRoleName())){
                    if(metadataSource.containsKey(permisionRolesDto.getPermission())){
                        Collection<ConfigAttribute> atts= metadataSource.get(permisionRolesDto.getPermission());
                        atts.add(new SecurityConfig(permisionRolesDto.getRoleName()));
                    }else{
                        Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
                        atts.add(new SecurityConfig(permisionRolesDto.getRoleName()));
                        metadataSource.put(permisionRolesDto.getPermission(),atts);
                    }
            }
        }
     }

    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) object;
        //  Map<String, Collection<ConfigAttribute>> metadataSource = CustomSecurityContext.getMetadataSource();
       // metadataSource.put(url, value);

        for (Map.Entry<String, Collection<ConfigAttribute>> entry : metadataSource.entrySet()) {
            String uri = entry.getKey();
            RequestMatcher requestMatcher = new AntPathRequestMatcher(uri);
            if (requestMatcher.matches(fi.getHttpRequest())) {
                return entry.getValue();
            }
        }
        return null;
    }


    public synchronized void  reloadMetadataSource(){
        metadataSource.clear();
        this.sysRoleMenuService=sysRoleMenuService;
        List<PermisionRolesDto>  permisionRoless= sysRoleMenuService.queryMenuRoleList();
        for(PermisionRolesDto permisionRolesDto:permisionRoless){
            if(permisionRolesDto!=null && !PRStrUtils.isEmpty(permisionRolesDto.getPermission()) &&!PRStrUtils.isEmpty(permisionRolesDto.getRoleName())){
                if(metadataSource.containsKey(permisionRolesDto.getPermission())){
                    Collection<ConfigAttribute> atts= metadataSource.get(permisionRolesDto.getPermission());
                    atts.add(new SecurityConfig(permisionRolesDto.getRoleName()));
                }else{
                    Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
                    atts.add(new SecurityConfig(permisionRolesDto.getRoleName()));
                    metadataSource.put(permisionRolesDto.getPermission(),atts);
                }
            }
        }


    }








    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }




}
