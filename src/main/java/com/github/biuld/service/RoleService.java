package com.github.biuld.service;

import com.github.biuld.config.Constants;
import com.github.biuld.config.exception.BusinessException;
import com.github.biuld.dto.params.Params;
import com.github.biuld.mapper.RoleMapper;
import com.github.biuld.model.Role;
import com.github.biuld.service.bi.UserRoleService;
import com.github.biuld.util.Page;
import com.github.biuld.util.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by biuld on 2019/8/22.
 */
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class RoleService {

    private RoleMapper roleMapper;

    private UserRoleService userRoleService;

    public Page<Role> getList(Params params) {
        PageHelper.startPage(params.getPageNum(), params.getPageSize());

        List<Role> roles = roleMapper.selectAll();
        PageInfo<Role> pageInfo = new PageInfo<>(roles);

        return Page.of(pageInfo, roles);
    }

    Role add(String roleName) {
        Role role = new Role();
        role.setRoleName(Constants.ROLE_PREFIX + roleName);

        if (roleMapper.selectCount(role) == 0) {
            roleMapper.insertUseGeneratedKeys(role);
            return role;
        }

        return roleMapper.selectOne(role);
    }

    public int delete(int roleId) {
        return Optional.of(roleId)
                .map(userRoleService::deleteAllByRoleId)
                .map(roleMapper::deleteByPrimaryKey)
                .orElse(0);
    }

    public int update(int roleId, String roleName) {

        Role role = new Role();
        role.setRoleName(Constants.ROLE_PREFIX + roleName);

        if (roleMapper.selectCount(role) != 0)
            throw new BusinessException(Result.ErrCode.ROLE_EXISTS);

        role.setId(roleId);
        return roleMapper.updateByPrimaryKeySelective(role);
    }
}
