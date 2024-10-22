package org.example.infrastructure.entity.poll;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = PollVersionEntity.POLL_VERSION_TABLE_NAME)
public class PollVersionEntity {

    public static final String POLL_VERSION_TABLE_NAME = "poll_version";
    public static final String ID_COLUMN_NAME = "poll_version_id";
    public static final String POLL_ID_COLUMN_NAME = "poll_id";
    public static final String VERSION_COLUMN_NAME = "version";
    public static final String POLL_ENTITY_FIELD_NAME = "pollEntity";

    @Id
    @Column(name = ID_COLUMN_NAME)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID pollVersionId;

    @ManyToOne
    @JoinColumn(name = POLL_ID_COLUMN_NAME)
    private PollEntity pollEntity;

    @Column(name = VERSION_COLUMN_NAME)
    private Integer version;

    @ManyToMany(mappedBy = AvailableAnswerEntity.POLL_VERSION_ENTITIES_FIELD_NAME)
    private List<AvailableAnswerEntity> availableAnswerEntities = new ArrayList<>();

}
