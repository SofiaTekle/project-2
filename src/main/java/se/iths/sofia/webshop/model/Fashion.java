package se.iths.sofia.webshop.model;

public class Fashion extends Product {
    public Fashion() {
        super();
    }

    public Fashion(String articleNumber, String title, double price, String description) {
        super(articleNumber, title, price, description);
    }

    @Override
    public String category() {
        return "Fashion";
    }
}
