package com.github.biuld.service;

import com.github.biuld.dto.PostParams;
import com.github.biuld.dto.PostTitleIdPair;
import com.github.biuld.mapper.*;
import com.github.biuld.model.*;
import com.github.biuld.dto.view.PostView;
import com.github.biuld.service.bi.PostTagService;
import com.github.biuld.util.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class PostService {

    private PostMapper postMapper;

    private CommentService commentService;

    private UserService userService;

    private TagService tagService;

    private PostTagService postTagService;

    public Page<PostView> getList(PostParams postParams) {

        PageHelper.startPage(postParams.getPageNum(), postParams.getPageSize());

        List<Post> postList = postMapper.getPostList(postParams);

        PageInfo<Post> page = new PageInfo<>(postList);

        List<PostView> result = postList.stream().map(this::postBeautifier).collect(Collectors.toList());

        return Page.of(page, result);

    }

    private PostView postBeautifier(Post post) {

        PostView postView = new PostView();
        BeanUtils.copyProperties(post, postView);

        //load tag info
        postView.setTagList(postTagService.getTagByPostId(postView.getId()));

        //load user info
        Optional.ofNullable(postView.getUserId())
                .map(userId -> userService.getOne(userId, false))
                .ifPresent(userView -> {
                    Optional.ofNullable(userView.getUsername()).ifPresent(postView::setUserName);
                    Optional.ofNullable(userView.getAvatar()).ifPresent(postView::setAvatar);
                });

        //load comment_count
        postView.setCommentCount(commentService.getPostCommentCount(postView.getId()));

        return postView;
    }

    public PostView getOne(Integer postId) {
        Post post = postMapper.selectByPrimaryKey(postId);

        Integer viewCount = Optional.ofNullable(post.getViewCount()).orElse(0);

        post.setViewCount(viewCount + 1);

        try {
            postMapper.updateByPrimaryKeySelective(post);
        } catch (Exception e) {
            log.error("ERROR while updating post view_count：{}", e.getMessage());
        }

        return postBeautifier(post);
    }

    public int delete(Integer postId, User user) {
        return Optional.ofNullable(postMapper.selectByPrimaryKey(postId))
                .filter(elem -> elem.getUserId().equals(user.getId()))
                .map(Post::getId)
                .map(this::delete)
                .orElse(0);
    }

    public int update(Post post, User user, List<String> tagList) {
        return Optional.ofNullable(postMapper.selectByPrimaryKey(post.getId()))
                .filter(elem -> elem.getUserId().equals(user.getId()))
                .map(elem -> this.update(post, tagList))
                .orElse(0);
    }

    @Transactional
    public int delete(Integer postId) {
        return Optional.of(postId)
                .map(postTagService::deleteAllByPostId)
                .map(postMapper::deleteByPrimaryKey)
                .orElse(0);
    }

    @Transactional
    public int update(Post post, List<String> tagDescList) {
        int result = Optional.ofNullable(postMapper.selectByPrimaryKey(post.getId()))
                .map(postMapper::updateByPrimaryKeySelective)
                .orElse(0);

        postTagService.deleteAllByPostId(post.getId());

        tagDescList.stream()
                .map(tagService::add)
                .map(Tag::getId)
                .forEach(tagId -> postTagService.addOneByPair(post.getId(), tagId));

        return result;
    }

    @Transactional
    public int add(Post post, List<String> tagDescList) {
        int result = postMapper.insertUseGeneratedKeys(post);

        tagDescList.stream()
                .map(tagService::add)
                .map(Tag::getId) //retrieve tag id
                .forEach(tagId -> postTagService.addOneByPair(post.getId(), tagId)); //create post_tag_map

        return result;
    }
}
