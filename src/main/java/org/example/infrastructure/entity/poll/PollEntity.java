package org.example.infrastructure.entity.poll;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = PollEntity.POLL_TABLE_NAME)
public class PollEntity {

    public static final String POLL_TABLE_NAME = "poll";

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID pollId;

    @Column
    private String tgChatId;

    @Column
    private OffsetDateTime creationDate;

    @OneToMany(mappedBy = PollVersionEntity.POLL_ENTITY_FIELD_NAME)
    private List<PollVersionEntity> pollVersionEntities = new ArrayList<>();

}
