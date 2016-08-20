package de.slackspace.geoconverter;

import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.GpsDirectory;

public class ExifReader {

    public com.drew.lang.GeoLocation getGeoLocation(Metadata metadata) throws MetadataException {
        GpsDirectory gpsData = getGpsData(metadata);
        return gpsData.getGeoLocation();
    }

    private GpsDirectory getGpsData(Metadata metadata) {
        return metadata.getFirstDirectoryOfType(GpsDirectory.class);
    }

}
