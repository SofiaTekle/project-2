package se.iths.sofia.webshop.view;

import java.util.Scanner;

public class ScannerUI implements UI {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String prompt(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    @Override
    public void info(String message) {
        System.out.println(message);
    }

    @Override
    public String menu(String[] options) {
        System.out.println("\nChoose an option:");

        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }

        while (true) {
            String choice = scanner.nextLine();
            try {
                int selected = Integer.parseInt(choice);
                if (selected >= 1 && selected <= options.length) {
                    return String.valueOf(selected);
                }
            } catch (NumberFormatException e) {

            }
            //vi hanterar felmeddelandet gemensamt
            System.out.println("Invalid choice. Try again.");
        }

    }
}
