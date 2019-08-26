package com.github.biuld.service.bi;

import com.github.biuld.mapper.PostMapper;
import com.github.biuld.mapper.PostTagMapMapper;
import com.github.biuld.mapper.TagMapper;
import com.github.biuld.model.Post;
import com.github.biuld.model.PostTagMap;
import com.github.biuld.model.Tag;
import com.github.biuld.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class PostTagService {

    private PostTagMapMapper postTagMapMapper;

    private TagMapper tagMapper;

    public void addOneByPair(Integer postId, Integer tagId) {
        PostTagMap map = new PostTagMap();
        map.setPostId(postId);
        map.setTagId(tagId);

        if (postTagMapMapper.selectCount(map) == 0) {
            postTagMapMapper.insertSelective(map);
        }
    }

    public Integer deleteAllByTagId(Integer tagId) {
        PostTagMap map = new PostTagMap();
        map.setTagId(tagId);
        postTagMapMapper.delete(map);
        return tagId;
    }

    public Integer deleteAllByPostId(Integer postId) {
        PostTagMap map = new PostTagMap();
        map.setPostId(postId);
        postTagMapMapper.delete(map);
        return postId;
    }

    public List<Tag> getTagByPostId(Integer postId) {
        return Optional.of(new PostTagMap())
                .map(map -> {
                    map.setPostId(postId);
                    return map;
                }).map(postTagMapMapper::select)
                .stream()
                .flatMap(Collection::stream)
                .map(PostTagMap::getTagId)
                .map(tagMapper::selectByPrimaryKey)
                .collect(Collectors.toList());
    }
}
