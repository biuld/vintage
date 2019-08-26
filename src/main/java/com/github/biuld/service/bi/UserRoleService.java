package com.github.biuld.service.bi;

import com.github.biuld.mapper.RoleMapper;
import com.github.biuld.mapper.UserMapper;
import com.github.biuld.mapper.UserRoleMapMapper;
import com.github.biuld.model.Role;
import com.github.biuld.model.UserRoleMap;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class UserRoleService {

    private UserRoleMapMapper userRoleMapMapper;

    private RoleMapper roleMapper;

    public void addOneByPair(Integer userId, Integer roleId) {
        UserRoleMap map = new UserRoleMap();
        map.setUserId(userId);
        map.setRoleId(roleId);

        if (userRoleMapMapper.selectCount(map) == 0) {
            userRoleMapMapper.insertSelective(map);
        }
    }

    public Integer deleteAllByRoleId(Integer roleId) {
        UserRoleMap map = new UserRoleMap();
        map.setRoleId(roleId);
        userRoleMapMapper.delete(map);
        return roleId;
    }

    public Integer deleteAllByUserId(Integer userId) {
        UserRoleMap map = new UserRoleMap();
        map.setUserId(userId);
        userRoleMapMapper.delete(map);
        return userId;
    }

    public List<Role> getRoleByUserId(Integer userId) {
        return Optional.of(new UserRoleMap())
                .map(map -> {
                    map.setUserId(userId);
                    return map;
                }).map(userRoleMapMapper::select)
                .stream()
                .flatMap(Collection::stream)
                .map(UserRoleMap::getRoleId)
                .map(roleMapper::selectByPrimaryKey)
                .collect(Collectors.toList());
    }
}
