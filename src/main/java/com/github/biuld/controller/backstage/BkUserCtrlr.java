package com.github.biuld.controller.backstage;


import com.github.biuld.dto.params.UserParams;
import com.github.biuld.dto.view.UserView;
import com.github.biuld.model.User;
import com.github.biuld.service.UserService;
import com.github.biuld.util.Page;
import com.github.biuld.util.Result;
import com.github.biuld.util.Token;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@Api(tags = "用户管理")
@RestController
@RequestMapping("/backstage")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BkUserCtrlr {

    private UserService userService;

    @GetMapping("user")
    @ApiOperation("获取用户信息")
    public Result<UserView> getOne(@RequestParam Integer userId) {
        return Result.success("ok", userService.getOne(userId, true));
    }

    @PostMapping("user")
    @ApiOperation("创建用户")
    public Result add(@Validated @RequestBody User user) {
        if (userService.findUserByNameOrEmail(user.getUsername(), user.getEmail()) != null)
            return Result.error(Result.ErrCode.USER_EXISTS);

        return Result.success("ok", userService.add(user, user.getRoleNameList()));
    }

    @DeleteMapping("user")
    @ApiOperation("删除用户")
    public Result delete(@RequestParam Integer userId) {
        return Result.success("ok", userService.delete(userId));
    }

    @PutMapping("user")
    @ApiOperation("修改用户信息")
    public Result update(@RequestParam Integer userId, @Validated @RequestBody User user) {
        user.setId(userId);

        //ignored attributes
        user.setPassword(null);
        return Result.success("ok", userService.update(user, user.getRoleNameList()));
    }

    @GetMapping("/user/all")
    @ApiOperation("获取用户列表")
    public Result<Page<UserView>> getList(@ModelAttribute UserParams params) {
        return Result.success("ok", userService.getList(params));
    }
}
