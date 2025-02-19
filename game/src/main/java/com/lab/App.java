package com.lab;

import java.util.Random;
import java.util.Scanner;

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
        Scanner scanner = new Scanner(System.in);

        System.out.println("Game Minesweeper!");
        System.out.println("คุณต้องการเล่นเกมไหม? (Y/N)");
        String res = scanner.nextLine().trim().toUpperCase();
        if (res.equals("Y") || res.equals("y")) {
            System.out.println("เลือกขนาดแผนที่:");
            System.out.println("1. 5x5");
            System.out.println("2. 7x7");
            System.out.println("3. 9x9");
            int choice = scanner.nextInt();

            int rows = 0, cols = 0;
            switch (choice) {
                case 1 -> {
                    rows = 5;
                    cols = 5;
                }
                case 2 -> {
                    rows = 7;
                    cols = 7;
                }
                case 3 -> {
                    rows = 9;
                    cols = 9;
                }
                default -> {
                    System.out.println("ตัวเลือกไม่ถูกต้อง ออกจากโปรแกรม");
                    scanner.close();
                    return;
                }
            }

            System.out.print("กรุณากรอกจำนวนระเบิด (ค่าควรอยู่ระหว่าง 1 ถึง " + (rows * cols - 1) + "): ");
            int mines = scanner.nextInt();
            if (mines < 1 || mines >= rows * cols) {
                System.out.print("จำนวนระเบิดไม่ถูกต้อง ออกจากโปรแกรม");
                scanner.close();
                return;
            }

            Minesweeper game = new Minesweeper(rows, cols, mines);
            while (true) {
                game.displayField();
                System.out.print("เลือกตำแหน่งที่ต้องการเปิด (แถว คอลัมน์): ");
                int r = scanner.nextInt();
                int c = scanner.nextInt();

                if (r < 0 || r >= rows || c < 0 || c >= cols) {
                    System.out.print("ตำแหน่งไม่ถูกต้อง! โปรดลองอีกครั้ง");
                    continue;
                }

                if (game.revealed[r][c]) {
                    System.out.print("ช่องนี้เปิดไปแล้ว! โปรดลองใหม่");
                    continue;
                }

                boolean hitMine = game.openCell(r, c);
                if (hitMine) {
                    System.out.println("เจอระเบิด!");
                    Random random = new Random();
                    boolean survive = random.nextBoolean();
                    if (survive) {
                        System.out.println("คุณโชคดี รอดจากระเบิด!");
                    } else {
                        System.out.println("เกมโอเวอร์ คุณแพ้แล้ว!!!");
                        game.displayField();
                        break;
                    }
                } else {
                    System.out.println("ไม่มีระเบิด!");
                }
                if (game.isWin()) {
                    System.out.println("ยินดีด้วย! คุณชนะเกมนี้!");
                    game.displayField();
                    break;
                }
            }
        } else {
            System.out.println("ลาก่อน!");
            scanner.close();
            return;
        }
        System.out.println("เกมจบ! ขอบคุณที่เล่น");
        scanner.close();

        /*
         * Minesweeper game = initMineField();
         * Minesweeper game = initMineFieldFromFile("minefield/minefield01.txt");
         * game.displayField();
         */
    }
}
