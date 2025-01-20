package com.algo.application.services;


import com.algo.domain.entities.Labyrinth;



public interface LabyrinthService {

    Labyrinth getLabyrinth(String id);

    Labyrinth createLabyrinth(String theme, int difficultyLevel);


    void persistLabyrinth(Labyrinth labyrinth);

    void deleteLabyrinth(String id);


}
