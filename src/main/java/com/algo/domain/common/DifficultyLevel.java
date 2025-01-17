package com.algo.domain.common;

public abstract class DifficultyLevel {

    public static final int EASY = 1;
    public static final int MEDIUM = 2;
    public static final int HARD = 3;



    public static final int EASY_WIDTH = 10;
    public static final int EASY_HEIGHT = 20;

    public static final int MEDIUM_WIDTH = 20;
    public static final int MEDIUM_HEIGHT = 40;

    public static final int HARD_WIDTH = 40;
    public static final int HARD_HEIGHT = 60;


    public static final int EASY_OBSTACLES = 10;
    public static final int MEDIUM_OBSTACLES = 20;
    public static final int HARD_OBSTACLES = 40;


    public static final int EASY_BASE_SCORE = 5;
    public static final int MEDIUM_BASE_SCORE = 10;
    public static final int HARD_BASE_SCORE = 20;


    public static int getBaseScore(int difficultyLevel) {
        return switch (difficultyLevel) {
            case MEDIUM -> MEDIUM_BASE_SCORE;
            case HARD -> HARD_BASE_SCORE;
            default -> EASY_BASE_SCORE;
        };
    }

    public static int getWidth(int difficultyLevel) {
        return switch (difficultyLevel) {
            case MEDIUM -> MEDIUM_WIDTH;
            case HARD -> HARD_WIDTH;
            default -> EASY_WIDTH;
        };
    }

    public static int getHeight(int difficultyLevel) {
        return switch (difficultyLevel) {
            case MEDIUM -> MEDIUM_HEIGHT;
            case HARD -> HARD_HEIGHT;
            default -> EASY_HEIGHT;
        };
    }

    public static int getObstacles(int difficultyLevel) {
        return switch (difficultyLevel) {
            case MEDIUM -> MEDIUM_OBSTACLES;
            case HARD -> HARD_OBSTACLES;
            default -> EASY_OBSTACLES;
        };
    }
}
