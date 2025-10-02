package se.iths.sofia.webshop.controller;

import se.iths.sofia.webshop.view.ScannerUI;

public class Main {


    public static void main(String[] args) {


        AdminSystem adminSystem = new AdminSystem(new ScannerUI());

        adminSystem.run();

    }
}
