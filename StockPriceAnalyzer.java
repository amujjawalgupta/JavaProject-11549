import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class StockPriceAnalyzer {

    private static final String FILE_NAME = "prices.txt";
    private static final double EPSILON = 1e-9;

    
    private static ArrayList<Double> prices = new ArrayList<>();
    
    private static ArrayList<Double> sortedPrices = new ArrayList<>();

    
    public static double parsePrice(String text) {
        try {
            double value = Double.parseDouble(text.trim());
            if (Double.isNaN(value) || Double.isInfinite(value) || value <= 0) {
                return -1;
            }
            return value;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

   
    // Core features
    
    public static void addPrice(Scanner sc) {
        System.out.print("Enter price: ");
        double price = parsePrice(sc.nextLine());
        if (price < 0) {
            System.out.println("Invalid price. Enter a positive number.");
            return;
        }
        prices.add(price);
        System.out.printf("Price added. Day %d: %.2f%n", prices.size(), price);
    }

    public static void displayPrices() {
        if (prices.isEmpty()) {
            System.out.println("No prices stored yet.");
            return;
        }
        System.out.println("Day   Closing Price");
        System.out.println("---------------------");
        for (int i = 0; i < prices.size(); i++) {
            System.out.printf("%-5d %.2f%n", i + 1, prices.get(i));
        }
    }

    public static void analyse() {
        if (prices.isEmpty()) {
            System.out.println("No prices stored yet.");
            return;
        }
        int highDay = 0, lowDay = 0;
        double sum = 0;
        for (int i = 0; i < prices.size(); i++) {
            double p = prices.get(i);
            sum += p;
            if (p > prices.get(highDay)) highDay = i;
            if (p < prices.get(lowDay)) lowDay = i;
        }
        double average = sum / prices.size();
        // Day numbers are shown only when more than one price is stored
        if (prices.size() == 1) {
            System.out.printf("Highest: %.2f | Lowest: %.2f%n", prices.get(highDay), prices.get(lowDay));
        } else {
            System.out.printf("Highest: %.2f (Day %d) | Lowest: %.2f (Day %d)%n",
                    prices.get(highDay), highDay + 1, prices.get(lowDay), lowDay + 1);
        }
        System.out.printf("Average: %.2f%n", average);
    }

    
    public static void sortAscending() {
        sortedPrices = new ArrayList<>(prices);
        for (int i = 1; i < sortedPrices.size(); i++) {
            double key = sortedPrices.get(i);
            int j = i - 1;
            while (j >= 0 && sortedPrices.get(j) > key) {
                sortedPrices.set(j + 1, sortedPrices.get(j));
                j--;
            }
            sortedPrices.set(j + 1, key);
        }
    }

    public static void showSorted() {
        if (prices.isEmpty()) {
            System.out.println("No prices stored yet.");
            return;
        }
        sortAscending();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sortedPrices.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(String.format("%.2f", sortedPrices.get(i)));
        }
        System.out.println(sb);
    }

    
    public static int binarySearch(ArrayList<Double> list, double target) {
        int low = 0, high = list.size() - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            double value = list.get(mid);
            if (Math.abs(value - target) < EPSILON) {
                return mid + 1;
            } else if (value < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    public static void searchPrice(Scanner sc) {
        if (prices.isEmpty()) {
            System.out.println("No prices stored yet.");
            return;
        }
        System.out.print("Enter price to search: ");
        double target = parsePrice(sc.nextLine());
        if (target < 0) {
            System.out.println("Invalid price. Enter a positive number.");
            return;
        }
        sortAscending(); // binary search needs sorted data
        int position = binarySearch(sortedPrices, target);
        if (position == -1) {
            System.out.printf("Price %.2f not found.%n", target);
        } else {
            System.out.printf("Price %.2f found at position %d%n", target, position);
        }
    }


    // File handling
 
    public static void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                double p = parsePrice(line);
                if (p > 0) prices.add(p); // skip blank / corrupted lines
            }
            System.out.println("Loaded " + prices.size() + " price(s) from " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Could not read " + FILE_NAME + ": " + e.getMessage());
        }
    }

    public static void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (double p : prices) {
                pw.println(String.format("%.2f", p));
            }
            System.out.println("Prices saved to " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Could not save " + FILE_NAME + ": " + e.getMessage());
        }
    }


    // Menu

    public static void showMenu() {
        System.out.println();
        System.out.println("================================");
        System.out.println("      STOCK PRICE ANALYZER");
        System.out.println("================================");
        System.out.println("1. Add price");
        System.out.println("2. Display prices");
        System.out.println("3. Analysis (highest, lowest, average)");
        System.out.println("4. Sort ascending");
        System.out.println("5. Binary search");
        System.out.println("6. Save & Exit");
        System.out.println("--------------------------------");
        System.out.print("Enter your choice: ");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        loadFromFile();

        boolean running = true;
        while (running) {
            showMenu();
            if (!sc.hasNextLine()) { // input ended: save and quit safely
                saveToFile();
                break;
            }
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": addPrice(sc); break;
                case "2": displayPrices(); break;
                case "3": analyse(); break;
                case "4": showSorted(); break;
                case "5": searchPrice(sc); break;
                case "6":
                    saveToFile();
                    System.out.println("Thank you for using Stock Price Analyzer.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Enter a number from 1 to 6.");
            }
        }
        sc.close();
    }
}
