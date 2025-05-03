package models;
public abstract class Technology {
    public Manufacturer manufacturer;
    public String id  = "unknown";
    public double price = 20;
    public String modelName;


    public Technology(String modelName, double price, Manufacturer manufacturer, String id) {
        this.modelName = limit(modelName,30);
        this.price = priceLimit(price,20);
        this.manufacturer = manufacturer;
        this.id = limit(id,10);
    }

    public String limit(String string, int length){
        if(string.length() >= length){
            return string.substring(0,length);
        }
        return string;
    }

    private double priceLimit(double price ,double Price){
        if(price < Price){
            return price;
        }
        return this.price;
    }


    public String getId(){
        if (id.length() < 10) {
            return id;
        }
        return "unknown";
    }

    public void setId(String id){
        if (id.length() < 10) {
            this.id = id;
        }

    }
    public Manufacturer getManufacturer(){
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer){
        this.manufacturer = manufacturer;
    }

    public String getModelName(){
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
    
    public abstract double getInsurancePremium();

    public abstract String connectToInternet();

}