package de.slackspace.geoconverter;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.drew.lang.GeoLocation;

public class JsonBuilder {

    private Map<String, GeoLocation> map = new HashMap<>();

    public void addData(GeoLocation geoLocation, String thumbnailPath) {
        map.put(thumbnailPath, geoLocation);
    }

    public String build() {
        StringBuilder sb = new StringBuilder();
        sb.append("{[");

        int i = 0;
        for (Entry<String, GeoLocation> entry : map.entrySet()) {
            if(i > 0) {
                sb.append(",");
            }

            String path = entry.getKey();
            double latitude = entry.getValue().getLatitude();
            double longitude = entry.getValue().getLongitude();

            sb.append(String.format("{\"path\":\"%s\", \"latitude\":%s, \"longitude\":%s}", path, latitude, longitude));
            i++;
        }

        sb.append("]}");

        return sb.toString();
    }
}
