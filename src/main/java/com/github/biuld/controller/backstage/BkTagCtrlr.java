package com.github.biuld.controller.backstage;

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
@RequestMapping("/backstage")
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class BkTagCtrlr {

    private TagService tagService;

    @DeleteMapping("tag")
    @ApiOperation("删除tag")
    public Result delete(@RequestParam Integer tagId) {
        return Result.success("ok", tagService.delete(tagId));
    }

    @PutMapping("tag")
    @ApiOperation("修改tag")
    public Result update(@RequestParam Integer tagId, @RequestParam String tagDesc) {
        return Result.success("ok", tagService.update(tagId, tagDesc));
    }
}
