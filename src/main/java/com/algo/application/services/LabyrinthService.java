package com.algo.application.services;

import com.algo.domain.common.Coordinates;
import com.algo.domain.entities.Labyrinth;

import java.util.List;

public interface LabyrinthService {

    Labyrinth createLabyrinth(String theme, int difficultyLevel);

    List<Coordinates> getShortestPath(String labyrinthId);


}
