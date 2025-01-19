package com.algo.application.implementations;

import com.algo.application.services.LabyrinthService;
import com.algo.domain.common.Coordinates;
import com.algo.domain.entities.Labyrinth;
import com.algo.domain.entities.Node;
import com.algo.domain.common.DifficultyLevel;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ApplicationScoped
@Slf4j
public class LabyrinthServiceImpl implements LabyrinthService {

    @Override
    public Labyrinth createLabyrinth(String theme, int difficultyLevel) {
        int height = DifficultyLevel.getHeight(difficultyLevel);
        int width = DifficultyLevel.getWidth(difficultyLevel);
        List<List<Node>> nodes = initializeNodes(height, width);

        Coordinates start = generateMaze(nodes, height, width);

        Coordinates end = placeStartAndEnd(nodes, height, width, start);

        Labyrinth labyrinth = new Labyrinth(
                new ObjectId(),
                nodes,
                start,
                end,
                width,
                height
        );

        log.info("Labyrinth created with theme: {}, difficultyLevel: {}", theme, difficultyLevel);
        return labyrinth;
    }

    @Override
    public List<Coordinates> getShortestPath(String labyrinthId) {
        return new ArrayList<>(); // Placeholder for pathfinding implementation
    }

    private List<List<Node>> initializeNodes(int height, int width) {
        List<List<Node>> nodes = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            List<Node> row = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                Coordinates coordinates = new Coordinates(i, j);
                List<Coordinates> neighbours = findNeighbours(coordinates,height,width);
                Node node = new Node(coordinates, true, '█', neighbours);
                row.add(node);
            }
            nodes.add(row);
        }
        return nodes;
    }
    private List<Coordinates> findNeighbours(Coordinates coordinates, int height, int width) {
        List<Coordinates> neighbours = new ArrayList<>();
        int x = coordinates.getX();
        int y = coordinates.getY();

        if (x + 1 < height) {
            neighbours.add(new Coordinates(x + 1, y));
        }
        if (x - 1 >= 0) {
            neighbours.add(new Coordinates(x - 1, y));
        }
        if (y + 1 < width) {
            neighbours.add(new Coordinates(x, y + 1));
        }
        if (y - 1 >= 0) {
            neighbours.add(new Coordinates(x, y - 1));
        }
        return neighbours;
    }

    private Coordinates generateMaze(List<List<Node>> nodes, int height, int width) {
        Random random = new Random();
        int startX = random.nextInt(height);
        int startY = width - 1;

        nodes.get(startX).get(startY).setWall(false);
        nodes.get(startX).get(startY).setValue('E');

        List<int[]> frontier = new ArrayList<>();
        frontier.add(new int[]{startX, startY});

        boolean[][] visited = new boolean[height][width];
        visited[startX][startY] = true;

        int[][] directions = {{0, 2}, {2, 0}, {-2, 0}, {0, -2}};

        while (!frontier.isEmpty()) {
            int[] current = frontier.remove(random.nextInt(frontier.size()));
            int x = current[0], y = current[1];
            randomShuffle(directions, random);

            for (int[] dir : directions) {
                int nx = x + dir[0];
                int ny = y + dir[1];
                int mx = x + dir[0] / 2;
                int my = y + dir[1] / 2;

                if (isValid(nx, ny, height, width) && !visited[nx][ny]) {
                    nodes.get(mx).get(my).setWall(false);
                    nodes.get(mx).get(my).setValue(' ');

                    nodes.get(nx).get(ny).setWall(false);
                    nodes.get(nx).get(ny).setValue(' ');

                    visited[nx][ny] = true;
                    frontier.add(new int[]{nx, ny});
                }
            }
        }
        return new Coordinates(startX, startY);
    }

    private Coordinates placeStartAndEnd(List<List<Node>> nodes, int height, int width, Coordinates start) {
        Random random = new Random();
        int endX = random.nextInt(height);
        int endY = random.nextBoolean() ? 0 : random.nextInt(width);

        nodes.get(endX).get(endY).setWall(false);
        nodes.get(endX).get(endY).setValue('S');

        return new Coordinates(endX, endY);
    }

    private boolean isValid(int x, int y, int height, int width) {
        return x >= 0 && x < height && y >= 0 && y < width;
    }

    private void randomShuffle(int[][] directions, Random random) {
        for (int i = directions.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int[] temp = directions[i];
            directions[i] = directions[j];
            directions[j] = temp;
        }
    }
}
