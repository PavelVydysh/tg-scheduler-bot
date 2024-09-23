package org.example.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "poll")
public class PollEntity {

    public static final String CREATED_DATE_COLUMN_NAME = "created_date";
    public static final String CHAT_ID_COLUMN_NAME = "chat_id";

    @Id
    @Column
    private String id;

    @Column(name = CREATED_DATE_COLUMN_NAME)
    private LocalDate createdDate;

    @Column
    private String title;

    @Column
    private Integer version;

    @Column(name = CHAT_ID_COLUMN_NAME)
    private String chatId;

    @Column(insertable = false, updatable = false)
    @OneToMany(mappedBy = AnswerEntity.POLL_ENTITY_FIELD_NAME)
    private List<AnswerEntity> answers = new ArrayList<>();

}
