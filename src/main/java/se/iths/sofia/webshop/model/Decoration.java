package se.iths.sofia.webshop.model;

public class Decoration extends Product {

    public Decoration() {
        super();
    }

    public Decoration(String articleNumber, String title, double price, String description) {
        super(articleNumber, title, price, description);
    }

    @Override
    public String category() {
        return "Decoration";
    }
}
