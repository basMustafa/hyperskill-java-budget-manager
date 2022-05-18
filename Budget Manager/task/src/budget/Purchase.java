package budget;

import java.io.Serializable;

public class Purchase implements Serializable {
    private final Category category;
    private final String name;
    private final double price;

    public Purchase(Category category, String name, double price) {
        this.category = category;
        this.name = name;
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("%s $%.2f", this.name, this.price);
    }
}
