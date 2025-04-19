package models;

public abstract class ComputingDevice extends Technology {
    public int storage;
    public String processor;

    public ComputingDevice (String modelName, double price, Manufacturer manufacturer, String id, String processor,int storage)
    {
        super(modelName, price, manufacturer, id);
        this.storage = storageLimit(storage, 8, 128);
        this.processor = limit(processor, 20);

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
        if (storage % 8 == 0) {
            this.storage = storage;
        }
        this.storage = 128;
    }

    @Override
    public String toString() {
        return "Processor: " + processor + ", Storage: " + storage + "GB" ;
    }

    private int storageLimit(int storage, int min, int max){
        if(storage >= min && storage <= 128){
            return storage;
        }
        return 8;
    }
}
