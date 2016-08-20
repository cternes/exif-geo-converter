package de.slackspace.geoconverter.exif;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;

import de.slackspace.geoconverter.domain.Photo;
import de.slackspace.geoconverter.reader.ImageProvider;

public class MetadataExtractor {

    private ImageProvider provider;
    private ExifReader exifReader;

    public MetadataExtractor(ImageProvider provider, ExifReader exifReader) {
        this.provider = provider;
        this.exifReader = exifReader;
    }

    public List<Photo> enrichPhotosWithGeoLocation() throws ImageProcessingException, IOException, MetadataException {
        List<Photo> photoList = provider.readImages();

        for (Photo photo : photoList) {
            Path sourcePath = photo.getSourcePath();
            Metadata metadata = ImageMetadataReader.readMetadata(sourcePath.toFile());

            GeoLocation geoLocation = exifReader.getGeoLocation(metadata);
            photo.setGeoLocation(geoLocation);
        }

        return photoList;
    }
}
