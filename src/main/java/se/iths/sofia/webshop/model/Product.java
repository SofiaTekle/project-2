package se.iths.sofia.webshop.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

// sparar typ info (vilken subklass i json filen)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Decoration.class, name = "Decoration"),
        @JsonSubTypes.Type(value = Fashion.class, name = "Fashion"),
        @JsonSubTypes.Type(value = Household.class, name = "Household")
})


public abstract class Product {

    private String articleNumber;
    private String title;
    private double price;
    private String description;

    public Product() {
        // tom konstruktor pga Jackson - deserialisering
    }

    public Product(String articleNumber, String title, double price, String description) {
        this.articleNumber = articleNumber;
        this.title = title;
        this.price = price;
        this.description = description;

    }

    public String getArticleNumber() {
        return articleNumber;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return String.format(
                "Category: %s | Article: %s | Title: %s | Price: %.2f SEK | Description: %s",
                category(), articleNumber, title, price, description
        );
    }

    public abstract String category();

}
