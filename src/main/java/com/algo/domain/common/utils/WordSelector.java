package com.algo.domain.common.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class WordSelector {

    private static final String THEMES_PATH = "src/main/resources/themes/";

    private static List<String> loadWordsFromFile(String theme, String difficultyLevel) throws FileNotFoundException {
        theme = theme.toLowerCase();
        String path = THEMES_PATH + theme + "/" + difficultyLevel + "/words.txt";
        List<String> words = new ArrayList<>();
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                words.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        }
        return words;
    }

    public static List<String> selectWords(String theme, int difficultyLevel, int numberOfWordsSelected) throws FileNotFoundException {
        String difficulty = parseDifficultyLevel(difficultyLevel);
        List<String> words = loadWordsFromFile(theme, difficulty);
        List<String> selectedWords = new ArrayList<>();
        for (int i = 0; i < numberOfWordsSelected; i++) {
            int randomIndex = (int) (Math.random() * words.size());
            selectedWords.add(words.get(randomIndex));
            words.remove(randomIndex);
        }
        return selectedWords;
    }

    private static String parseDifficultyLevel(int difficultyLevel) {
        return switch (difficultyLevel) {
            case 2 -> "medium";
            case 3 -> "hard";
            default -> "easy";
        };
    }
}
