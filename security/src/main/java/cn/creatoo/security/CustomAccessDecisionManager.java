package cn.creatoo.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by liyd on 16/12/9.
 */
public class CustomAccessDecisionManager implements AccessDecisionManager {

    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {

        Iterator<ConfigAttribute> iterator = configAttributes.iterator();
        while (iterator.hasNext()) {

            if (authentication == null) {
                throw new AccessDeniedException("当前访问没有权限");
            }

            ConfigAttribute configAttribute = iterator.next();
            String needCode = configAttribute.getAttribute();

            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (StringUtils.equals(authority.getAuthority(), needCode)) {
                    return;
                }
            }
        }

        throw new AccessDeniedException("当前访问没有权限");
    }

    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
