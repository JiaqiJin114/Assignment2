package models;

public abstract class ComputingDevice extends Technology {
    public int storage;
    public String processor;



    public ComputingDevice (String modelName, double price, Manufacturer manufacturer, String id, String processor,int storage)
    {
        super(modelName, price, manufacturer, id);
        this.storage = ComputingDevice.storagelimit(storage);
        this.processor = Technology.limit(processor, 20);

    }
    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }
    public int getStorage() {
        return storage;
    }
    public void setStorage(int storage) {
        if(storage > 8 || storage < 128 ) {
            if (storage % 8 == 0) {
                this.storage = storage;
            }
        }
        this.storage = 128;
    }
    @Override
    public String toString() {
        return "Processor: " + processor + ", Storage: " + storage + "GB" ;
    }
    private static int storagelimit(int storage){
       if ( storage<64 && storage%8!=0){
           return 8;
       }
       else if ( storage>64 && storage%8!=0){
           return 128;
       }
       return storage;

    }
    public abstract double getInsurancePremium ();

    public abstract String connectToInternet ();
}
