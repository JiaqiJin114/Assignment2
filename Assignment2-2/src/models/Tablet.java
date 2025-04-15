package models;

public class Tablet extends ComputingDevice {
    public String operatingSystem = "Windows";


    public Tablet(String modelName, double price, Manufacturer manufacturer, String id, String processor, int storage, String operatingSystem) {
        super(modelName, price, manufacturer, id, processor, storage);
        this.operatingSystem = operatingSystem;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    @Override
    public double getInsurancePremium() {
        double result = getPrice() * 0.01;
        return result;
    }

    @Override
    public String connectToInternet() {
        return "Connects to the internet via bluetooth”";
    }

    @Override
    public String toString() {
        return "Tablet{" +
                "operatingSystem='" + operatingSystem + '\'' +
                ", processor='" + processor + '\'' +
                ", storage=" + storage +
                ", id='" + id + '\'' +
                ", manufacturer=" + manufacturer +
                ", modelName='" + modelName + '\'' +
                ", price=" + price +
                "} " + super.toString();

    }
}