package com.github.biuld.controller.frontend;


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
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FrUserCtrlr {

    private UserService userService;

    @PostMapping("/user/register")
    @ApiOperation("创建用户")
    public Result create(@Validated @RequestBody User user) {

        if (userService.findUserByNameOrEmail(user.getUsername(), user.getEmail()) != null)
            return Result.error(Result.ErrCode.USER_EXISTS);

        //ignored attributes
        user.setVerified(null);

        return Result.success("ok", userService.add(user));
    }

    @GetMapping("/auth/user")
    @ApiOperation("获取用户信息")
    public Result<UserView> getOne(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        user.setPassword(null);

        return Result.success("ok", userService.getOne(user.getId(), true));
    }

    @PutMapping("/auth/user")
    @ApiOperation("修改当前用户信息")
    public Result update(HttpServletRequest request, @Validated @RequestBody User userToUpdate) {
        User user = (User) request.getAttribute("user");
        userToUpdate.setId(user.getId());

        //ignored attributes
        userToUpdate.setPassword(null);
        userToUpdate.setVerified(null);

        return Result.success("ok", userService.update(userToUpdate));
    }

    @PutMapping("/auth/user/password")
    @ApiOperation("修改密码")
    public Result changePassword(HttpServletRequest request, @RequestParam String oldPassword, @RequestParam String newPassword) {
        User user = (User) request.getAttribute("user");

        if (!user.getPassword().equals(Sha512DigestUtils.shaHex(oldPassword)))
            return Result.error(Result.ErrCode.PWD_NOT_MATCH);

        user.setPassword(Sha512DigestUtils.shaHex(newPassword));

        return Result.success("ok", userService.changePassword(user));
    }

    @GetMapping("/user/login")
    @ApiOperation("登录")
    @ApiResponses({@ApiResponse(code = 200, message = "返回token")})
    public Result login(@ApiParam(value = "用户名或邮箱", required = true) @RequestParam String credence, @RequestParam String password) {

        User user = userService.findUserByCredenceAndPassword(credence, password);

        if (user == null)
            return Result.error(Result.ErrCode.USER_NOT_FOUND);

        String token = Token.create(Map.of("userId", user.getId()));
        log.info("{}: {}", user.getUsername(), token);

        return Result.success("ok", token);
    }
}
