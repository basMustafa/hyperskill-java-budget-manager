package budget;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class BudgetManager {

    private double balance;
    private List<Purchase> purchaseList;

    public BudgetManager() {
        this.balance = 0;
        this.purchaseList = new LinkedList<>();
    }

    public void addBalance(double balance) {
        this.balance += balance;
    }

    public void addPurchase(Category category, String name, Double price) {
        Purchase purchase = new Purchase(category, name, price);
        this.purchaseList.add(purchase);
        this.balance -= purchase.getPrice();
        System.out.println("Purchase was added!");
    }

    public void print(Category category, List<Purchase> list) {
        boolean sorting = false;

        if (list == null) {
            list = purchaseList;
            sorting = true;
        }

        if (list.isEmpty()) {
            System.out.println(category.name() + ":");
            System.out.println("\nThe purchase list is empty\n");
        } else if (category == Category.ALL) {
            System.out.println();
            if (!sorting) {
                System.out.println(category.name() + ":");
            }
            list.forEach(System.out::println);
            double totalSum = list.stream()
                            .mapToDouble(Purchase::getPrice).sum();
            System.out.printf("Total sum: $%.2f%n%n", totalSum);
        } else {
            System.out.println();
            if (!sorting) {
                System.out.println(category.name() + ":");
            }
            list.stream()
                    .filter(person -> person.getCategory().equals(category))
                    .forEach(System.out::println);
            double totalSum = list.stream()
                    .filter(person -> person.getCategory().equals(category))
                    .mapToDouble(Purchase::getPrice).sum();
            System.out.printf("Total sum: $%.2f%n%n", totalSum);
        }
    }

    public void showBalance() {
        if (balance < 0) {
            System.out.println("\nBalance: $0.00\n");
        } else {
            System.out.printf("%nBalance: $%.2f%n%n", this.balance);
        }
    }

    public void saveList() {
        try (FileOutputStream fileOut = new FileOutputStream("purchases.txt");
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

            objectOut.writeObject(purchaseList);
            objectOut.writeObject(balance);
            System.out.println("\nPurchases were saved!\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadList() {
        try (FileInputStream fileIn = new FileInputStream("purchases.txt");
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)){

            purchaseList = (LinkedList<Purchase>) objectIn.readObject();
            balance = (double) objectIn.readObject();
            System.out.println("\nPurchases were loaded!\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sortAllPurchases() {
        List<Purchase> list = purchaseList.stream()
                .sorted(Comparator.comparingDouble(Purchase::getPrice).reversed())
                .collect(Collectors.toList());
        print(Category.ALL, list);
    }

    public void sortByType() {
        double sumFood = purchaseList.stream()
                .filter(purchase -> purchase.getCategory().equals(Category.FOOD))
                .mapToDouble(Purchase::getPrice).sum();
        double sumClothes = purchaseList.stream()
                .filter(purchase -> purchase.getCategory().equals(Category.CLOTHES))
                .mapToDouble(Purchase::getPrice).sum();
        double sumEntertainment = purchaseList.stream()
                .filter(purchase -> purchase.getCategory().equals(Category.ENTERTAINMENT))
                .mapToDouble(Purchase::getPrice).sum();
        double sumOther = purchaseList.stream()
                .filter(purchase -> purchase.getCategory().equals(Category.OTHER))
                .mapToDouble(Purchase::getPrice).sum();
        double totalSum = sumFood + sumClothes + sumEntertainment + sumOther;

        Map<String, Double> map = new LinkedHashMap<>();
        Map<String, Double> sortedMap = new LinkedHashMap<>();

        map.put("Food - $", sumFood);
        map.put("Clothes - $", sumClothes);
        map.put("Entertainment - $", sumEntertainment);
        map.put("Other - $", sumOther);

        System.out.println("\nTypes:");

        map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));

        sortedMap.forEach((key, value) -> System.out.printf("%s%.2f%n", key, value));
        System.out.printf("Total Sum: $%.2f%n%n", totalSum);
    }

    public void sortByCertainType(Category category) {
        List<Purchase> list = purchaseList.stream()
                .filter(purchase -> purchase.getCategory().equals(category))
                .sorted(Comparator.comparingDouble(Purchase::getPrice).reversed())
                .collect(Collectors.toList());
        print(category, list);
    }
}
