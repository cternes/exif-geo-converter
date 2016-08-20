package de.slackspace.geoconverter.domain;

import java.nio.file.Path;

import com.drew.lang.GeoLocation;

public class Photo {

    private static final String THUMB_PREFIX = "thumb_";

    private Path sourcePath;
    private GeoLocation geoLocation;
    private byte[] thumbnail;

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

    public void setThumbnail(byte[] bytes) {
        this.thumbnail = bytes;
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public String getThumbnailName() {
        return  THUMB_PREFIX + getSourcePath().getFileName().toString();
    }

}
