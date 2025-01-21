package com.algo.application.implementations;

import com.algo.application.services.LabyrinthService;
import com.algo.domain.common.Coordinates;
import com.algo.domain.common.utils.WordSelector;
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
    public void persistLabyrinth(Labyrinth labyrinth) {
        labyrinthRepository.createLabyrinth(labyrinth);
    }

    @Override
    public void deleteLabyrinth(String id) {
        labyrinthRepository.deleteLabyrinth(new ObjectId(id));
    }

    @Override
    public Labyrinth createLabyrinth(String theme, int difficultyLevel) throws Exception {
        int height = DifficultyLevel.getHeight(difficultyLevel);
        int width = DifficultyLevel.getWidth(difficultyLevel);
        List<List<Node>> nodes = initializeLabyrinth(height, width);
        Coordinates start = placeLabyrinthEntree(nodes, height, width);
        generateLabyrinthStructure(nodes, height, width, start);
        Coordinates end = placeLabyrinthExit(nodes, height, width);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Node currentNode = nodes.get(i).get(j);
                if (currentNode.isWall()) continue;
                determineNodeNeighbors(currentNode, nodes, height, width);
            }
        }
        List<Coordinates> shortestPath;
        try {
            Node startNode = nodes.get(start.getX()).get(start.getY());
            Node endNode = nodes.get(end.getX()).get(end.getY());
            shortestPath = calculateShortestPath(startNode, endNode, nodes);
        } catch (Exception e) {
            log.error("Error generating shortest path for labyrinth", e);
            throw e;
        }
        List<String> selectedWords = WordSelector.selectWords(theme , difficultyLevel,4);
        placeWordsInLabyrinth(selectedWords, nodes, height, width);
        placeRandomLowercaseLetters(nodes, height, width);
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

    private List<Coordinates> calculateShortestPath(Node start, Node end, List<List<Node>> nodes) throws Exception {

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

    private void generateLabyrinthStructure(List<List<Node>> nodes, int height, int width, Coordinates start) {
        Random random = new Random();
        List<int[]> frontier = new ArrayList<>();
        frontier.add(new int[]{start.getX(), start.getY()});

        boolean[][] visited = new boolean[height][width];
        visited[start.getX()][start.getY()] = true;

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

    private Coordinates placeLabyrinthEntree(List<List<Node>> nodes, int height, int width) {
        Random random = new Random();
        int startX = random.nextInt(height);
        int startY = width - 1;
        Node startNode = nodes.get(startX).get(startY);
        startNode.setWall(false);
        startNode.setValue('E');
        return new Coordinates(startX, startY);
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

    private void determineNodeNeighbors(Node node, List<List<Node>> nodes, int height, int width) {
        List<Coordinates> neighbors = new LinkedList<>();
        int[][] directions = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
        for (int[] dir : directions) {
            int nx = node.getCoordinates().getX() + dir[0];
            int ny = node.getCoordinates().getY() + dir[1];
            if (isValidNodePlacement(nx, ny, height, width) && !nodes.get(nx).get(ny).isWall()) {
                neighbors.add(nodes.get(nx).get(ny).getCoordinates());
            }
        }
        node.setNeighbors(neighbors);
    }

    private void placeWordsInLabyrinth(List<String> words, List<List<Node>> nodes, int height, int width) throws Exception {
        Random random = new Random();
        for (String word : words) {
            boolean wordPlaced = false;
            for (int attempts = 0; attempts < 100; attempts++) {
                int x = random.nextInt(height);
                int y = random.nextInt(width);
                if (nodes.get(x).get(y).isWall()) continue;
                List<int[]> directions = Arrays.asList(new int[]{0, 1},  new int[]{1, 0},  new int[]{0, -1}, new int[]{-1, 0});
                Collections.shuffle(directions);
                for (int[] direction : directions) {
                    if (canPlaceWord(word, nodes, x, y, direction, height, width)) {
                        placeWord(word, nodes, x, y, direction);
                        wordPlaced = true;
                        break;
                    }
                }
                if (wordPlaced) break;
            }
            if (!wordPlaced) throw new Exception("Could not place word in labyrinth");
        }
    }

    private boolean canPlaceWord(String word, List<List<Node>> nodes, int x, int y, int[] direction, int height, int width) {
        int dx = direction[0];
        int dy = direction[1];
        for (int i = 0; i < word.length(); i++) {
            int nx = x + i * dx;
            int ny = y + i * dy;
            if (!isValidNodePlacement(nx, ny, height, width) || nodes.get(nx).get(ny).isWall())  return false;
        }
        return true;
    }

    private void placeWord(String word, List<List<Node>> nodes, int x, int y, int[] direction) {
        int dx = direction[0];
        int dy = direction[1];
        for (int i = 0; i < word.length(); i++) {
            int nx = x + i * dx;
            int ny = y + i * dy;
            nodes.get(nx).get(ny).setValue(word.charAt(i));
        }
    }

    private void placeRandomLowercaseLetters(List<List<Node>> nodes, int height, int width) {
        Random random = new Random();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Node node = nodes.get(i).get(j);
                if (node.isWall() || Character.isLetter(node.getValue())) continue;
                node.setValue((char) (random.nextInt(26) + 'a'));
            }
        }
    }
}
