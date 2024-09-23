package org.example.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "t_user")
public class UserEntity {

    @Id
    @Column
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username")
    private String username;

    @OneToMany(mappedBy = AnswerEntity.USER_ENTITY_FIELD_NAME)
    private List<AnswerEntity> answers = new ArrayList<>();

}
