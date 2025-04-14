package models;

public class Tablet extends ComputingDevice {
    public String operatingSystem = "Windows";

    public Tablet(String modelName, double price, Manufacturer manufacturer, String id,  String processor,int storage, String operatingSystem) {
        super(modelName, price, manufacturer, id, processor, storage);
        this.operatingSystem = operatingSystem;
    }
        @Override
        public double getInsurancePremium () {
            double result = getPrice() * 0.01;
            return result;
        }

        @Override
        public String connectToInternet () {
            return "Connects to the internet via bluetooth”";
        }

    }
