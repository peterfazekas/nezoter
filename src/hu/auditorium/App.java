package hu.auditorium;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class App {

    private static final int ROW = 15;
    private static final int COLUMN = 20;
    private static final int TOTAL = ROW * COLUMN;
    private static final int[] CATEGORIES = {0, 5000, 4000, 3000, 2000, 1500};
    private static final char OCCUPIED = 'x';
    private static final String NEW_LINE = "\r\n";

    private final List<String> availabilities;
    private final List<String> categories;

    private App() {
        availabilities = readFromFile("foglaltsag.txt");
        categories = readFromFile("kategoria.txt");
    }

    public static void main(String[] args) {
        new App().run();
    }

    private void run() {
        System.out.println(task_2());
        System.out.println(task_3());
        System.out.println(task_4());
        System.out.println(task_5());
        System.out.println(task_6());
        System.out.println(task_7());
    }

    private List<String> readFromFile(String input) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(input))) {
            lines = br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    /** 2. feladat */
    private String task_2() {
        System.out.println("2. feladat: Kérem adja meg az alábbi adatokat:");
        int row = readFromConsole("   - sor száma: ");
        int column = readFromConsole("   - szék száma: ");
        return isOccupied(row - 1, column - 1) ? "   Az adott szék már foglalt": "   Az adott szék még üres";
    }

    private boolean isOccupied(int row, int column) {
        return availabilities.get(row).charAt(column) == OCCUPIED;
    }

    private int readFromConsole(String text) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(text);
        return scanner.nextInt();
    }

    /** 3. feladat */
    private String task_3() {
        long occupiedSeats = countOccupiedSeats();
        long percent = occupiedSeats * 100 / TOTAL;
        return String.format("3. feladat: Az előadásra eddig %d jegyet adtak el, ez a nézőtér %d%%-a.", occupiedSeats, percent);
    }

    private long countOccupiedSeats() {
        return availabilities.stream().mapToLong(this::countOccupiedSeats).sum();
    }

    private long countOccupiedSeats(String rowData) {
        return rowData.chars().filter(i -> i == OCCUPIED).count();
    }

    private int max(int[] soldCategories) {
        int max = 0;
        for (int i = 1; i < soldCategories.length; i++) {
            if (soldCategories[i] > soldCategories[max]) {
                max = i;
            }
        }
        return max;
    }

    /** 4. feladat */
    private String task_4() {
        int category = max(countSoldCategories());
        return String.format("4. feladat: A legtöbb jegyet a(z) %d. árkategóriában értékesítették.", category);
    }

    private int[] countSoldCategories() {
        int[] soldCategories = new int[6];
        for (int row = 0; row < ROW ; row++) {
            for (int column = 0; column < COLUMN; column++) {
                if (isOccupied(row, column))
                    soldCategories[getCategory(row, column)]++;
            }
        }
        return soldCategories;
    }

    private int getCategory(int row, int column) {
        return Integer.parseInt(String.valueOf(categories.get(row).charAt(column)));
    }

    /** 5. feladat */
    private String task_5() {
        return String.format("5. feladat: A színház bevétele a pillanatnyilag eladott jegyek alapján: %d Ft", calculateTotalAmount());
    }

    private int calculateTotalAmount() {
        int totalAmount = 0;
        for (int row = 0; row < ROW ; row++) {
            for (int column = 0; column < COLUMN; column++) {
                if (isOccupied(row, column))
                    totalAmount += CATEGORIES[getCategory(row, column)];
            }
        }
        return totalAmount;
    }

    /** 6. feladat */
    private String task_6() {
        return String.format("6. feladat: %d egyedülálló üres hely van a nézőtéren!", countUniqueAvailableSeats());
    }

    private int countUniqueAvailableSeats() {
        int uniqueSeats = 0;
        for (int row = 0; row < ROW ; row++) {
            String seats = availabilities.get(row) + OCCUPIED;
            int counter = 0;
            for (int column = 0; column < COLUMN; column++) {
                if (seats.charAt(column) == OCCUPIED) {
                    if (counter == 1) {
                        uniqueSeats++;
                    }
                    counter = 0;
                } else {
                    counter++;
                }
            }
        }
        return uniqueSeats;
    }

    /** 7. feladat */
    private String task_7() {
        String output = "szabad.txt";
        dataWrite(output, mergeData());
        return String.format("7. feladat: A \"%s\" állomány elkészült!", output);
    }

    private void dataWrite(String output, String text) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(output))) {
            pw.print(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String mergeData() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < ROW ; row++) {
            for (int column = 0; column < COLUMN; column++) {
                String place = isOccupied(row, column) ? String.valueOf(OCCUPIED) : String.valueOf(getCategory(row, column));
                sb.append(place);
            }
            sb.append(NEW_LINE);
        }
        return sb.toString();
    }
}
