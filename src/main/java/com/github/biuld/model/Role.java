package com.github.biuld.model;

import javax.persistence.*;
import lombok.Data;

@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "role_name")
    private String roleName;
}