package models;
public class Technology {
    private String modelName = "unknow";
    private double price = 20;
    private Manufacturer manufacturer;
    private String id;


    public void Tablet(String modelName, double price, Manufacturer manufacturer, String id) {
    this.modelName = limit(modelName,30);
    this.price = pricelimit (price ,20);
    this.manufacturer = manufacturer;
    this.id = limit(id,10);
 }
    public static String limit(String string, int length){
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    @Override
    public String toString() {
        return "Technology{" +
                "id='" + id + '\'' +
                ", modelName='" + modelName + '\'' +
                ", price=" + price +
                ", manufacturer=" + manufacturer +
                '}';
    }

}