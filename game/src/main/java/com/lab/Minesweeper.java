package com.lab;

import java.util.Random;
import java.util.Scanner;
import java.util.Random;
import java.io.InputStream;

public class Minesweeper {
    private static final char SAFE_CELL = '.';
    private static final char MINE_CELL = 'X';
    private static final char HIDDEN_CELL = '#';
    private static final int IS_SAFE = 0;
    private static final int IS_MINE = 1;
    int fieldX, fieldY;
    int[][] cells;
    String fieldFileName;

    public int rows, cols;
    public int[][] board;
    public boolean[][] revealed;

    public Minesweeper(int rows, int cols, int mineCount) {
        this.rows = rows;
        this.cols = cols;
        board = new int[rows][cols];
        revealed = new boolean[rows][cols];
        placeRandomMines(mineCount);
    }

    public Minesweeper(String fieldFile) {
        this.fieldFileName = fieldFile;
        initFromFile(fieldFileName);
    }

    public Minesweeper(int fieldX, int fieldY) {
        this.fieldX = fieldX;
        this.fieldY = fieldY;
        this.cells = new int[fieldX][fieldY];
        for (int i = 0; i < fieldX; i++) {
            for (int j = 0; j < fieldY; j++) {
                cells[i][j] = IS_SAFE;
            }
        }
    }

    /*
     * void displayField() {
     * for (int i = 0; i < fieldX; i++) {
     * for (int j = 0; j < fieldY; j++) {
     * if (cells[i][j] == IS_SAFE) {
     * System.out.print(SAFE_CELL);
     * } else {
     * System.out.print(MINE_CELL);
     * }
     * }
     * System.out.println("");
     * }
     * }
     */

    void setMineCell(int x, int y) {
        cells[x][y] = IS_MINE;
    }

    void initFromFile(String mineFieldFile) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(mineFieldFile);
        Scanner scanner = new Scanner(is);

        fieldX = Integer.parseInt(scanner.nextLine());
        fieldY = Integer.parseInt(scanner.nextLine());

        cells = new int[fieldX][fieldY];
        for (int i = 0; i < fieldX; i++) {
            String line = scanner.nextLine();
            for (int j = 0; j < fieldY; j++) {
                if (line.charAt(j) == 'X') {
                    cells[i][j] = IS_MINE;
                } else {
                    cells[i][j] = IS_SAFE;
                }
            }
        }
    }

    private void placeRandomMines(int mineCount) {
        Random random = new Random();
        int placed = 0;
        while (placed < mineCount) {
            int x = random.nextInt(rows);
            int y = random.nextInt(cols);
            if (board[x][y] != IS_MINE) {
                board[x][y] = IS_MINE;
                placed++;
            }
        }
    }

    public void displayField() {
        System.out.println();
        System.out.print("   ");
        for (int j = 0; j < cols; j++) {
            System.out.print(j + " ");
        }
        System.out.println();
        for (int i = 0; i < rows; i++) {
            System.out.print(i + "  ");
            for (int j = 0; j < cols; j++) {
                if (!revealed[i][j]) {
                    System.out.print(HIDDEN_CELL + " ");
                } else {
                    if (board[i][j] == IS_MINE) {
                        System.out.print(MINE_CELL + " ");
                    } else {
                        int count = countAdjacentMines(i, j);
                        if (count == 0) {
                            System.out.print(SAFE_CELL + " ");
                        } else {
                            System.out.print(count + " ");
                        }
                    }
                }
            }
            System.out.println();
        }
    }

    public int countAdjacentMines(int x, int y) {
        int count = 0;
        int[] dx = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int[] dy = { -1, 0, 1, -1, 1, -1, 0, 1 };

        for (int i = 0; i < 8; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (nx >= 0 && nx < rows && ny >= 0 && ny < cols) {
                if (board[nx][ny] == IS_MINE) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean openCell(int x, int y) {
        if (x < 0 || x >= rows || y < 0 || y >= cols || revealed[x][y])
            return false;

        revealed[x][y] = true;

        if (board[x][y] == IS_MINE)
            return true;

        if (countAdjacentMines(x, y) == 0) {
            int[] dx = { -1, -1, -1, 0, 0, 1, 1, 1 };
            int[] dy = { -1, 0, 1, -1, 1, -1, 0, 1 };
            for (int i = 0; i < 8; i++) {
                openCell(x + dx[i], y + dy[i]);
            }
        }
        return false;
    }

    private int countMines() {
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == IS_MINE) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean isWin() {
        int countRevealed = 0;
        int totalSafeCells = (rows * cols) - countMines();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (revealed[i][j]) {
                    countRevealed++;
                }
            }
        }
        return countRevealed == totalSafeCells;
    }
}