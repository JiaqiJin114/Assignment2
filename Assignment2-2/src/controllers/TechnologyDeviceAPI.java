package controllers;

import models.*;
import java.io.*;
import java.util.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

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
   public boolean addTechnologyDevice(Technology technology) {
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
    public boolean deleteTechnologyById(String id) {
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
        for (Technology technology : technologyList) {
            if (technology instanceof SmartBand) {
                return technology.toString();
            }

        }
        return "";
    }

    public String listAllSmartWatches() {
        for (Technology technology : technologyList) {
            if (technology instanceof SmartWatch) {
                return technology.toString();
            }
        }
        return "";
    }

    public String listAllTablets() {
        for (Technology technology : technologyList) {
            if (technology instanceof Tablet) {
                return technology.toString();
            }
        }
        return "";
    }
    public String listAllTechnologyAbovePrice(double price) {
        for (Technology technology : technologyList) {
            if (technology.getPrice() > price) {
                return technology.toString();
            }
        }
        return "No technology more expensive than " + price;
    }

    public String listAllTechnologyBelowPrice(double price) {
        for (Technology technology : technologyList) {
            if (technology.getPrice() < price) {
                return technology.toString();
            }
        }
        return "No technology more cheap than " + price;
    }

    public String listAllTabletsByOperatingSystem(String os) {
        for (Technology technology : technologyList) {
            Scanner sc = new Scanner(System.in);
            String result = sc.next();
            if (technology instanceof Tablet && ((Tablet) technology).getOperatingSystem() == result) {
                return technology.toString();
            }

        }
        return null;
    }

    //TODO - Number methods
    public int numberOfTechnologyDevices() {
        return technologyList.size();
    }

    public int numberOfTablets() {
     int count = 0;
        for (Technology technology : technologyList) {
            if (technology instanceof Tablet) {
                count++;
            }
        }
        return count;
    }
    public int numberOfSmartBands() {
        int count = 0;
        for (Technology technology : technologyList) {
            if (technology instanceof SmartBand) {
                count++;
            }
        }
        return count;
    }

    public int numberOfSmartWatch()  {
        int count = 0;
        for (Technology technology : technologyList) {
            if (technology instanceof SmartWatch) {
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



    private void swapTechnology(int i, int j) {
        Technology temp = technologyList.get(i);
        technologyList.set(i, technologyList.get(j));
        technologyList.set(j, temp);
    }

    public void sortByPriceAscending() {
        for (int i = 0; i < technologyList.size() - 1; i++) {
            for (int j = 0; j < technologyList.size() - 1 - i; j++) {
                if (technologyList.get(j).getPrice() > technologyList.get(j + 1).getPrice()) {
                    swapTechnology(j, j + 1);
                }
            }
        }
    }

    public void sortByPriceDescending() {
        for (int i = 0; i < technologyList.size() - 1; i++) {
            for (int j = 0; j < technologyList.size() - 1 - i; j++) {
                if (technologyList.get(j).getPrice() < technologyList.get(j + 1).getPrice()) {
                    swapTechnology(j, j + 1);
                }
            }
        }
    }

    //TODO Top 5 methods
    public List<Technology> topFiveMostExpensiveTechnology() {
        List<Technology> sortedList = new ArrayList<>(technologyList);
        Collections.sort(sortedList, new Comparator<Technology>() {
            @Override
            public int compare(Technology tech1, Technology tech2) {
                return Double.compare(tech2.getPrice(), tech1.getPrice());
            }
        });
        List<Technology> topFive = new ArrayList<>();
        int count = 0;
        for (Technology tech : sortedList) {
            if (count < 5) {
                topFive.add(tech);
                count++;
            } else {
                break;
            }
        }
        return topFive;
    }
    public List<Technology>  topFiveMostExpensiveSmartWatch() {
        List<SmartWatch> smartWatchList = new ArrayList<>();
        for (Technology tech : technologyList) {
            if (tech instanceof SmartWatch) {
                smartWatchList.add((SmartWatch) tech);
            }
        }  Collections.sort(smartWatchList, new Comparator<SmartWatch>() {
            @Override
            public int compare(SmartWatch watch1, SmartWatch watch2) {
                return Double.compare(watch2.getPrice(), watch1.getPrice());
            }
        });
        List<Technology> topFive = new ArrayList<>();
        int count = 0;
        for (SmartWatch watch : smartWatchList) {
            if (count < 5) {
                topFive.add(watch);
                count++;
            } else {
                break;
            }
        }
        return topFive;
    }

    public List<Technology>  topFiveMostExpensiveSmartBand(){
        List<SmartBand> smartBandList = new ArrayList<>();
        for (Technology tech : technologyList) {
            if (tech instanceof SmartBand) {
                smartBandList.add((SmartBand) tech);
            }
        }
        Collections.sort(smartBandList, new Comparator<SmartBand>() {
            @Override
            public int compare(SmartBand band1, SmartBand band2) {
                return Double.compare(band2.getPrice(), band1.getPrice());
            }
        });
        List<Technology> topFive = new ArrayList<>();
        int count = 0;
        for (SmartBand band : smartBandList) {
            if (count < 5) {
                topFive.add(band);
                count++;
            } else {
                break;
            }
        }
        return topFive;
    }

    public List<Technology>  topFiveMostExpensiveTablet(){
        List<Tablet> tabletList = new ArrayList<>();
        for (Technology tech : technologyList) {
            if (tech instanceof Tablet) {
                tabletList.add((Tablet) tech);
            }
        }
        Collections.sort(tabletList, new Comparator<Tablet>() {
            @Override
            public int compare(Tablet tablet1, Tablet tablet2) {
                return Double.compare(tablet2.getPrice(), tablet1.getPrice());
            }
        });
        List<Technology> topFive = new ArrayList<>();
        int count = 0;
        for (Tablet tablet : tabletList) {
            if (count < 5) {
                topFive.add(tablet);
                count++;
            } else {
                break;
            }
        }
        return topFive;
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
            Class<?>[] classes = new Class[]{ Technology.class};

        //setting up the Xstream object with default security and the above classes
        XStream xstream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);

        //doing the actual serialisation to an XML file
        ObjectInputStream in = xstream.createObjectInputStream(new FileReader(file));
        technologyList = (ArrayList<Technology>) in.readObject();
        in.close();
    }
}








