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
                List<Node> neighbors = getNeighbors(node.getCoordinates(), nodes, height, width);
                node.setNeighbors(neighbors);
            }
        }
        List<Coordinates> shortestPath = new LinkedList<>();
        try {
            shortestPath = getShortestPath(start, end, nodes);
        } catch (Exception e) {
            log.error("Dijkstra's algorithm couldn't converge ", e);
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

    private List<Coordinates> getShortestPath(Coordinates start, Coordinates end, List<List<Node>> nodes) throws Exception {
       Map<Coordinates, Integer> distances = new HashMap<>();
    Map<Coordinates, Coordinates> previous = new HashMap<>();
    PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(node -> distances.getOrDefault(node.getCoordinates(), Integer.MAX_VALUE)));
    Set<Coordinates> visited = new HashSet<>();

    for (List<Node> row : nodes) {
        for (Node node : row) {
            distances.put(node.getCoordinates(), Integer.MAX_VALUE);
        }
    }
    distances.put(start, 0);
    queue.add(new Node(start, true, ' ', new LinkedList<>()));

    while (!queue.isEmpty()) {
        Node current = queue.poll();
        Coordinates currentCoord = current.getCoordinates();

        if (visited.contains(currentCoord)) {
            continue;
        }
        visited.add(currentCoord);

        if (currentCoord.equals(end)) {
            break;
        }

        for (Node neighbor : current.getNeighbors()) {
            if (neighbor.isWall() || visited.contains(neighbor.getCoordinates())) {
                continue;
            }

            int newDist = distances.get(currentCoord) + 1;
            if (newDist < distances.get(neighbor.getCoordinates())) {
                distances.put(neighbor.getCoordinates(), newDist);
                previous.put(neighbor.getCoordinates(), currentCoord);
                queue.add(neighbor);
            }
        }
    }

    List<Coordinates> path = new LinkedList<>();
    for (Coordinates at = end; at != null; at = previous.get(at)) {
        path.add(0, at);
    }

    if (path.get(0).equals(start)) {
        return path;
    } else {
        throw new Exception("Dijkstra's algorithm couldn't converge");
    }


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

                if (isValidNodePlacement(nx, ny, height, width) && !visited[nx][ny]) {
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

    private Coordinates placeLabyrinthExit(List<List<Node>> nodes, int height, int width) {
        Random random = new Random();
        int endX = random.nextInt(height);
        int endY = random.nextBoolean() ? 0 : random.nextInt(width);

        nodes.get(endX).get(endY).setWall(false);
        nodes.get(endX).get(endY).setValue('S');

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

    private List<Node> getNeighbors(Coordinates coordinates, List<List<Node>> nodes, int height, int width) {
        List<Node> neighbors = new LinkedList<>();
        int[][] directions = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
        for (int[] dir : directions) {
            int nx = coordinates.getX() + dir[0];
            int ny = coordinates.getY() + dir[1];
            if (isValidNodePlacement(nx, ny, height, width) && !nodes.get(nx).get(ny).isWall()) {
                neighbors.add(nodes.get(nx).get(ny));
            }
        }
        return neighbors;
    }
}
