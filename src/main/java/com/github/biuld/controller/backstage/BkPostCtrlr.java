package com.github.biuld.controller.backstage;

import com.github.biuld.dto.params.PostParams;
import com.github.biuld.dto.view.PostView;
import com.github.biuld.model.Post;
import com.github.biuld.model.User;
import com.github.biuld.service.PostService;
import com.github.biuld.util.Page;
import com.github.biuld.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Api(tags = "文章管理")
@RestController
@RequestMapping("/backstage")
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class BkPostCtrlr {

    private PostService postService;

    @PostMapping("post")
    @ApiOperation("创建文章")
    public Result create(HttpServletRequest request, @RequestParam Integer userId, @Validated @RequestBody Post post) {
        User user = (User) request.getAttribute("user");

        post.setGmtCreate(new Date());
        post.setId(userId);
        post.setLastModifiedBy(user.getId());
        post.setGmtModify(post.getGmtCreate());

        return Result.success("ok", postService.add(post, post.getTagDescList()));
    }

    @DeleteMapping("post")
    @ApiOperation("删除文章")
    public Result delete(@RequestParam Integer postId) {
        return Result.success("ok", postService.delete(postId));
    }

    @PutMapping("post")
    @ApiOperation("修改文章")
    public Result adminUpdate(HttpServletRequest request, @RequestParam Integer postId, @Validated @RequestBody Post post) {
        User user = (User) request.getAttribute("user");
        post.setLastModifiedBy(user.getId());
        post.setId(postId);
        post.setGmtModify(new Date());
        return Result.success("ok", postService.update(post, post.getTagDescList()));
    }
}
