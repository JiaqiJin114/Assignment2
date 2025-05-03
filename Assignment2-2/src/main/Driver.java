package main;

import controllers.ManufacturerAPI;
import controllers.TechnologyDeviceAPI;

import models.*;
import utils.ScannerInput;

import javax.swing.*;
import java.awt.*;
import java.io.File;

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

        JPanel manufacturerCRUDPage = createManufacturerCRUDPage(); // 修改：创建制造商CRUD页面
        mainPanel.add(manufacturerCRUDPage, "Manufacturer CRUD MENU");

        JPanel technologyCRUDPage = createTechnologyCRUDPage(); // 修改：创建技术设备CRUD页面
        mainPanel.add(technologyCRUDPage, "Technology CRUD MENU");

        JPanel reportsPage = createReportsPage(); // 修改：创建报告页面
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
                |  2) Technology  CRUD MENU      |
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
                case 2 -> techAPIMenu();
                case 3 -> runReportsMenu();
                case 0 -> exitApp();
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
    private int manufacturerMenu() {
        System.out.println("""
                 --------Manufacturer Menu---------
                |  1) Add a manufacturer           |
                |  2) Delete a manufacturer        |
                |  3) Update manufacturer details  |
                |  4) List all manufacturers       |
                |  5) Find a manufacturer          |
                |  6) List by manufacturers name   |
                |  0) Return to main menu          |
                 ----------------------------------
                """);
        return ScannerInput.readNextInt("==>>");
    }

    private void runManufacturerMenu() {
        int option = manufacturerMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> addManufacturer();
                case 2 -> deleteManufacturer();
                case 3 -> updateManufacturer();
                case 4 -> System.out.println(manufacturerAPI.listManufacturers());
                case 5 -> findManufacturer();
                case 6 -> listByManufacturerName();
                case 0 -> runMainMenu();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.readNextLine("\n Press the enter key to continue");
            option = manufacturerMenu();
        }
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

    //---------------------
    //  Tech Store Menu
    //---------------------
    private int techAPIMenu() {
        System.out.println(""" 
                 -----Technology Store Menu----- 
                | 1) Add a Tech Device           |
                | 2) Delete a Tech Device        |
                | 3) List all Tech Devices       |
                | 4) Update Tech Device          |
                | 0) Return to main menu         |
                 ----------------------------""");
        return ScannerInput.readNextInt("==>>");
    }

    public void runReportsMenu() {
        int option = reportsMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> runManufacturerReports();
                case 2 -> System.out.println("TODO - case 2");
                case 0 -> runMainMenu();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.readNextLine("\n Press the enter key to continue");
            option = reportsMenu();
        }
    }

    private int reportsMenu() {
        System.out.println(""" 
                 --------Reports Menu ---------
                | 1) Manufacturers Overview    |
                | 2) Technology Overview       |
                | 0) Return to main menu       |
                  -----------------------------
                """);
        return ScannerInput.readNextInt("==>>");
    }

    private int manufacturerReportsMenu() {
        System.out.println(""" 
                 ---------- Manufacturers Reports Menu  -------------
                | 1) List Manufacturers                              |
                | 2) List Manufacturers from a given manufacturer    |
                | 3) List Manufacturers by a given name              |
                | 0) Return to main menu                             | 
                  ---------------------------------------------------  """);
        return ScannerInput.readNextInt("==>>");
    }

    public void runManufacturerReports() {
        int option = manufacturerReportsMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> System.out.println(manufacturerAPI.listManufacturers());
                case 2 -> System.out.println("todo - Case 2");
                case 3 -> System.out.println("todo - Case 3");
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.readNextLine("\n Press the enter key to continue");
            option = manufacturerReportsMenu();
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
        panel.setLayout(new GridLayout(12, 1)); // 修改：调整布局以适应更多按钮

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
        searchManufacturersButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Search Manufacturers feature not implemented yet."));
        panel.add(searchManufacturersButton);

        JButton searchTechnologyDevicesButton = new JButton("Search Technology Devices");
        searchTechnologyDevicesButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Search Technology Devices feature not implemented yet."));
        panel.add(searchTechnologyDevicesButton);

        JButton sortTechnologyDevicesButton = new JButton("Sort Technology Devices");
        sortTechnologyDevicesButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Sort Technology Devices feature not implemented yet."));
        panel.add(sortTechnologyDevicesButton);

        JButton saveAllButton = new JButton("Save all");
        saveAllButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Save all feature not implemented yet."));
        panel.add(saveAllButton);

        JButton loadAllButton = new JButton("Load all");
        loadAllButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Load all feature not implemented yet."));
        panel.add(loadAllButton);

        return panel;
    }

    private JPanel createManufacturerCRUDPage() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1)); // 修改：调整布局以适应更多按钮

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
        panel.setLayout(new GridLayout(5, 1)); // 修改：调整布局以适应更多按钮

        JButton addButton = new JButton("Add Technology Device");
        addButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Add Technology Device feature not implemented yet."));
        panel.add(addButton);

        JButton deleteButton = new JButton("Delete Technology Device");
        deleteButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Delete Technology Device feature not implemented yet."));
        panel.add(deleteButton);

        JButton listButton = new JButton("List All Technology Devices");
        listButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "List All Technology Devices feature not implemented yet."));
        panel.add(listButton);

        JButton updateButton = new JButton("Update Technology Device");
        updateButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Update Technology Device feature not implemented yet."));
        panel.add(updateButton);

        JButton returnButton = new JButton("Return to Main Menu");
        returnButton.addActionListener(e -> cardLayout.show(mainPanel, "MainPage"));
        panel.add(returnButton);

        return panel;
    }

    private JPanel createReportsPage() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1)); // 修改：调整布局以适应更多按钮

        JButton manufacturersOverviewButton = new JButton("Manufacturers Overview");
        manufacturersOverviewButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Manufacturers Overview feature not implemented yet."));
        panel.add(manufacturersOverviewButton);

        JButton technologyOverviewButton = new JButton("Technology Overview");
        technologyOverviewButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Technology Overview feature not implemented yet."));
        panel.add(technologyOverviewButton);

        JButton returnButton = new JButton("Return to Main Menu");
        returnButton.addActionListener(e -> cardLayout.show(mainPanel, "MainPage"));
        panel.add(returnButton);

        return panel;
    }
}
