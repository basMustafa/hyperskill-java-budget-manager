package app;

import budget.BudgetManager;
import budget.Category;

import java.util.Scanner;

public class UserInterface {
    private static final BudgetManager bm = new BudgetManager();
    private static final Scanner scanner = new Scanner(System.in);
    private static Category category;

    public static void menu() {
        System.out.println("Choose your action:\n" +
                "1) Add income\n" +
                "2) Add purchase\n" +
                "3) Show list of purchases\n" +
                "4) Balance\n" +
                "5) Save\n" +
                "6) Load\n" +
                "7) Analyze (Sort)\n" +
                "0) Exit");

        int action = scanner.nextInt();

        switch (action) {
            case 1:
                System.out.println("\nEnter income:");
                double income = scanner.nextDouble();
                bm.addBalance(income);
                System.out.println("Income was added!\n");
                break;
            case 2:
                addPurchase();
                break;
            case 3:
                System.out.println();
                showList();
                break;
            case 4:
                bm.showBalance();
                break;
            case 5:
                bm.saveList();
                break;
            case 6:
                bm.loadList();
                break;
            case 7:
                System.out.println();
                analyze();
                break;
            case 0:
                System.out.println("\nBye!");
                System.exit(0);
                break;
        }

        menu();
    }

    public static void addPurchase() {
        System.out.println("\nChoose the type of purchase\n" +
                "1) Food\n" +
                "2) Clothes\n" +
                "3) Entertainment\n" +
                "4) Other\n" +
                "5) Back");

        int action = scanner.nextInt();

        switch (action) {
            case 1:
                category = Category.FOOD;
                break;
            case 2:
                category = Category.CLOTHES;
                break;
            case 3:
                category = Category.ENTERTAINMENT;
                break;
            case 4:
                category = Category.OTHER;
                break;
            case 5:
                System.out.println();
                return;
        }

        scanner.nextLine();
        System.out.println("\nEnter purchase name:");
        String name = scanner.nextLine();
        System.out.println("Enter its price:");
        double price = scanner.nextDouble();
        bm.addPurchase(category, name, price);
        addPurchase();
    }

    public static void showList() {
        System.out.println("Choose the type of purchases\n" +
                "1) Food\n" +
                "2) Clothes\n" +
                "3) Entertainment\n" +
                "4) Other\n" +
                "5) All\n" +
                "6) Back");

        int action = scanner.nextInt();

        switch (action) {
            case 1:
                bm.print(Category.FOOD, null);
                break;
            case 2:
                bm.print(Category.CLOTHES, null);
                break;
            case 3:
                bm.print(Category.ENTERTAINMENT, null);
                break;
            case 4:
                bm.print(Category.OTHER, null);
                break;
            case 5:
                bm.print(Category.ALL, null);
                break;
            case 6:
                System.out.println();
                return;
        }
        showList();
    }

    public static void analyze() {
        System.out.println("How do you want to sort?\n" +
                "1) Sort all purchases\n" +
                "2) Sort by type\n" +
                "3) Sort certain type\n" +
                "4) Back");

        int action = scanner.nextInt();

        switch (action) {
            case 1:
                bm.sortAllPurchases();
                break;
            case 2:
                bm.sortByType();
                break;
            case 3:
                sortCertainType();
                break;
            case 4:
                System.out.println();
                return;
        }
        analyze();
    }

    public static void sortCertainType() {
        System.out.println("\nChoose the type of purchase\n" +
                "1) Food\n" +
                "2) Clothes\n" +
                "3) Entertainment\n" +
                "4) Other");

        int action = scanner.nextInt();

        switch (action) {
            case 1:
                bm.sortByCertainType(Category.FOOD);
                break;
            case 2:
                bm.sortByCertainType(Category.CLOTHES);
                break;
            case 3:
                bm.sortByCertainType(Category.ENTERTAINMENT);
                break;
            case 4:
                bm.sortByCertainType(Category.OTHER);
                break;
        }
    }
}
