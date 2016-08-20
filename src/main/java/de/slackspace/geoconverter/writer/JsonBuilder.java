package de.slackspace.geoconverter.writer;

import java.util.ArrayList;
import java.util.List;

import de.slackspace.geoconverter.domain.Photo;

public class JsonBuilder {

    private List<Photo> photoList = new ArrayList<>();

    public void addData(Photo photo) {
        photoList.add(photo);
    }

    public String build() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        int i = 0;
        for (Photo entry : photoList) {
            if(i > 0) {
                sb.append(",");
            }

            String path = entry.getThumbnailName();
            double latitude = entry.getGeoLocation().getLatitude();
            double longitude = entry.getGeoLocation().getLongitude();

            sb.append(String.format("{\"path\":\"%s\", \"latitude\":%s, \"longitude\":%s}", path, latitude, longitude));
            i++;
        }

        sb.append("]");

        return sb.toString();
    }
}
