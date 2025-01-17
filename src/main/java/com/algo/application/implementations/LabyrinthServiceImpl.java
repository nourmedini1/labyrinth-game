package com.algo.application.implementations;

import com.algo.application.services.LabyrinthService;
import com.algo.domain.common.Coordinates;
import com.algo.domain.entities.Labyrinth;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@ApplicationScoped
@Slf4j
public class LabyrinthServiceImpl implements LabyrinthService {
    @Override
    public Labyrinth createLabyrinth(String theme, int difficultyLevel) {
        return null;
    }

    @Override
    public List<Coordinates> getShortestPath(String labyrinthId) {
        return List.of();
    }
}
