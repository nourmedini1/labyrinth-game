package com.algo.domain.repositories;

import com.algo.domain.entities.Labyrinth;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.faulttolerance.Bulkhead;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Timeout;

@ApplicationScoped
//@Transactional
@Slf4j
@Bulkhead
//@CircuitBreaker
@Timeout
public class LabyrinthRepository implements PanacheMongoRepositoryBase<Labyrinth, ObjectId> {

    public Labyrinth findLabyrinthById(ObjectId id) {
        return findById(id);
    }

    public void createLabyrinth(Labyrinth labyrinth) {
        persist(labyrinth);
    }

    public void updateLabyrinth(Labyrinth labyrinth) {
        update(labyrinth);
    }

    public void deleteLabyrinth(ObjectId id) {
        deleteById(id);
    }
}
