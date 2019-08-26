package com.github.biuld.model;

import javax.persistence.*;
import lombok.Data;

@Table(name = "user_role_map")
@Data
public class UserRoleMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "role_id")
    private Integer roleId;
}