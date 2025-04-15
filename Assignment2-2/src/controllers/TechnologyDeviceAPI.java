package controllers;

import models.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import models.Manufacturer;
import utils.ISerializer;


//TODO - ensure that this class implements iSerializer
public class TechnologyDeviceAPI implements ISerializer {
    private ArrayList<Technology> technologyList;
    private File file;



 //TODO - create 2 fields
 public TechnologyDeviceAPI(File file) {
     this.technologyList = new ArrayList<>();
     this.file = file;
 }
    //TODO - create constructor



   //TODO - CRUD Methods
   public boolean addTechnology(Technology technology) {
       technologyList.add(technology);
       return true;
   }
    //TODO - delete methods
    public Technology deleteTechnologyByIndex(int index) {
        if (index >= 0 && index < technologyList.size()) {
            return technologyList.remove(index);
        }
        return null;
    }
    public Technology deletTechnologyById(String id) {
        Technology techDev = findTechnologyById(id);
        if (techDev != null) {
            technologyList.remove(techDev);
            return techDev;
        }
        return null;
    }
    //TODO get Technology methods
    public Technology getTechnologyByIndex(int index) {
        if (index >= 0 && index < technologyList.size()) {
            return technologyList.get(index);
        }
        return null;
    }

    public Technology getTechnologyById(String id) {
        return findTechnologyById(id);
    }
    // Report Methods
    // TODO Read/list methods
    public String listAllTechnologyDevices() {
        if (technologyList.isEmpty()) {
            return "No Technology Devices";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < technologyList.size(); i++) {
            sb.append(i).append(": ").append(technologyList.get(i)).append("\n");
        }
        return sb.toString();
    }

    public String listAllSmartBands() {
        return listTechnologiesByType("SmartBands");
    }

    public String listAllSmartWatches() {
        return listTechnologiesByType("SmartWatches");
    }

    public String listAllTablets() {
        return listTechnologiesByType("Tablets");
    }
    public String listAllTechnologyAbovePrice(double price) {
        for (int i = 0; i < technologyList.size(); i++) {
            if (technologyList.get(i).getPrice() > price) {
                return technologyList.get(i).toString();
            }
        } return "No technology more expensive than " + price;
    }

    public String listAllTechnologyBelowPrice(double price) {
        for (int i = 0; i <technologyList.size() ; i++) {
            if (technologyList.get(i).getPrice() < price) {
                return technologyList.get(i).toString();
            }
        } return "No technology more cheap than " + price;
    
    }
    public String listAllTabletsByOperatingSystem(String os) {
        for (int i = 0; i <technologyList.size(); i++) {
            Scanner sc = new Scanner(System.in);
            String result = sc.next();
            if (technologyList.get(i) instanceof Tablet && ((Tablet) technologyList.get(i)).getOperatingSystem()== result) {
                return technologyList.get(i).toString();
            }

        }
        return null;
    }
    public int getTabletCount() {
        return getTechnologyByType("Tablet");
    }
    public int getSmartBandCount() {
        return getTechnologyByType("SmartBand");
    }
    public int getSmartWatchCount() {
        return getTechnologyByType("SmartWatch");
    }
    public int getTechnologyCountByManufacturer(String manufacturer) {
        int count = 0;
        for (Technology techDev : technologyList) {
            if (techDev.getManufacturer().equalsIgnoreCase(manufacturer)) {
                count++;
            }
        }
        return count;
    }
    // Update Methods
    public boolean updateTablet(String id, Tablet updatedDetails) {
        Technology technology = findTechnologyById(id);
        if (technology instanceof Tablet) {
            technologyList.set(technologyList.indexOf(technology), updatedDetails);
            return true;
        }
        return false;
    }
    public boolean updateSmartBand(String id, SmartBand updatedDetails) {
        Technology technology = findTechnologyById(id);
        if (technology instanceof SmartBand) {
            technologyList.set(technologyList.indexOf(technology), updatedDetails);
            return true;
        }
        return false;
    }
    public boolean updateSmartWatch(String id, SmartWatch updatedDetails) {
        Technology technology = findTechnologyById(id);
        if (technology instanceof SmartWatch) {
            technologyList.set(technologyList.indexOf(technology), updatedDetails);
            return true;
        }
        return false;
    }

    // Validation Methods
    public boolean isValidId(String id) {
        for (Technology techDev : technologyList) {
            if (techDev.getId().equals(id)) {
                return false;
            }
        }
        return true;
    }


    //TODO - sort methods
    public void sortByPriceDescending() {
        Collections.sort(technologyList, (t1, t2) -> Double.compare(t2.getPrice(), t1.getPrice()));
    }
    public void sortByPriceAscending() {
        Collections.sort(technologyList, (t1, t2) -> Double.compare(t1.getPrice(), t2.getPrice()));
    }

    private void swap(int i, int j) {
        Technology temp = technologyList.get(i);
        technologyList.set(i, technologyList.get(j));
        technologyList.set(j, temp);
    }




    //TODO - Number methods


    //TODO Top 5 methods
    public ArrayList<Technology> getTop5TechnologiesByPrice() {
        ArrayList<Technology> sortedList = new ArrayList<>(technologyList);
        Collections.sort(sortedList, (t1, t2) -> Double.compare(t2.getPrice(), t1.getPrice()));
        return new ArrayList<>(sortedList.subList(0, Math.min(5, sortedList.size())));
    }
    public ArrayList<Technology> getTop5WatchesByPrice() {
        ArrayList<Technology> watches = getTechnologiesByType("SmatrWatche");
        Collections.sort(watches, (t1, t2) -> Double.compare(t2.getPrice(), t1.getPrice()));
        return new ArrayList<>(watches.subList(0, Math.min(5, watches.size())));
    }
    public ArrayList<Technology> getTop5TabletsByPrice() {
        ArrayList<Technology> tablets = getTechnologiesByType("Tablets");
        Collections.sort(tablets, (t1, t2) -> Double.compare(t2.getPrice(), t1.getPrice()));
        return new ArrayList<>(tablets.subList(0, Math.min(5, tablets.size())));
    }



    // TODO Persistence methods




    @Override
    public String fileName() {
        return String.valueOf(file);
    }
    @Override
    public void save() throws Exception {
        var xstream = new XStream(new DomDriver());
        ObjectOutputStream os = xstream.createObjectOutputStream(new FileWriter(file));
        os.writeObject(technologyList);
        os.close();
    }
    @Override
    public void load() throws Exception {
        //list of classes that you wish to include in the serialisation, separated by a comma
        Class<?>[] classes = new Class[]{ Manufacturer.class};

        //setting up the xstream object with default security and the above classes
        XStream xstream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);

        //doing the actual serialisation to an XML file
        ObjectInputStream in = xstream.createObjectInputStream(new FileReader(file));
        technologyList = new ArrayList<>(loadedList);
        in.close();
     if (loadedList != null) {
        technologyList = new ArrayList<>(loadedList);
    } else {
        technologyList = new ArrayList<>();
    }
}
    private String technologiesToString(ArrayList<Technology> technologies) {
        if (technologies.isEmpty()) {
            return "No Technology Devices";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < technologies.size(); i++) {
            sb.append(i).append(": ").append(technologies.get(i)).append("\n");
        }
        return sb.toString();
    }
    private String listTechnologiesByType(String type) {
        ArrayList<Technology> result =  getTechnologyByType(type);
        if (result.isEmpty()) {
            return "No " + type + "s";
        }
        return technologiesToString(result);
    }
    public Technology getTechnologyByType(String type) {
     for (Technology technolgy : technologyList) {
            if (technolgy.getType().equalsIgnoreCase(type)) {
                count++;
            }
        }
        return count;
    }
}

//techDev instanceof SmartWatch

