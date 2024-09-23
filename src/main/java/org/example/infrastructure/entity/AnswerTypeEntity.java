package org.example.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "answer_type")
public class AnswerTypeEntity {

    public static final String ID_FIELD_NAME = "id";
    public static final String POSITION_FIELD_NAME = "position";
    public static final String TITLE_FIELD_NAME = "title";
    public static final String VERSIONS_FIELD_NAME = "versions";

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private Integer position;

    @Column
    private String title;

    @OneToMany(mappedBy = PollVersionAnswerTypeEntity.ANSWER_TYPE_FIELD_NAME)
    private List<PollVersionAnswerTypeEntity> versions;
}
