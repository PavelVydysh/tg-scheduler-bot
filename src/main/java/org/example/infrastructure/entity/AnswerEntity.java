package org.example.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "answer")
public class AnswerEntity {

    public static final String POLL_ID_COLUMN_NAME = "poll_id";

    public static final String POLL_ENTITY_FIELD_NAME = "poll";

    public static final String USER_ID_COLUMN_NAME = "user_id";

    public static final String USER_ENTITY_FIELD_NAME = "user";

    public static final String ANSWER_TYPE_ID_COLUMN_NAME = "answer_type_id";

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = POLL_ID_COLUMN_NAME)
    private PollEntity poll;

    @ManyToOne
    @JoinColumn(name = USER_ID_COLUMN_NAME)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = ANSWER_TYPE_ID_COLUMN_NAME)
    private AnswerTypeEntity answerType;

}
