package com.github.biuld.service;

import com.github.biuld.dto.CommentContentIdPair;
import com.github.biuld.dto.view.CommentView;
import com.github.biuld.mapper.CommentMapper;
import com.github.biuld.mapper.UserMapper;
import com.github.biuld.model.Comment;
import com.github.biuld.model.User;
import com.github.biuld.util.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class CommentService {

    private CommentMapper commentMapper;

    private UserService userService;

    public Page<CommentView> getList(Integer pageNum, Integer pageSize, Integer postId, Integer commentId) {

        Comment tester = new Comment();
        tester.setPostId(postId);
        tester.setParentId(commentId);

        PageHelper.startPage(pageNum, pageSize);

        List<Comment> commentList = commentMapper.select(tester);

        PageInfo page = new PageInfo<>(commentList);

        List<CommentView> result = commentList.stream().map(this::commentBeautifier).collect(Collectors.toList());

        return Page.of(page, result);
    }

    private CommentView commentBeautifier(Comment comment) {

        CommentView commentView = new CommentView();
        BeanUtils.copyProperties(comment, commentView);

        //load sender info
        Optional.ofNullable(commentView.getUserId())
                .map(userId -> userService.getOne(userId, false))
                .ifPresent(userView -> {
                    Optional.ofNullable(userView.getUsername()).ifPresent(commentView::setUsername);
                    Optional.ofNullable(userView.getAvatar()).ifPresent(commentView::setAvatar);
                });

        //load recipient info
        Optional.ofNullable(commentView.getReplyUserId())
                .filter(id -> id != 0)
                .map(userId -> userService.getOne(userId, false))
                .ifPresent(userView -> commentView.setReplyUserName(userView.getUsername()));

        Comment tester = new Comment();
        tester.setParentId(commentView.getId());

        int subCommentNum = commentMapper.selectCount(tester);
        commentView.setSubCommentNum(subCommentNum);

        if (subCommentNum > 1)
            commentView.setFirstSubComment(commentBeautifier(commentMapper.selectFirstComment(commentView.getId())));

        return commentView;
    }

    public CommentView getOne(Integer commentId) {
        return Optional.ofNullable(commentMapper.selectByPrimaryKey(commentId))
                .map(this::commentBeautifier)
                .orElse(null);
    }

    public int add(Comment comment) {
        return commentMapper.insertSelective(comment);
    }

    public int delete(Integer commentId, User user) {
        return Optional.ofNullable(commentMapper.selectByPrimaryKey(commentId))
                .filter(comment -> comment.getUserId().equals(user.getId()))
                .map(commentMapper::delete)
                .orElse(0);
    }

    public int delete(Integer commentId) {
        return commentMapper.deleteByPrimaryKey(commentId);
    }

    int getPostCommentCount(Integer postId) {
        return Optional.of(new Comment())
                .map(comment -> {
                    comment.setPostId(postId);
                    return comment;
                }).map(commentMapper::selectCount)
                .orElse(0);
    }
}
