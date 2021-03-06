package com.github.biuld.controller.backstage;

import com.github.biuld.dto.params.Params;
import com.github.biuld.model.Role;
import com.github.biuld.service.RoleService;
import com.github.biuld.util.Page;
import com.github.biuld.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by biuld on 2019/8/22.
 */
@Api(tags = "系统角色管理")
@RestController
@RequestMapping("/backstage")
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class BkRoleCtrlr {

    private RoleService roleService;

    @ApiOperation("获取角色列表")
    @GetMapping("/role/all")
    public Result<Page<Role>> getList(Params params) {
        return Result.success("ok", roleService.getList(params));
    }

    @ApiOperation("修改角色")
    @PutMapping("role")
    public Result update(@RequestParam Integer roleId, @RequestParam String roleName) {
        return Result.success("ok", roleService.update(roleId, roleName));
    }

    @ApiOperation("删除角色")
    @DeleteMapping("role")
    public Result delete(@RequestParam Integer roleId) {
        return Result.success("ok", roleService.delete(roleId));
    }
}
