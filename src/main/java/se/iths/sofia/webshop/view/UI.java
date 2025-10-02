package se.iths.sofia.webshop.view;

public interface UI {

    String prompt(String message);

    void info(String message);

    String menu(String[] options);

}
