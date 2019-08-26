package com.github.biuld.service;

import com.github.biuld.config.exception.BusinessException;
import com.github.biuld.mapper.TagMapper;
import com.github.biuld.model.Tag;
import com.github.biuld.model.User;
import com.github.biuld.service.bi.PostTagService;
import com.github.biuld.util.Page;
import com.github.biuld.util.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class TagService {

    private TagMapper tagMapper;

    private PostTagService postTagService;

    public Page<Tag> getList(Integer pageNum, Integer pageSize, String keyword) {

        PageHelper.startPage(pageNum, pageSize);

        List<Tag> tagList = tagMapper.getTagList(keyword);

        PageInfo<Tag> page = new PageInfo<>();

        return Page.of(page, tagList);
    }

    public int delete(Integer tagId, User user) {
        return Optional.ofNullable(tagMapper.selectByPrimaryKey(tagId))
                .filter(tag -> tag.getUserId().equals(user.getId()))
                .map(Tag::getId)
                .map(this::delete)
                .orElse(0);
    }

    @Transactional
    public int delete(Integer tagId) {
        return Optional.of(tagId)
                .map(postTagService::deleteAllByTagId)
                .map(tagMapper::deleteByPrimaryKey)
                .orElse(0);
    }

    /**
     * @param tagDesc
     * @return a tag Object with its database id
     */
    Tag add(String tagDesc) {
        Tag tag = new Tag();
        tag.setTagDesc(tagDesc);

        if (tagMapper.selectCount(tag) == 0) {
            tagMapper.insertUseGeneratedKeys(tag);
            return tag;
        }

        return tagMapper.selectOne(tag);
    }

    public int update(Integer tagId, String tagDesc) {

        Tag tag = new Tag();
        tag.setTagDesc(tagDesc);

        if (tagMapper.selectCount(tag) != 0)
            throw new BusinessException(Result.ErrCode.TAG_EXISTS);

        tag.setId(tagId);
        return tagMapper.updateByPrimaryKeySelective(tag);
    }
}
