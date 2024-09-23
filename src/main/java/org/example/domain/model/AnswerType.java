package org.example.domain.model;

import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AnswerType implements Comparable<AnswerType>{

    private UUID id;

    private Integer position;

    private String title;

    private List<Integer> versions;

    public Integer getLastVersion() {
        return Collections.max(versions);
    }

    @Override
    public int compareTo(@NotNull AnswerType o) {
        return position - o.getPosition();
    }
}
