package org.example.repository;

import org.example.dto.MarkDto;
import org.example.model.Mark;


import java.util.LinkedHashSet;

public interface MarkRepository {
    boolean create(MarkDto markDto);

    Mark getById(int id);

    LinkedHashSet<Mark> getAll();

    void update(MarkDto markDto);

    void deleteById(int id);
}
