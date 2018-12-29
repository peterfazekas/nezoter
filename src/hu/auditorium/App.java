package hu.auditorium;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class App {

    /** A színházban 15 sor van.*/
    private static final int ROW = 15;
    /** A színházban soronként 20 szék van.*/
    private static final int COLUMN = 20;
    /** A színházban 15 sor, és soronként 20 szék van.*/
    private static final int TOTAL = ROW * COLUMN;
    /** árkategória  1       2       3       4       5
     *  ár (Ft)      5000    4000    3000    2000    1500 */
    private static final int[] CATEGORIES = {0, 5000, 4000, 3000, 2000, 1500};
    /** Az adott szék foglalt. */
    private static final char OCCUPIED = 'x';
    /** Új sor kiírása*/
    private static final String NEW_LINE = "\r\n";

    /**
     * Egy előadásra a pillanatnyilag eladott jegyek eloszlását a "foglaltsag.txt" szöveges állomány tartalmazza,
     * melyben „x” jelzi a foglalt és „o” a szabad helyeket.
     */
    private final List<String> availabilities;
    /**
     * A jegyek ára nem egyforma, összege a helytől függően ötféle lehet. Azt, hogy az adott szék
     * az öt közül melyik árkategóriába tartozik, a "kategoria.txt" fájl tartalmazza
     */
    private final List<String> categories;

    /**
     * Konstruktor az adatok inicializálására!
     */
    private App() {
        availabilities = readFromFile("foglaltsag.txt");
        categories = readFromFile("kategoria.txt");
    }

    /**
     * Főprogram
     * @param args indítási argumentumok - nincsenek használatban!
     */
    public static void main(String[] args) {
        new App().run();
    }

    /**
     * A feladatok meghívásai!
     */
    private void run() {
        System.out.println(task_2());
        System.out.println(task_3());
        System.out.println(task_4());
        System.out.println(task_5());
        System.out.println(task_6());
        System.out.println(task_7());
    }

    /**
     * 1. feladat: Olvassa be és tárolja el a "foglaltsag.txt" és a "kategoria.txt" fájl adatait!
     * @param input szövegs állomány neve
     * @return szöveglista
     *
     * Szöveges tartalom beolvasása forrásállományból String listába! */
    private List<String> readFromFile(String input) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(input))) {
            lines = br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    /**
     * 2. feladat: Kérje be a felhasználótól egy sor, és azon belül egy szék számát, majd írassa ki
     * a képernyőre, hogy az adott hely még szabad-e vagy már foglalt!
     */
    private String task_2() {
        System.out.println("2. feladat: Kérem adja meg az alábbi adatokat:");
        int row = readFromConsole("   - sor száma: ");
        int column = readFromConsole("   - szék száma: ");
        return isOccupied(row - 1, column - 1) ? "   Az adott szék már foglalt": "   Az adott szék még üres";
    }

    /** Adott sor / oszlop paraméter alapján eldönti, hogy az adott szék foglalt-e */
    private boolean isOccupied(int row, int column) {
        return availabilities.get(row).charAt(column) == OCCUPIED;
    }

    /** A billentyűzetről olvas be int típusú adatot */
    private int readFromConsole(String text) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(text);
        return scanner.nextInt();
    }

    /**
     * 3. feladat: Határozza meg, hogy hány jegyet adtak el eddig, és ez a nézőtér befogadóképességének
     * hány százaléka! A százalékértéket kerekítse egészre, és az eredményt írassa ki a képernyőre.
     */
    private String task_3() {
        long occupiedSeats = countOccupiedSeats();
        long percent = occupiedSeats * 100 / TOTAL;
        return String.format("3. feladat: Az előadásra eddig %d jegyet adtak el, ez a nézőtér %d%%-a.", occupiedSeats, percent);
    }

    /** Megszámolja, hogy összesen hány foglalt (OCCUPIED) szék van. */
    private long countOccupiedSeats() {
        return availabilities.stream().mapToLong(this::countOccupiedSeats).sum();
    }

    /** Megszámolja, hogy egy sorba hány foglalt (OCCUPIED) szék van. */
    private long countOccupiedSeats(String rowData) {
        return rowData.chars().filter(i -> i == OCCUPIED).count();
    }

    /**
     * 4. feladat: Határozza meg, hogy melyik árkategóriában adták el a legtöbb jegyet! Az eredményt
     * írassa ki a képernyőre
     */
    private String task_4() {
        int category = max(countSoldCategories());
        return String.format("4. feladat: A legtöbb jegyet a(z) %d. árkategóriában értékesítették.", category);
    }

    /** Maximumkiválasztás: a legtöbb eladott jegy kateriájának meghatározása */
    private int max(int[] soldCategories) {
        int max = 0;
        for (int i = 1; i < soldCategories.length; i++) {
            if (soldCategories[i] > soldCategories[max]) {
                max = i;
            }
        }
        return max;
    }

    /** Megszámolja, hogy kategoróánként hány db jegyet adtak el eddig */
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

    /** Az adott szék kategóriájának meghatározása sor / oszlop paraméterek alapján */
    private int getCategory(int row, int column) {
        return Integer.parseInt(String.valueOf(categories.get(row).charAt(column)));
    }

    /**
     * 5. feladat: A jegyek árát kategóriánként CATEGORIES konstans tömb tartalmazza!
     * Mennyi lenne a színház bevétele a pillanatnyilag eladott jegyek alapján? Írassa ki az
     * eredményt a képernyőre!
     */
    private String task_5() {
        return String.format("5. feladat: A színház bevétele a pillanatnyilag eladott jegyek alapján: %d Ft", calculateTotalAmount());
    }

    /** A pillanatnyi bevétel meghatározása az input állományok tartalma alpján */
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

    /**
     * 6. feladat: Mivel az emberek általában nem egyedül mennek színházba, ha egy üres hely mellett
     * nincs egy másik üres hely is, akkor azt nehezebben lehet értékesíteni. Határozza meg, és
     * írassa ki a képernyőre, hogy hány ilyen „egyedülálló” üres hely van a nézőtéren!
     */
    private String task_6() {
        return String.format("6. feladat: %d egyedülálló üres hely van a nézőtéren!", countUniqueAvailableSeats());
    }

    /** Az egyedülálló üres helyek számának meghatározása*/
    private int countUniqueAvailableSeats() {
        int uniqueSeats = 0;
        for (int row = 0; row < ROW ; row++) {
            String seats = availabilities.get(row) + OCCUPIED;  // Ideiglenesen tegyünk egy foglalt helyet a sor végére!
            int counter = 0;
            for (int column = 0; column <= COLUMN; column++) {
                if (seats.charAt(column) == OCCUPIED) {     // Ha az adott szék foglalt, akkor
                    if (counter == 1) {                     //    Ha a `counter` számláló értéke 1, akkor
                        uniqueSeats++;                      //    egyedülálló üres szék volt -> (uniqueSeats értékének növelése)
                    }
                    counter = 0;                            // a `counter` kinullázása
                } else {
                    counter++;                              // Ha az adott szék üres, akkor növeljük meg a `counter` számláló értékét
                }
            }
        }
        return uniqueSeats;
    }

    /**
     * 7. feladat: A színház elektronikus eladási rendszere az érdeklődőknek az üres helyek esetén a hely
     * árkategóriáját jeleníti meg, míg a foglalt helyeket csak egy „x” karakterrel jelzi. Készítse
     * el ennek megfelelően a fenti adatokat tartalmazó "szabad.txt" fájlt!
     */
    private String task_7() {
        String output = "szabad.txt";
        dataWrite(output, mergeData());
        return String.format("7. feladat: A \"%s\" állomány elkészült!", output);
    }

    /** Eredmény kiírása állományba*/
    private void dataWrite(String output, String text) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(output))) {
            pw.print(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Az eredmény meghatározása a foglaltság és a kategória tartalma alapján*/
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
