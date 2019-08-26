package com.github.biuld.controller;

import com.github.biuld.dto.view.CommentView;
import com.github.biuld.model.Comment;
import com.github.biuld.model.User;
import com.github.biuld.service.CommentService;
import com.github.biuld.util.Page;
import com.github.biuld.util.Result;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Api(tags = "评论管理")
@RestController
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class CommentController {

    private CommentService commentService;

    private Gson gson;

    @ApiOperation(value = "获取文章评论列表", notes = "当postId=0（默认）， 返回留言列表")
    @GetMapping("/comment/all")
    public Result<Page<CommentView>> getList(@ApiParam(value = "当前页数", defaultValue = "1") @RequestParam(defaultValue = "1") Integer pageNum,
                                             @ApiParam(value = "每页条数", defaultValue = "20") @RequestParam(defaultValue = "20") Integer pageSize,
                                             @ApiParam(value = "文章id，0-留言", defaultValue = "0") @RequestParam(defaultValue = "0") Integer postId) {

        Page<CommentView> result = commentService.getList(pageNum, pageSize, postId, 0);

        return Result.success("ok", result);
    }

    @ApiOperation("获取某条评论详情")
    @GetMapping("/comment")
    public Result<CommentView> getOne(@RequestParam Integer commentId) {
        return Result.success("ok", commentService.getOne(commentId));
    }

    @ApiOperation(value = "获取评论子评论列表", notes = "当postId=0（默认）， 返回留言列表")
    @GetMapping("/comment/subComment")
    public Result<Page<CommentView>> subComment(@ApiParam(value = "当前页数", defaultValue = "1") @RequestParam(defaultValue = "1") Integer pageNum,
                                                @ApiParam(value = "每页条数", defaultValue = "20") @RequestParam(defaultValue = "20") Integer pageSize,
                                                @ApiParam(value = "文章id，0-留言", defaultValue = "0") @RequestParam(defaultValue = "0") Integer postId,
                                                @ApiParam(value = "评论id") @RequestParam Integer commentId) {

        Page<CommentView> result = commentService.getList(pageNum, pageSize, postId, commentId);

        return Result.success("ok", result);
    }

    @ApiOperation("添加评论")
    @PostMapping("/comment")
    public Result add(HttpServletRequest request, @Validated @RequestBody Comment comment) {
        User user = (User) request.getAttribute("user");

        comment.setGmtCreate(new Date());
        comment.setRequest(gson.toJson(request));
        comment.setUserId(user.getId());

        return Result.success("ok", commentService.add(comment));
    }

    @ApiOperation("删除评论")
    @DeleteMapping("/auth/comment")
    public Result delete(HttpServletRequest request, @RequestParam Integer commentId) {

        User user = (User) request.getAttribute("user");

        int result = commentService.delete(commentId, user);

        if (result == 0)
            return Result.error(Result.ErrCode.INPUT_ERROR);

        return Result.success("ok", result);
    }

    @ApiOperation("删除评论")
    @DeleteMapping("/backstage/comment")
    public Result delete(@RequestParam Integer commentId) {
        return Result.success("ok", commentService.delete(commentId));
    }
}
