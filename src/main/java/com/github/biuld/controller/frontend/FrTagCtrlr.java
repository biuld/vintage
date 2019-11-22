package com.github.biuld.controller.frontend;

import com.github.biuld.model.Tag;
import com.github.biuld.model.User;
import com.github.biuld.service.TagService;
import com.github.biuld.util.Page;
import com.github.biuld.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "文章标签管理")
@RestController
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class FrTagCtrlr {

    private TagService tagService;

    @GetMapping("tag/all")
    @ApiOperation("获取tag列表")
    public Result<Page<Tag>> getList(@ApiParam(value = "当前页数", defaultValue = "1") @RequestParam(defaultValue = "1") Integer pageNum,
                                        @ApiParam(value = "每页条数", defaultValue = "20") @RequestParam(defaultValue = "20") Integer pageSize,
                                        @ApiParam("关键字") @RequestParam(required = false) String keyword) {

        Page<Tag> result = tagService.getList(pageNum, pageSize, keyword);

        return Result.success("ok", result);
    }

    @DeleteMapping("/auth/tag")
    @ApiOperation("删除tag")
    public Result delete(HttpServletRequest request, @RequestParam Integer tagId) {
        User user = (User) request.getAttribute("user");

        return Result.success("ok", tagService.delete(tagId, user));
    }
}
