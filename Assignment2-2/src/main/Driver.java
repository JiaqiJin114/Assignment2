package main;

import controllers.ManufacturerAPI;
import controllers.TechnologyDeviceAPI;

import models.*;
import utils.ScannerInput;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Scanner;

public class Driver extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    private TechnologyDeviceAPI techAPI;
    private ManufacturerAPI manufacturerAPI;

    public static void main(String[] args) {
        new Driver();
    }

    public Driver() {
        super("Technology Store");

        setSize(400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        JPanel mainPage = createMainPage();
        mainPanel.add(mainPage, "MainPage");

        JPanel manufacturerCRUDPage = createManufacturerCRUDPage();
        mainPanel.add(manufacturerCRUDPage, "Manufacturer CRUD MENU");

        JPanel technologyCRUDPage = createTechnologyCRUDPage();
        mainPanel.add(technologyCRUDPage, "Technology CRUD MENU");

        JPanel reportsPage = createReportsPage();
        mainPanel.add(reportsPage, "Reports MENU");

        add(mainPanel);

        cardLayout.show(mainPanel, "mainPage");

        setVisible(true);

        manufacturerAPI = new ManufacturerAPI(new File("manufacturers.xml"));
        //TODO - construct fields
        //TODO - load all data once the serializers are set up
        runMainMenu();
    }

    private String mainMenu() {
        return("""
                 -------Technology Store-------------
                |  1) Manufacturer CRUD MENU     |
                |  2) Technology CRUD MENU      |
                |  3) Reports MENU               |
                |--------------------------------|
                |  4) Search Manufacturers       |
                |  5) Search Technology Devices  |
                |  6) Sort Technology Devices    |
                |--------------------------------|
                |  10) Save all                  |
                |  11) Load all                  |
                |--------------------------------|
                |  0) Exit                       |
                 --------------------------------
                 ==>>
                """);
    }

    //// search todo by different criteria i.e. look at the list methods and give options based on that.
    // sort todo (and give a list of options - not a recurring menu thou)
    private void runMainMenu() {
        int option = ScannerInput.readNextInt(mainMenu());
        while (option != 0) {
            switch (option) {
                //TODO - Add options
                case 1 -> runManufacturerMenu();
                case 2 -> runTechAPIMenu();
                case 3 -> runReportsMenu();
                case 4 -> findManufacturer();
                case 5 -> findTechDevice();
                case 6 -> runSortTechDevice();
                case 10 -> save();
                case 11 -> load();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.readNextLine("\n Press the enter key to continue");
            option = ScannerInput.readNextInt(mainMenu());
        }
        exitApp();
    }



    private void exitApp() {
        //TODO - save all the data entered
        System.out.println("Exiting....");
        System.exit(0);
    }

    //----------------------
    //  Manufacturer Menu Items
    //----------------------
    private String manufacturerMenu() {
        return ("""
                 --------Manufacturer Menu---------
                |  1) Add a manufacturer           |
                |  2) Delete a manufacturer        |
                |  3) Update manufacturer details  |
                |  4) List all manufacturers       |
                |  5) Find a manufacturer          |
                |  6) List by manufacturers name   |
                |  0) Return to main menu          |
                 ----------------------------------
                 ==>>
                """);
    }

    private void runManufacturerMenu() {
        int option = ScannerInput.readNextInt(manufacturerMenu());
        while (option != 0) {
            switch (option) {
                case 1 -> addManufacturer();
                case 2 -> deleteManufacturer();
                case 3 -> updateManufacturer();
                case 4 -> System.out.println(manufacturerAPI.listManufacturers());
                case 5 -> findManufacturer();
                case 6 -> listByManufacturerName();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.readNextLine("\n Press the enter key to continue");
            option = ScannerInput.readNextInt(manufacturerMenu());
        }
        runMainMenu();
    }

    private void addManufacturer() {
        String manufacturerName = ScannerInput.readNextLine("Please enter the manufacturer name: ");
        int manufacturerNumEmployees = ScannerInput.readNextInt("Please enter the number of employees: ");
        if (manufacturerAPI.addManufacturer(new Manufacturer(manufacturerName, manufacturerNumEmployees))) {
            System.out.println("Add successful");
        } else {
            System.out.println("Add not successful");
        }
    }

    private void deleteManufacturer() {
        String manufacturerName = ScannerInput.readNextLine("Please enter the manufacturer name: ");
        if (manufacturerAPI.removeManufacturerByName(manufacturerName) != null) {
            System.out.println("Delete successful");
        } else {
            System.out.println("Delete not successful");
        }
    }

    private void updateManufacturer() {
        Manufacturer manufacturer = getManufacturerByName();
        if (manufacturer != null) {
            int numEmployees = ScannerInput.readNextInt("Please enter number of Employees: ");
            if (manufacturerAPI.updateManufacturer(manufacturer.getManufacturerName(), numEmployees))
                System.out.println("Number of Employees Updated");
            else
                System.out.println("Number of Employees NOT Updated");
        } else
            System.out.println("Manufacturer name is NOT valid");
    }
    //---------------------
    //  Tech Store Menu
    //---------------------
    private String techAPIMenu() {
        return(""" 
                |-----Technology Store Menu----- |
                | 1) Add a Tech Device           |
                | 2) Delete a Tech Device        |
                | 3) List all Tech Devices       |
                | 4) Update Tech Device          |
                | 0) Return to main menu         |
                 --------------------------------
                 ==>>""");
    }

    private void runTechAPIMenu() {
        int option = ScannerInput.readNextInt(techAPIMenu());
        while (option != 0) {
            switch (option) {
                case 1 -> addTechnologyDevice();
                case 2 -> deleteTechnologyDevice();
                case 3 -> techAPI.listAllTechnologyDevices();
                case 4 -> updateTechDevice();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.readNextLine("\n Press the enter key to continue");
            option = ScannerInput.readNextInt(reportsMenu());
        }
        runMainMenu();
    }

    private void addTechnologyDevice() {
        Manufacturer manufacturer = new Manufacturer(ScannerInput.readNextLine("Please enter the manufacturer's name : "), ScannerInput.readNextInt("Please enter the manufacturer's number : "));
        String id = ScannerInput.readNextLine("Please enter the technology's id : ");
        double price = ScannerInput.readNextDouble("Please enter the technology's price : ");
        String modelName = ScannerInput.readNextLine("Please enter the technology's model name : ");
        Technology technology = new Technology(modelName, price, manufacturer, id) {
            @Override
            public double getInsurancePremium() {
                return 0;
            }

            @Override
            public String connectToInternet() {
                return "";
            }
        };
        techAPI.addTechnologyDevice(technology);
    }

    private void deleteTechnologyDevice() {
        String option = ScannerInput.readNextLine("By id or index? : ");
        if(option.equalsIgnoreCase("id")){
            String id = ScannerInput.readNextLine("Please enter the id : ");
            if (techAPI.deleteTechnologyById(id)){
                System.out.println("Delete successful");
            }else System.out.println("Delete unsuccessful");
        } else if (option.equalsIgnoreCase("index")) {
            int index = ScannerInput.readNextInt("Please enter the index : ");
            techAPI.deleteTechnologyByIndex(index);
        }
    }

    private void updateTechDevice() {
        String choice = ScannerInput.readNextLine("What would you like to update? : ");
        if(choice.equalsIgnoreCase("tablet")){
            techAPI.listAllTablets();
            String id = ScannerInput.readNextLine("Enter the id : ");
            String modelName = ScannerInput.readNextLine("Enter the new model name : ");
            double price = ScannerInput.readNextDouble("Enter the new price : ");
            String manufacturerName = ScannerInput.readNextLine("Enter the new manufacturer name");
            int numEmployees = ScannerInput.readNextInt("Enter the new numEmployees : ");
            Manufacturer manufacturer = new Manufacturer(manufacturerName, numEmployees);
            String newId = ScannerInput.readNextLine("Enter the new id : ");
            String processor = ScannerInput.readNextLine("Enter the new processor");
            int storage = ScannerInput.readNextInt("Enter the new storage : ");
            String operatingSystem = ScannerInput.readNextLine("Enter the new operating system : ");
            Tablet tablet = new Tablet(modelName, price, manufacturer, newId, processor, storage, operatingSystem);
            techAPI.updateTablet(newId, tablet);
        } else if (choice.equalsIgnoreCase("SmartBand")) {
            techAPI.listAllSmartBands();
            String id = ScannerInput.readNextLine("Enter the id : ");
            String modelName = ScannerInput.readNextLine("Enter the new model name : ");
            double price = ScannerInput.readNextDouble("Enter the new price : ");
            String manufacturerName = ScannerInput.readNextLine("Enter the new manufacturer name");
            int numEmployees = ScannerInput.readNextInt("Enter the new numEmployees : ");
            Manufacturer manufacturer = new Manufacturer(manufacturerName, numEmployees);
            String newId = ScannerInput.readNextLine("Enter the new id : ");
            String material = ScannerInput.readNextLine("Enter the new material : ");
            String size = ScannerInput.readNextLine("Enter the new size : ");
            boolean heartMonitor = false;
            String option = ScannerInput.readNextLine("Enter is it has a heart monitor(yes/no) : ");
            if(option.equalsIgnoreCase("yes")){
                heartMonitor = true;
            } else{
                heartMonitor = false;
            }
            SmartBand smartBand = new SmartBand(modelName, price, manufacturer, newId, material,size, heartMonitor);
            techAPI.updateSmartBand(id, smartBand);

        } else if (choice.equalsIgnoreCase("SmartWatch")) {
            techAPI.listAllSmartWatches();
            String id = ScannerInput.readNextLine("Enter the id : ");
            String modelName = ScannerInput.readNextLine("Enter the new model name : ");
            double price = ScannerInput.readNextDouble("Enter the new price : ");
            String manufacturerName = ScannerInput.readNextLine("Enter the new manufacturer name");
            int numEmployees = ScannerInput.readNextInt("Enter the new numEmployees : ");
            Manufacturer manufacturer = new Manufacturer(manufacturerName, numEmployees);
            String newId = ScannerInput.readNextLine("Enter the new id : ");
            String material = ScannerInput.readNextLine("Enter the new material : ");
            String size = ScannerInput.readNextLine("Enter the new size : ");
            String displayType = ScannerInput.readNextLine("Enter the new display type : ");
            SmartWatch smartWatch = new SmartWatch(modelName, price, manufacturer, newId, material, size, displayType);
            techAPI.updateSmartWatch(id, smartWatch);
        }else{
            System.out.println("Wrong input");
            updateTechDevice();
        }
    }


    private String manufacturerReportsMenu() {
        return(""" 
                 ---------- Manufacturers Reports Menu  -------------
                | 1) List Manufacturers                              |
                | 2) List Manufacturers from a given manufacturer    |
                | 3) List Manufacturers by a given name              |
                | 0) Return to main menu                             |
                  ---------------------------------------------------  
                  ==>>""");
    }

    public void runManufacturerReports() {
        int option = ScannerInput.readNextInt(manufacturerReportsMenu());
        while (option != 0) {
            switch (option) {
                case 1 -> System.out.println(manufacturerAPI.listManufacturers());
                case 2 -> System.out.println(listAllByManufacturer());
                case 3 -> System.out.println(listAllByManufacturerName());
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.readNextLine("\n Press the enter key to continue");
            option = ScannerInput.readNextInt(manufacturerReportsMenu());
        }
    }

    private String listAllByManufacturer() {
        String name = ScannerInput.readNextLine("Please enter the manufacturers' names : ");
        int number = ScannerInput.readNextInt("Please enter the manufacturers' numbers : ");
        Manufacturer manufacturer = new Manufacturer(name, number);
        return manufacturerAPI.listAllByManufacturer(manufacturer);
    }

    private String listAllByManufacturerName() {
        String name = ScannerInput.readNextLine("Please enter the manufacturers' names : ");
        return manufacturerAPI.listAllByManufacturerName(name);
    }

    private String reportsMenu() {
        return (""" 
                 --------Reports Menu ---------
                | 1) Manufacturers Overview    |
                | 2) Technology Overview       |
                | 0) Return to main menu       |
                  -----------------------------
                  ==>>
                """);
    }

    public void runReportsMenu() {
        int option = ScannerInput.readNextInt(reportsMenu());
        while (option != 0) {
            switch (option) {
                case 1 -> runManufacturerReports();
                case 2 -> runTechnologyReports();
                case 0 -> runMainMenu();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.readNextLine("\n Press the enter key to continue");
            option = ScannerInput.readNextInt(reportsMenu());
        }
    }

    private String technologyReports(){
        return """
                ----------- Technology Reports Menu -----------
                | 1) List all technology                         |
                | 2) List all SmartBands                         |
                | 3) List all Smart watch                        |
                | 4) List all Tablets                            |
                | 5) List all devices above a price              |
                | 6) List all devices below a price              |
                | 7) List all tablets by operating system        |
                | 8) List the top five most expensive smart watches |
                | 0) Return to main menu                         |
                -------------------------------------------------
                ==>>
                """;
    }

    private void runTechnologyReports() {
        int option = ScannerInput.readNextInt(technologyReports());
        while (option != 0) {
            switch (option) {
                case 1 -> techAPI.listAllTechnologyDevices();
                case 2 -> techAPI.listAllSmartBands();
                case 3 -> techAPI.listAllSmartWatches();
                case 4 -> techAPI.listAllTablets();
                case 5 -> listAllTechnologyAbovePrice();
                case 6 -> listAllTechnologyBelowPrice();
                case 7 -> listAllTabletsByOperatingSystem();
                case 8 -> techAPI.topFiveMostExpensiveSmartWatch();
                case 0 -> runMainMenu();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.readNextLine("\n Press the enter key to continue");
            option = ScannerInput.readNextInt(technologyReports());
        }
    }

    private void listAllTechnologyAbovePrice() {
        int price = ScannerInput.readNextInt("Enter the price : ");
        System.out.println(techAPI.listAllTechnologyAbovePrice(price));
    }

    private void listAllTechnologyBelowPrice() {
        int price = ScannerInput.readNextInt("Enter the price : ");
        System.out.println(techAPI.listAllTechnologyBelowPrice(price));
    }

    private void listAllTabletsByOperatingSystem() {
        String operatingSystem = ScannerInput.readNextLine("Enter the operating system please : ");
        System.out.println(techAPI.listAllTabletsByOperatingSystem(operatingSystem));
    }

    private void findManufacturer() {
        Manufacturer developer = getManufacturerByName();
        if (developer == null) {
            System.out.println("No such manufacturer exists");
        } else {
            System.out.println(developer);
        }
    }

    private void listByManufacturerName() {
        String manufacturer = ScannerInput.readNextLine("Enter the manufacturer's name:  ");
        System.out.println(manufacturerAPI.listAllByManufacturerName(manufacturer));
    }

    private void findTechDevice() {
        Technology developer = techAPI.getTechnologyById(ScannerInput.readNextLine("Enter the id : "));
        if (developer == null) {
            System.out.println("No such technology exists");
        } else {
            System.out.println(developer);
        }

    }

    private String sortTechDevice(){
        return ("""
                 ----------- Technology Reports Menu -----------
                | 1) sort by price ascending                        |
                | 2) sort by price descending                       |
                | 3) top five most expensive SmartBands             |
                | 4) top five most expensive Tablets                |
                | 5) top five most expensive Smartwatches           |
                | 6) top five most expensive Technologies           |
                -------------------------------------------------
                ==>>
                """);
    }

    private void runSortTechDevice() {
        int option = ScannerInput.readNextInt(sortTechDevice());
        switch (option){
            case 1 -> techAPI.sortByPriceAscending();
            case 2 -> techAPI.sortByPriceDescending();
            case 3 -> techAPI.topFiveMostExpensiveSmartBand();
            case 4 -> techAPI.topFiveMostExpensiveTablet();
            case 5 -> techAPI.topFiveMostExpensiveSmartWatch();
            case 6 -> techAPI.topFiveMostExpensiveTechnology();
            default -> System.out.println("Invalid option entered" + option);
        }
        ScannerInput.readNextLine("\n Press the enter key to continue");
        option = ScannerInput.readNextInt(technologyReports());
    }


    private void save() {
        if (techAPI != null) {
            try {
                techAPI.save();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("techAPI is not initialized.");
        }
    }

    private void load() {
        if (techAPI != null) {
            try {
                techAPI.load();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("techAPI is not initialized.");
        }
    }

    //todo update methods counting methods
    //---------------------
    //  General Menu Items
    //---------------------
    //TODO - write all the methods that are called from your menu
    //---------------------
    //  Helper Methods
    //---------------------
    //TODO- write any helper methods that are required
    private Manufacturer getManufacturerByName() {
        String manufacturerName = ScannerInput.readNextLine("Please enter the manufacturer's name: ");
        if (manufacturerAPI.isValidManufacturer(manufacturerName)) {
            return manufacturerAPI.getManufacturerByName(manufacturerName);
        } else {
            return null;
        }
    }

    private JPanel createMainPage() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(12, 1));

        JTextArea menuArea = new JTextArea(mainMenu());
        menuArea.setEditable(false);
        menuArea.setLineWrap(true);
        menuArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(menuArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton manufacturerCRUDButton = new JButton("Manufacturer CRUD MENU");
        manufacturerCRUDButton.addActionListener(e -> cardLayout.show(mainPanel, "Manufacturer CRUD MENU"));
        panel.add(manufacturerCRUDButton);

        JButton technologyCRUDButton = new JButton("Technology CRUD MENU");
        technologyCRUDButton.addActionListener(e -> cardLayout.show(mainPanel, "Technology CRUD MENU"));
        panel.add(technologyCRUDButton);

        JButton reportsButton = new JButton("Reports MENU");
        reportsButton.addActionListener(e -> cardLayout.show(mainPanel, "Reports MENU"));
        panel.add(reportsButton);

        JButton searchManufacturersButton = new JButton("Search Manufacturers");
        searchManufacturersButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Search Manufacturers feature not implemented yet. Please look at controller below."));
        panel.add(searchManufacturersButton);

        JButton searchTechnologyDevicesButton = new JButton("Search Technology Devices");
        searchTechnologyDevicesButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Search Technology Devices feature not implemented yet. Please look at controller below."));
        panel.add(searchTechnologyDevicesButton);

        JButton sortTechnologyDevicesButton = new JButton("Sort Technology Devices");
        sortTechnologyDevicesButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Sort Technology Devices feature not implemented yet. Please look at controller below."));
        panel.add(sortTechnologyDevicesButton);

        JButton saveAllButton = new JButton("Save all");
        saveAllButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Save all feature not implemented yet. Please look at controller below."));
        panel.add(saveAllButton);

        JButton loadAllButton = new JButton("Load all");
        loadAllButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Load all feature not implemented yet. Please look at controller below."));
        panel.add(loadAllButton);

        JButton displayButton = new JButton("Display devices");
        displayButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "No device now"));
        panel.add(displayButton);

        return panel;
    }

    private JPanel createManufacturerCRUDPage() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1));

        JTextArea menuArea = new JTextArea(manufacturerMenu());
        menuArea.setEditable(false);
        menuArea.setLineWrap(true);
        menuArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(menuArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton addButton = new JButton("Add Manufacturer");
        addButton.addActionListener(e -> addManufacturer());
        panel.add(addButton);

        JButton deleteButton = new JButton("Delete Manufacturer");
        deleteButton.addActionListener(e -> deleteManufacturer());
        panel.add(deleteButton);

        JButton updateButton = new JButton("Update Manufacturer");
        updateButton.addActionListener(e -> updateManufacturer());
        panel.add(updateButton);

        JButton listButton = new JButton("List All Manufacturers");
        listButton.addActionListener(e -> JOptionPane.showMessageDialog(this, manufacturerAPI.listManufacturers()));
        panel.add(listButton);

        JButton findButton = new JButton("Find Manufacturer");
        findButton.addActionListener(e -> findManufacturer());
        panel.add(findButton);

        JButton listByNameButton = new JButton("List by Manufacturer Name");
        listByNameButton.addActionListener(e -> listByManufacturerName());
        panel.add(listByNameButton);

        JButton returnButton = new JButton("Return to Main Menu");
        returnButton.addActionListener(e -> cardLayout.show(mainPanel, "MainPage"));
        panel.add(returnButton);

        return panel;
    }

    private JPanel createTechnologyCRUDPage() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        JTextArea menuArea = new JTextArea(manufacturerMenu());
        menuArea.setEditable(false);
        menuArea.setLineWrap(true);
        menuArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(menuArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton addButton = new JButton("Add Technology Device");
        addButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Add Technology Device feature not implemented yet. Please look at controller below."));
        panel.add(addButton);

        JButton deleteButton = new JButton("Delete Technology Device");
        deleteButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Delete Technology Device feature not implemented yet. Please look at controller below."));
        panel.add(deleteButton);

        JButton listButton = new JButton("List All Technology Devices");
        listButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "List All Technology Devices feature not implemented yet. Please look at controller below."));
        panel.add(listButton);

        JButton updateButton = new JButton("Update Technology Device");
        updateButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Update Technology Device feature not implemented yet. Please look at controller below."));
        panel.add(updateButton);

        JButton returnButton = new JButton("Return to Main Menu");
        returnButton.addActionListener(e -> cardLayout.show(mainPanel, "MainPage"));
        panel.add(returnButton);

        return panel;
    }

    private JPanel createReportsPage() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        JTextArea menuArea = new JTextArea(manufacturerMenu());
        menuArea.setEditable(false);
        menuArea.setLineWrap(true);
        menuArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(menuArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton manufacturersOverviewButton = new JButton("Manufacturers Overview");
        manufacturersOverviewButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Manufacturers Overview feature not implemented yet. Please look at controller below."));
        panel.add(manufacturersOverviewButton);

        JButton technologyOverviewButton = new JButton("Technology Overview");
        technologyOverviewButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Technology Overview feature not implemented yet. Please look at controller below."));
        panel.add(technologyOverviewButton);

        JButton returnButton = new JButton("Return to Main Menu");
        returnButton.addActionListener(e -> cardLayout.show(mainPanel, "MainPage"));
        panel.add(returnButton);

        return panel;
    }
}
