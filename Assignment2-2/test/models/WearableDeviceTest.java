package models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WearableDeviceTest {
    private SmartWatch validSmartwatch, invalidSmartwatch;
    private SmartBand validSmartBand, invalidSmartBand;
    @AfterEach
    public void tearDown() {
        validSmartwatch = invalidSmartwatch = null;
        validSmartBand = invalidSmartBand = null;
    }

    @BeforeEach
    public void setUp() {
        Manufacturer manufacturer = new Manufacturer("Samsung", 333);
        Manufacturer invalidManufacturer = new Manufacturer("ABCDEFGHIJKLMNOPQRSTU", 0);
            validSmartwatch = new SmartWatch("Watch 1", 799.99,  manufacturer, "64", "Steel1", "1", "Metal") ;
            invalidSmartwatch =new SmartWatch("Galaxy Tab S7 version 1 c.09462b",19,invalidManufacturer, "12345678910","Wood1","2","Android");
            validSmartBand = new SmartBand("Band 1", 399.99,  manufacturer, "1", "Steel2", "1", true) ;
            invalidSmartBand =new SmartBand("Band 2", 20.00,  invalidManufacturer, "2", "Wood2", "2", false) ;
        }
    @Test
    void getMaterial() {
        assertEquals("Steel1",  validSmartwatch.getMaterial());
        assertEquals("Wood1",  invalidSmartwatch.getMaterial());
        assertEquals("Steel2",  validSmartBand.getMaterial());
        assertEquals("Wood2", invalidSmartBand.getMaterial());
    }

    @Test
    void setMaterial() {
        validSmartwatch.setMaterial("Steel3");
        assertEquals("Steel3", validSmartwatch.getMaterial());
        validSmartBand.setMaterial("Steel4");
        assertEquals("Steel4", validSmartBand.getMaterial());
        invalidSmartBand.setMaterial("Wood4");
        assertEquals("Wood4", invalidSmartBand.getMaterial());
        invalidSmartwatch.setMaterial("Steel5");
        assertEquals("Steel5", invalidSmartwatch.getMaterial());
    }

    @Test
    void getSize() {
        assertEquals("1",  validSmartwatch.getSize());
        assertEquals("2",  invalidSmartwatch.getSize());
        assertEquals("1",  validSmartBand.getSize());
        assertEquals("2", invalidSmartBand.getSize());
    }

    @Test
    void setSize() {
        validSmartwatch.setSize("2");
        assertEquals("2", validSmartwatch.getSize());
        validSmartBand.setSize("3");
        assertEquals("3", validSmartBand.getSize());
        invalidSmartBand.setSize("4");
        assertEquals("4", invalidSmartBand.getSize());
        invalidSmartwatch.setSize("5");
        assertEquals("5", invalidSmartwatch.getSize());
    }

    @Test
    void testToString() {
        String expected = "DisplayType: Metal";
        assertTrue( validSmartwatch.toString().contains(expected));
        expected = "HeartRateMonitor: true";
        assertTrue( validSmartBand.toString().contains(expected));
    }
}