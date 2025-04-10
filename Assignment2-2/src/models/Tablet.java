package models;

public class Tablet extends ComputingDevice{
    private String modelName;
    private double price;
    private Manufacturer manufacturer;
    private String id;
    private String processor;
    private int storage;
    private String os;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public Tablet(String modelName, double price, Manufacturer manufacturer, String id, String processor, int storage, String os) {
        this.modelName = modelName;
        this.price = price;
        this.manufacturer = manufacturer;
        this.id = id;
        this.processor = processor;
        this.storage = storage;
        this.os = os;
    }


    @Override
    public String toString() {
        return "Tablet{" +
                "modelName='" + modelName + '\'' +
                ", price=" + price +
                ", manufacturer=" + manufacturer.getManufacturerName() +
                ", id='" + id + '\'' +
                ", processor='" + processor + '\'' +
                ", storage=" + storage +
                ", os='" + os + '\'' +
                '}';
    }
}
