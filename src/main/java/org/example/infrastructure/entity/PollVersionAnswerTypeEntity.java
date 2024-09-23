package org.example.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "poll_version_answer_type")
public class PollVersionAnswerTypeEntity {

    public static final String ANSWER_TYPE_ID_COLUMN_NAME = "answer_type_id";
    public static final String ANSWER_TYPE_FIELD_NAME = "answerType";

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Integer version;

    @ManyToOne
    @JoinColumn(name = ANSWER_TYPE_ID_COLUMN_NAME)
    private AnswerTypeEntity answerType;

}
