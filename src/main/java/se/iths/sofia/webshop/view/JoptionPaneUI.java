package se.iths.sofia.webshop.view;

import javax.swing.*;

public class JoptionPaneUI implements UI {
    @Override
    public String prompt(String message) {
        return JOptionPane.showInputDialog(null, message);
    }

    @Override
    public void info(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    @Override
    public String menu(String[] options) {

        Object choice = JOptionPane.showInputDialog(
                null,
                "Choose an option",
                "Menu",
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                options,
                options[0] // defaultvärde 
        );
        return String.valueOf(java.util.Arrays.asList(options).indexOf(choice) + 1);
        //konverterar arrayen till en lista, hittar vilket element användaren valde
    }
}
