package org.example.repository;

import org.example.dto.MarkDto;
import org.example.model.Mark;


import java.util.LinkedHashSet;
import java.util.UUID;

public interface MarkRepository {
    boolean create(MarkDto markDto);

    Mark getById(UUID id);

    LinkedHashSet<Mark> getAll();

    void update(MarkDto markDto);

    void deleteById(UUID id);
}
