package se.iths.sofia.webshop.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import se.iths.sofia.webshop.model.Decoration;
import se.iths.sofia.webshop.model.Fashion;
import se.iths.sofia.webshop.model.Household;
import se.iths.sofia.webshop.model.Product;
import se.iths.sofia.webshop.view.UI;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminSystem {
    private final UI ui;
    private final List<Product> products = new ArrayList<>();
    private boolean running = true;

    private File dataFile = new File("data/products.json");
    private final ObjectMapper mapper = new ObjectMapper();


    public AdminSystem(UI ui) {
        this.ui = ui;
        loadProducts(); // laddar filen vid start
    }

    // ======== RUN METHOD / MENY ========

    public void run() {
        ui.info("Welcome to the Webshop Administration System!");
        while (running) {
            String[] mainMenu = {
                    "Add Product",
                    "List all Products",
                    "Search product by article number",
                    "Exit program"
            };

            String choice = ui.menu(mainMenu);

            switch (choice) {
                case "1" -> addProduct();
                case "2" -> listProducts();
                case "3" -> showProduct();
                case "4" -> exitProgram();
                default -> ui.info("Invalid choice! Please try again. (1-4)");
            }
        }

    }

// ========= PRODUKTOPERATIONER =========

    //samlar input, skapar objekt + sparar
    private void addProduct() {
        String type = chooseProductType();
        String articleNumber = enterArticleNumber();
        String title = ui.prompt("Please enter title");
        double price = enterPrice();
        String description = ui.prompt("Please enter description");

        Product product = createProduct(type, articleNumber, title, price, description);
        products.add(product);
        saveProducts();
        ui.info("---Product added successfully. " + product.getTitle() + " (" + product.category() + ")---");

    }


    private String chooseProductType() {
        String[] options = {"Decoration", "Fashion", "Household"};
        while (true) {
            String input = ui.prompt("Enter product type (Decoration, Fashion, Household):").trim();
            // Kontrollera om input matchar någon av alternativen
            for (String option : options) {
                if (option.equalsIgnoreCase(input)) {
                    return option;
                }
            }
            ui.info("Invalid type. Please enter Decoration, Fashion, or Household.");
        }
    }


    private Product createProduct(String type, String articleNumber, String title, double price, String description) {
        return switch (type.toLowerCase()) {
            case "decoration" -> new Decoration(articleNumber, title, price, description);
            case "fashion" -> new Fashion(articleNumber, title, price, description);
            case "household" -> new Household(articleNumber, title, price, description);
            default -> throw new IllegalArgumentException("Invalid product type");
        };
    }


    private String enterArticleNumber() {
        while (true) {
            String articleNumber = ui.prompt("Please enter the article number");

            if (articleNumber.isEmpty()) {
                ui.info("Article number cannot be empty. Try again.");
                continue;
            }

            // Kolla om det redan finns i listan
            boolean exists = false;
            for (Product p : products) {
                if (p.getArticleNumber().equals(articleNumber)) {
                    exists = true;
                    break;
                }
            }

            if (exists) {
                ui.info("Article number already exists. Try again.");
                continue;
            }

            // Annars är det giltigt
            return articleNumber;
        }
    }


    private double enterPrice() {
        while (true) {
            String input = ui.prompt("Please enter the price");
            try {
                double price = Double.parseDouble(input);
                if (price >= 0) return price;
                ui.info("Price cannot be negative. Try again.");
            } catch (NumberFormatException e) {
                ui.info("Invalid input. Enter a numeric value.");
            }
        }
    }


    private void listProducts() {
        if (products.isEmpty()) {
            ui.info("There are no products in the system");
        } else {
            StringBuilder sb = new StringBuilder("\n--- All products ---\n");
            for (Product p : products) {  // p.toString anropas automatiskt
                sb.append(p).append("\n");
            }
            ui.info(sb.toString());
        }
    }


    private void showProduct() {
        String articleNumber = ui.prompt("Please enter article number:");

        for (Product product : products) {
            if (product.getArticleNumber().equals(articleNumber)) {
                String details = "\n---Product details:---\n" +
                        "Title: " + product.getTitle() + "\n" +
                        "Description: " + product.getDescription() + "\n" +
                        "Price: " + product.getPrice() + " SEK" + "\n" +
                        "Article number: " + product.getArticleNumber();

                ui.info(details);
                return;

            }
        }
        ui.info("\nCould not find product with article number: " + articleNumber);
    }

    private void exitProgram() {
        saveProducts();
        ui.info("\nExiting program...Goodbye!");
        running = false;
    }


    //  ======= FILHANTERING =======

    private void loadProducts() {
        if (!dataFile.exists() || dataFile.length() == 0) {
            return;  //inget att ladda
        }

        //Läs in produkterna från JSON
        try {
            // mapper läser JSON-filen och konverterar den till en Java-lista.
            List<Product> loadedList = mapper.readValue(dataFile, new TypeReference<List<Product>>() {
            });
            products.clear();
            products.addAll(loadedList);

        } catch (IOException e) {
            ui.info("Couldn't read products from the file.");
        }
    }

    // spara produkterna till JSON
    private void saveProducts() {
        try {
            mapper.writerFor(new TypeReference<List<Product>>() {
                    })
                    .withDefaultPrettyPrinter()
                    .writeValue(dataFile, products);
        } catch (IOException e) {
            ui.info("Couldn't write products to the file.");
        }
    }


}

