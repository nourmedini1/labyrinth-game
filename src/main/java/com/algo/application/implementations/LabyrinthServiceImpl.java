package com.algo.application.implementations;

import com.algo.application.services.LabyrinthService;
import com.algo.domain.common.Coordinates;
import com.algo.domain.entities.Labyrinth;
import com.algo.domain.entities.Node;
import com.algo.domain.common.DifficultyLevel;
import com.algo.domain.repositories.LabyrinthRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import java.util.*;


@ApplicationScoped
@Slf4j
public class LabyrinthServiceImpl implements LabyrinthService {
    @Inject
    LabyrinthRepository labyrinthRepository;

    @Override
    public Labyrinth getLabyrinth(String id) {
        return labyrinthRepository.findLabyrinthById(new ObjectId(id));
    }

    @Override
    public Labyrinth createLabyrinth(String theme, int difficultyLevel) {
        int height = DifficultyLevel.getHeight(difficultyLevel);
        int width = DifficultyLevel.getWidth(difficultyLevel);
        List<List<Node>> nodes = initializeLabyrinth(height, width);
        Coordinates start = generateLabyrinthStructure(nodes, height, width);
        Coordinates end = placeLabyrinthExit(nodes, height, width);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Node node = nodes.get(i).get(j);
                if (node.isWall()) {
                    continue;
                }
                List<Coordinates> neighbors = getNeighbors(node.getCoordinates(), nodes, height, width);
                node.setNeighbors(neighbors);
            }
        }
        log.info("Generating labyrinth with start: {} and end: {}, with nodes {}", start, end, nodes);
        List<Coordinates> shortestPath = new LinkedList<>();
        try {
            Node startNode = nodes.get(start.getX()).get(start.getY());
            Node endNode = nodes.get(end.getX()).get(end.getY());
            shortestPath = getShortestPath(startNode, endNode, nodes);
        } catch (Exception e) {
            log.error("Error generating shortest path for labyrinth", e);
        }
        return Labyrinth.builder()
                .id(new ObjectId())
                .height(height)
                .width(width)
                .nodes(nodes)
                .start(start)
                .end(end)
                .shortestPath(shortestPath)
                .build();
    }



    @Override
    public void persistLabyrinth(Labyrinth labyrinth) {
        labyrinthRepository.createLabyrinth(labyrinth);
    }

    @Override
    public void deleteLabyrinth(String id) {
        labyrinthRepository.deleteLabyrinth(new ObjectId(id));
    }

    private List<Coordinates> getShortestPath(Node start, Node end, List<List<Node>> nodes) throws Exception {

        Map<Coordinates, Coordinates> prev = new HashMap<>();
        Queue<Node> queue = new LinkedList<>();
        Set<Coordinates> visited = new HashSet<>();
        queue.add(start);
        visited.add(start.getCoordinates());
        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (current.getCoordinates().equals(end.getCoordinates())) {
                return reconstructPath(prev, start.getCoordinates(), end.getCoordinates());
            }

            for (Coordinates neighborCoords : current.getNeighbors()) {
                Node neighbor = nodes.get(neighborCoords.getX()).get(neighborCoords.getY());
                if (neighbor.isWall() || visited.contains(neighborCoords)) continue;

                visited.add(neighbor.getCoordinates());
                queue.add(neighbor);
                prev.put(neighbor.getCoordinates(), current.getCoordinates());
            }
        }

        throw new Exception("No path found");

    }

    private LinkedList<Coordinates> reconstructPath(Map<Coordinates, Coordinates> prev, Coordinates start, Coordinates end) {
        LinkedList<Coordinates> path = new LinkedList<>();
        for (Coordinates at = end; at != null; at = prev.get(at)) {
            path.addFirst(at);
        }
        if (!path.isEmpty() && !path.getFirst().equals(start)) {
            return new LinkedList<>();
        }

        return path;
    }

    private List<List<Node>> initializeLabyrinth(int height, int width) {
        List<List<Node>> nodes = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            List<Node> row = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                Coordinates coordinates = new Coordinates(i, j);
                Node node = new Node(coordinates, true, '█', new ArrayList<>());
                row.add(node);
            }
            nodes.add(row);
        }
        return nodes;
    }

    private Coordinates generateLabyrinthStructure(List<List<Node>> nodes, int height, int width) {
        Random random = new Random();
        int startX = random.nextInt(height);
        int startY = width - 1;

        Node startNode = nodes.get(startX).get(startY);
        startNode.setWall(false);
        startNode.setValue('E');

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

                if (isValidNodePlacement(nx, ny, height, width) && !visited[nx][ny]) {
                    Node mNode = nodes.get(mx).get(my);
                    mNode.setWall(false);
                    mNode.setValue(' ');

                    Node nNode = nodes.get(nx).get(ny);
                    nNode.setWall(false);
                    nNode.setValue(' ');

                    visited[nx][ny] = true;
                    frontier.add(new int[]{nx, ny});
                }
            }
        }
        return new Coordinates(startX, startY);
    }

    private Coordinates placeLabyrinthExit(List<List<Node>> nodes, int height, int width) {
        Random random = new Random();
        int endX = random.nextInt(height);
        int endY = random.nextBoolean() ? 0 : random.nextInt(width);

        Node endNode = nodes.get(endX).get(endY);
        endNode.setWall(false);
        endNode.setValue('S');

        return new Coordinates(endX, endY);
    }

    private boolean isValidNodePlacement(int x, int y, int height, int width) {
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

    private List<Coordinates> getNeighbors(Coordinates coordinates, List<List<Node>> nodes, int height, int width) {
        List<Coordinates> neighbors = new LinkedList<>();
        int[][] directions = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
        for (int[] dir : directions) {
            int nx = coordinates.getX() + dir[0];
            int ny = coordinates.getY() + dir[1];
            if (isValidNodePlacement(nx, ny, height, width) && !nodes.get(nx).get(ny).isWall()) {
                neighbors.add(nodes.get(nx).get(ny).getCoordinates());
            }
        }
        return neighbors;
    }
}
