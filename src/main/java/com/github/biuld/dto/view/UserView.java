package com.github.biuld.dto.view;

import com.github.biuld.dto.CommentContentIdPair;
import com.github.biuld.dto.PostTitleIdPair;
import com.github.biuld.model.Role;
import com.github.biuld.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Data
public class UserView  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    private String avatar;

    private String email;

    private Integer verified;

    private List<Role> roleList;

    private List<PostTitleIdPair> postPairList;

    private List<CommentContentIdPair> commentPairList;
}
