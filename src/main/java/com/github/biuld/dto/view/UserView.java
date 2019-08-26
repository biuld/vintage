package com.github.biuld.dto.view;

import com.github.biuld.dto.CommentContentIdPair;
import com.github.biuld.dto.PostTitleIdPair;
import com.github.biuld.model.Role;
import com.github.biuld.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserView extends User {

    private List<Role> roleList;

    private List<PostTitleIdPair> postPairList;

    private List<CommentContentIdPair> commentPairList;
}
