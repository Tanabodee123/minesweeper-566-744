package com.lab;

/**
 * Hello world!
 */
public class App {
    static Minesweeper initMineField() {
        Minesweeper game = new Minesweeper(9, 9);
        game.setMineCell(0, 1);
        game.setMineCell(1, 5);
        game.setMineCell(1, 8);
        game.setMineCell(2, 4);
        game.setMineCell(3, 6);        
        game.setMineCell(4, 2);
        game.setMineCell(5, 4);
        game.setMineCell(6, 2);
        game.setMineCell(7, 2);
        game.setMineCell(8, 6);
        return game;
    }
    static Minesweeper initMineFieldFromFile(String minefieldFile) {
        return new Minesweeper(minefieldFile);
    }
    public static void main(String[] args) {
        // Task 3: Implement a menu to select the mine field template
        // Design the menu by yourself.
                
        /* Minesweeper game = initMineField(); */
        Minesweeper game = initMineFieldFromFile("minefield/minefield01.txt");
        game.displayField();
    }    
}
