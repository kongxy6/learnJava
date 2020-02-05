package test;

import concurrent.vehicleTracker.PublishingVehicleTracker;
import concurrent.vehicleTracker.SafePoint;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Test {

    @org.junit.jupiter.api.Test
    void test() {
        HashMap<String, SafePoint> locations2 = new HashMap();
        HashMap<String, SafePoint> locations1 = new HashMap();
        SafePoint safePoint = new SafePoint(1, 1);
        locations1.put("1", safePoint);
        locations2.put("2", safePoint);
        PublishingVehicleTracker publishingVehicleTracker = new PublishingVehicleTracker(locations1);
        Map map = publishingVehicleTracker.getLocations();
    }

    @org.junit.jupiter.api.Test
    void test_split() {
        String s = ".cs";
        int dot = s.indexOf(".");
        System.out.println(dot);
        String[] sq = s.split("\\.");
        System.out.println(sq);
    }

    @org.junit.jupiter.api.Test
    void replace() throws UnsupportedEncodingException {
        String url = "https://%IP/config/SlbNewCfgEnhRealServerTable/dsadsad/dsadasd/dasdsad";
        url = url.replaceFirst("%IP", "sadasd");
        System.out.println(url);
    }
}
