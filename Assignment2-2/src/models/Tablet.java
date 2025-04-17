package models;

public class Tablet extends ComputingDevice {
    public String operatingSystem = "Windows";


    public Tablet(String modelName, double price, Manufacturer manufacturer, String id, String processor, int storage, String operatingSystem) {
        super(modelName, price, manufacturer, id, processor, storage);
        this.operatingSystem = Tablet.check(operatingSystem);
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        if( operatingSystem.equals("Windows") ||  operatingSystem.equals("Android") ||  operatingSystem.equals("Chrome")||  operatingSystem.equals("iPad")||  operatingSystem.equals("Amazom Fire"))
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
        return "Operating System: " + operatingSystem + ", Insurance Premium: €"  +getInsurancePremium() ;
    }
    public  abstract class getInsurancePremium {
        double result = getPrice() * 0.01;
    }
    public static String check(String os){
        if(os.equals("Windows") || os.equals("Android") || os.equals("Chrome")|| os.equals("iPad")|| os.equals("Amazom Fire")){
            return os;
        }
        return "Windows";

    }

}