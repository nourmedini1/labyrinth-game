package com.algo.domain.repositories;

import com.algo.domain.entities.Player;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.faulttolerance.Bulkhead;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Timeout;

import java.util.Optional;

@ApplicationScoped
@Transactional
@Slf4j
@Bulkhead
@CircuitBreaker
@Timeout
public class PlayerRepository implements PanacheMongoRepositoryBase<Player, ObjectId> {

        public Player findPlayerById(ObjectId id) {
            return findById(id);
        }

        public Optional<Player> findPlayerByName(String name) {
            return find("name", name).firstResultOptional();
        }

        public void createPlayer(Player player) {
            persist(player);
        }

        public void updatePlayer(Player player) {
            update(player);
        }

        public void deletePlayer(ObjectId id) {
            deleteById(id);
        }
}
