package models;

public class SmartWatch extends WearableDevice {
    public String DisplayType;


    public SmartWatch(String modelName, double price, Manufacturer manufacturer, String id, String material, String size, String displayType)
    {
        super(modelName, price, manufacturer, id, material, size);
        this.DisplayType = displayType;
    }

    public String getDisplayType() {
        return DisplayType;
    }

    public void setDisplayType(String displayType) {
        DisplayType = displayType;
    }

    @Override
    public String toString() {
        return "SmartWatch{" +
                "DisplayType='" + DisplayType + '\'' +
                "} " + super.toString();
    }

    @Override
    public double getInsurancePremium() {
        double result = getPrice() * 0.06;
        return result;
    }

    @Override
    public String connectToInternet() {
        return "Connects to the internet via bluetooth”";
    }


}
