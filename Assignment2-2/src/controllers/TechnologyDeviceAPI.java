package controllers;

import models.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import models.Manufacturer;
import utils.ISerializer;


//TODO - ensure that this class implements iSerializer
public class TechnologyDeviceAPI implements ISerializer {
    private ArrayList<Technology> technologyList = new ArrayList<>();
    private File file;



 //TODO - create 2 fields
 public TechnologyDeviceAPI(File file) {
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
    public boolean deletTechnologyById(String id) {
        for (int i = 0; i < technologyList.size(); i++) {
            if (technologyList.get(i).getId().equals(id) ){
                technologyList.remove(i);
            }
          return true;
        }

      return false;
    }
    //TODO get Technology methods
    public Technology getTechnologyByIndex(int index) {
        if (index >= 0 && index < technologyList.size()) {
            return technologyList.get(index);
        }
        return null;
    }

    public Technology getTechnologyById(String id) {
        for (int i = 0; i < technologyList.size(); i++) {
            if (technologyList.get(i).getId().equals(id) ){
                return technologyList.get(i);
            }
        }
        return null;
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
        for (int i = 0; i < technologyList.size() ; i++) {
            if (technologyList.get(i) instanceof SmartBand) {
                return technologyList.get(i).toString();
            }

        }
        return "";
    }

    public String listAllSmartWatches() {
        for (int i = 0; i < technologyList.size() ; i++) {
            if (technologyList.get(i) instanceof SmartWatch) {
                return technologyList.get(i).toString();
            }
        }
        return "";
    }

    public String listAllTablets() {
        for (int i = 0; i < technologyList.size() ; i++) {
            if (technologyList.get(i) instanceof Tablet) {
                return technologyList.get(i).toString();
            }
        }
        return "";
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
    //TODO - Number methods
    public int getTabletCount() {
     int count = 0;
        for (int i = 0; i < technologyList.size(); i++) {
            if (technologyList.get(i) instanceof Tablet) {
               count++;
            }
        }
        return count;
    }
    public int getSmartBandCount() {
        int count = 0;
        for (int i = 0; i < technologyList.size(); i++) {
            if (technologyList.get(i) instanceof SmartBand) {
                count++;
            }
        }
        return count;
    }
    public int getSmartWatchCount() {
        int count = 0;
        for (int i = 0; i < technologyList.size(); i++) {
            if (technologyList.get(i) instanceof SmartWatch) {
                count++;
            }
        }
        return count;
    }
    public int getTechnologyCountByManufacturer(String manufacturer) {
        for (int i = 0; i < technologyList.size(); i++) {
            if (technologyList.get(i).getManufacturer().getManufacturerName().equals(manufacturer)) {
                return technologyList.size();
            }
        }
        return 0;
    }
    // Update Methods
    public boolean updateTablet(String id, Tablet updatedDetails) {
        Technology technology = getTechnologyById(id);
        if (technology instanceof Tablet) {
            technologyList.set(technologyList.indexOf(technology), updatedDetails);
            return true;
        }
        return false;
    }
    public boolean updateSmartBand(String id, SmartBand updatedDetails) {
        Technology technology = getTechnologyById(id);
        if (technology instanceof SmartBand) {
            technologyList.set(technologyList.indexOf(technology), updatedDetails);
            return true;
        }
        return false;
    }
    public boolean updateSmartWatch(String id, SmartWatch updatedDetails) {
        Technology technology = getTechnologyById(id);
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






    //TODO Top 5 methods


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
            Class<?>[] classes = new Class[]{ Technology.class};

        //setting up the xstream object with default security and the above classes
        XStream xstream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);

        //doing the actual serialisation to an XML file
        ObjectInputStream in = xstream.createObjectInputStream(new FileReader(file));
        technologyList = (ArrayList<Technology>) in.readObject();
        in.close();
    }
}








