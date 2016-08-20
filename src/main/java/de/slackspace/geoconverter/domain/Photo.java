package de.slackspace.geoconverter.domain;

import java.nio.file.Path;

import com.drew.lang.GeoLocation;

public class Photo {

    private Path sourcePath;
    private GeoLocation geoLocation;

    public Photo(Path path) {
        sourcePath = path;
    }

    public Path getSourcePath() {
        return sourcePath;
    }

    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

}
