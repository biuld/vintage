package com.github.biuld.controller.frontend;

import com.github.biuld.controller.backstage.BkPostCtrlr;
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
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class FrPostCtrlr {

    private BkPostCtrlr bkPostCtrlr;

    private PostService postService;

    @GetMapping("post")
    @ApiOperation("获取某篇文章内容")
    public Result<PostView> getOne(@ApiParam(value = "文章id", required = true) @RequestParam Integer postId) {

        PostView result = postService.getOne(postId);

        return Result.success("ok", result);
    }

    @GetMapping("/post/all")
    @ApiOperation("获取文章列表")
    public Result<Page<PostView>> getList(@ModelAttribute PostParams postParams) {

        Page<PostView> result = postService.getList(postParams);

        return Result.success("ok", result);
    }

    @PostMapping("/auth/post")
    @ApiOperation("创建文章")
    public Result create(HttpServletRequest request, @Validated @RequestBody Post post) {
        User user = (User) request.getAttribute("user");
        return bkPostCtrlr.create(request, user.getId(), post);
    }

    @DeleteMapping("/auth/post")
    @ApiOperation("删除文章")
    public Result delete(HttpServletRequest request, @RequestParam Integer postId) {
        User user = (User) request.getAttribute("user");
        return Result.success("ok", postService.delete(postId, user));
    }

    @PutMapping("/auth/post")
    @ApiOperation("修改文章")
    public Result update(HttpServletRequest request, @RequestParam Integer postId, @Validated @RequestBody Post post) {
        User user = (User) request.getAttribute("user");
        post.setLastModifiedBy(user.getId());
        post.setGmtModify(new Date());
        post.setId(postId);
        return Result.success("ok", postService.update(post, user, post.getTagDescList()));
    }

}
