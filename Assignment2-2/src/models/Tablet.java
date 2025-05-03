package models;

public class Tablet extends ComputingDevice {
    public String operatingSystem = "Windows";

    public Tablet(String modelName, double price, Manufacturer manufacturer, String id, String processor, int storage, String operatingSystem) {
        super(modelName, price, manufacturer, id, processor, storage);
        this.operatingSystem = check(operatingSystem);
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        if(isValidOperatingSystem(operatingSystem)){
            this.operatingSystem = operatingSystem;
        }
    }

    @Override
    public double getInsurancePremium() {
        return getPrice() * 0.01;
    }

    @Override
    public String connectToInternet() {
        return "Connects to the internet via Wi-Fi";
    }

    private static String check(String os) {
        if (isValidOperatingSystem(os)) {
            return os;
        }
        return "Windows";
    }

    private static boolean isValidOperatingSystem(String os) {
        return os.equals("Windows") || os.equals("Android") || os.equals("Chrome") || os.equals("iPad") || os.equals("Amazon Fire");
    }

    @Override
    public String toString() {
        return "Operating System: " + getOperatingSystem() + ", Insurance Premium: €" + getInsurancePremium();
    }

}