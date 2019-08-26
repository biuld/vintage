package com.github.biuld.config.security;

import com.github.biuld.mapper.RoleMapper;
import com.github.biuld.mapper.UserMapper;
import com.github.biuld.mapper.UserRoleMapMapper;
import com.github.biuld.model.Role;
import com.github.biuld.model.User;
import com.github.biuld.model.UserRoleMap;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by biuld on 2019/8/21.
 */
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class JwtUserService implements UserDetailsService {

    private UserMapper userMapper;

    private UserRoleMapMapper userRoleMapMapper;

    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = userMapper.selectByPrimaryKey(Integer.parseInt(s));

        if (user == null)
            throw new UsernameNotFoundException("No user found with Id " + s);

        UserRoleMap tester = new UserRoleMap();
        tester.setUserId(user.getId());

        List<GrantedAuthority> roles = userRoleMapMapper.select(tester)
                .stream()
                .map(UserRoleMap::getRoleId)
                .map(roleMapper::selectByPrimaryKey)
                .map(Role::getRoleName)
                .filter(roleName -> !StringUtils.isEmpty(roleName))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new JwtUser(user, roles);
    }
}
