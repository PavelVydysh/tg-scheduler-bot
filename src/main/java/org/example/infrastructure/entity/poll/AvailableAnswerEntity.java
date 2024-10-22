package org.example.infrastructure.entity.poll;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = AvailableAnswerEntity.AVAILABLE_ANSWER_TABLE_NAME)
public class AvailableAnswerEntity {

    public static final String AVAILABLE_ANSWER_TABLE_NAME = "available_answer";
    public static final String ID_COLUMN_NAME = "available_answer_id";
    public static final String TITLE_COLUMN_NAME = "title";
    public static final String POSITION_COLUMN_NAME = "position";
    public static final String POLL_VERSION_JOIN_TABLE_NAME = "poll_version_and_available_answer";
    public static final String POLL_VERSION_JOIN_TABLE_JOIN_COLUMN_NAME = "poll_version_id";
    public static final String POLL_VERSION_JOIN_TABLE_INVERSE_JOIN_COLUMN_NAME = "available_answer_id";
    public static final String POLL_VERSION_ENTITIES_FIELD_NAME = "pollVersionEntities";

    @Id
    @Column(name = ID_COLUMN_NAME)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID availableAnswerId;

    @Column(name = TITLE_COLUMN_NAME)
    private String title;

    @Column(name = POSITION_COLUMN_NAME)
    private Integer position;

    @ManyToMany
    @JoinTable(
            name = POLL_VERSION_JOIN_TABLE_NAME,
            joinColumns = @JoinColumn(name = POLL_VERSION_JOIN_TABLE_JOIN_COLUMN_NAME),
            inverseJoinColumns = @JoinColumn(name = POLL_VERSION_JOIN_TABLE_INVERSE_JOIN_COLUMN_NAME)
    )
    private List<PollVersionEntity> pollVersionEntities = new ArrayList<>();

}
