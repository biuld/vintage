package com.github.biuld.controller.backstage;

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
@RequestMapping("/backstage")
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class BkCommentCtrlr {

    private CommentService commentService;

    @ApiOperation("删除评论")
    @DeleteMapping("comment")
    public Result delete(@RequestParam Integer commentId) {
        return Result.success("ok", commentService.delete(commentId));
    }
}
