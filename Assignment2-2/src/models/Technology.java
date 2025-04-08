package models;
public class Technology {
    private String modelName = "unknow";
    private double price = 20;
    private Manufacturer manufacturer;
    private String id;

public void Technology(String modelName, double price, Manufacturer manufacturer, String id) {
    this.modelName = limit(modelName,10);
    this.price = pricelimit (price ,20);
    this.manufacturer = manufacturer;
    this.id = id;
 }
    private String limit(String string, int length){
        if(string == null){
            return "";
        }
        if(string.length() > length){
            return string.substring(0,length);
        }
        return string;
    }
    private  double pricelimit (double price ,double Price){
        if(price < 0){
            return 20;
        }
        return Price;
    }
}