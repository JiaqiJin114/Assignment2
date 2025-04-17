package models;
public abstract class Technology {
    public String modelName = "unknow";
    public double price = 20;
    public Manufacturer manufacturer;
    public String id;



    public Technology(String modelName, double price, Manufacturer manufacturer, String id) {
        this.modelName = Technology.limit(modelName,30);
        this.price = pricelimit(price,price);
        this.manufacturer = manufacturer;
        this.id = Technology.limit(id,10);
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
        if(price < 20){
            return 20;
        }
        return Price;
    }


    public String getId() {
        if (id.length() < 10) {
            return id;
        }
        return "unknown";
    }
    public void setId(String id) {
        if (id.length() < 10) {
            this.id = id;
        }

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
        if (modelName.length() < 30) {
            this.modelName = modelName;
        }
    }
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price > 20) {
            this.price = price;
        }
    }
    @Override
    public String toString() {
        return "Model: " + modelName + ", Price: €"  + price + ", Manufacturer Details: "+ manufacturer +", ID: " + id;
    }

}