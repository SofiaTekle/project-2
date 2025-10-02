package se.iths.sofia.webshop.model;

public class Household extends Product {
    public Household() {
        super();
    }

    public Household(String articleNumber, String title, double price, String description) {
        super(articleNumber, title, price, description);
    }

    @Override
    public String category() {
        return "Household";
    }
}
