package com.github.biuld.service;

import com.github.biuld.config.Constants;
import com.github.biuld.config.exception.BusinessException;
import com.github.biuld.dto.params.UserParams;
import com.github.biuld.dto.view.UserView;
import com.github.biuld.mapper.CommentMapper;
import com.github.biuld.mapper.PostMapper;
import com.github.biuld.mapper.UserMapper;
import com.github.biuld.model.Role;
import com.github.biuld.model.User;
import com.github.biuld.service.bi.UserRoleService;
import com.github.biuld.util.Page;
import com.github.biuld.util.Result;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private UserMapper userMapper;

    private RoleService roleService;

    private CommentMapper commentMapper;

    private PostMapper postMapper;

    private UserRoleService userRoleService;

    public User findUserByNameOrEmail(String username, String email) {
        return Optional.ofNullable(this.findUserByName(username))
                .orElse(this.findUserByEmail(email));
    }

    public User findUserByCredenceAndPassword(String credence, String password) {
        return Optional.ofNullable(this.findUserByNameAndPwd(credence, password))
                .orElse(this.findUserByEmailAndPwd(credence, password));
    }

    private User findUserByEmail(String email) {
        User user = new User();
        user.setEmail(email);
        return userMapper.selectOne(user);
    }

    private User findUserByName(String username) {
        User user = new User();
        user.setUsername(username);
        return userMapper.selectOne(user);
    }

    private User findUserByEmailAndPwd(String email, String password) {
        return Optional.of(new User())
                .map(user -> {
                    user.setEmail(email);
                    user.setPassword(Sha512DigestUtils.shaHex(password));
                    return user;
                }).map(userMapper::selectOne)
                .orElse(null);
    }

    private User findUserByNameAndPwd(String name, String password) {
        return Optional.of(new User())
                .map(user -> {
                    user.setUsername(name);
                    user.setPassword(Sha512DigestUtils.shaHex(password));
                    return user;
                }).map(userMapper::selectOne)
                .orElse(null);
    }

    public int add(User user) {
        return add(user, List.of(Constants.ROLE_DEFAULT));
    }

    public int update(User user) {
        return update(user, List.of(Constants.ROLE_DEFAULT));
    }

    public int changePassword(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Transactional
    public int add(User user, List<String> roleNameList) {
        user.setVerified(Constants.NOT_VERIFIED);
        user.setPassword(Sha512DigestUtils.shaHex(user.getPassword()));
        int result = userMapper.insertUseGeneratedKeys(user);

        roleNameList.stream()
                .map(roleService::add)
                .map(Role::getId)
                .forEach(roleId -> userRoleService.addOneByPair(user.getId(), roleId));

        return result;
    }

    @Transactional
    public int update(User user, List<String> roleNameList) {

        Optional.ofNullable(user.getEmail())
                .map(this::findUserByEmail)
                .ifPresent(foundUser -> {
                    if (!foundUser.getId().equals(user.getId()))
                        throw new BusinessException(Result.ErrCode.USER_EXISTS);

                    if (!foundUser.getEmail().equals(user.getEmail()))
                        user.setVerified(0);
                });

        userRoleService.deleteAllByUserId(user.getId());

        roleNameList.stream()
                .map(roleService::add)
                .map(Role::getId)
                .forEach(roleId -> userRoleService.addOneByPair(user.getId(), roleId));

        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Transactional
    public int delete(Integer userId) {
        return Optional.of(userId)
                .map(userRoleService::deleteAllByUserId)
                .map(userMapper::deleteByPrimaryKey)
                .orElse(0);
    }

    public UserView getOne(Integer userId, boolean beautify) {
        return Optional.ofNullable(userMapper.selectByPrimaryKey(userId))
                .map(user -> this.userBeautifier(user, beautify))
                .orElse(null);
    }

    public Page<UserView> getList(UserParams params) {
        List<User> userList = userMapper.getUserList(params);

        PageInfo<User> pageInfo = new PageInfo<>(userList);

        List<UserView> result = userList.stream()
                .map(user -> this.userBeautifier(user, true))
                .collect(Collectors.toList());

        return Page.of(pageInfo, result);
    }

    private UserView userBeautifier(User user, boolean beautify) {
        UserView userView = new UserView();
        BeanUtils.copyProperties(user, userView);

        userView.setPassword(null);

        if (beautify) {
            //load role info
            Optional.ofNullable(userRoleService.getRoleByUserId(user.getId()))
                    .ifPresent(userView::setRoleList);

            //load comment info
            Optional.ofNullable(commentMapper.getPairList(user.getId()))
                    .ifPresent(userView::setCommentPairList);

            //load post info
            Optional.ofNullable(postMapper.getPairList(user.getId()))
                    .ifPresent(userView::setPostPairList);
        }

        return userView;
    }
}
