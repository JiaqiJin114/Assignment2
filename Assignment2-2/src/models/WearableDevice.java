package models;

public abstract class WearableDevice extends Technology{
    private String material;
    private String size;

    public WearableDevice(String modelName, double price, Manufacturer manufacturer, String id, String material, String size)
    {
        super(modelName, price, manufacturer, id);
        this.material = limit(material,20);
        this.size = limit(size,10);
    }
    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "WearableDevice{" +
                "material='" + material + '\'' +
                ", size='" + size + '\'' +
                "} " + super.toString();
    }
}
