package de.slackspace.geoconverter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;

public class ExifGeoConverter {

    private DirectoryStream<Path> directoryStream;
    private ExifReader exifReader;
    private JsonBuilder jsonBuilder;

    public static void main(String[] args) throws ImageProcessingException, IOException, MetadataException {
        String sourceDirectory = args[0];
        JsonBuilder builder = new JsonBuilder();
        ExifReader exifReader = new ExifReader();
        ExifGeoConverter converter = new ExifGeoConverter(sourceDirectory, builder, exifReader);
        converter.readGeoLocations();

        String targetFile = sourceDirectory + "/geocoordinates.json";
        Path targetPath = Paths.get(targetFile);
        try (BufferedWriter writer = Files.newBufferedWriter(targetPath)) {
            writer.write(converter.toJson());
        }
    }

    public ExifGeoConverter(String directory, JsonBuilder jsonBuilder, ExifReader exifReader) throws IOException, ImageProcessingException, MetadataException {
        this.jsonBuilder = jsonBuilder;
        this.exifReader = exifReader;
        Path dir = Paths.get(directory);
        directoryStream = Files.newDirectoryStream(dir, "*.{jpg,jpeg}");
    }

    public void readGeoLocations() throws ImageProcessingException, IOException, MetadataException {
        for (Path path : directoryStream) {
            Metadata metadata = ImageMetadataReader.readMetadata(path.toFile());
            GeoLocation geoLocation = exifReader.getGeoLocation(metadata);

            jsonBuilder.addData(geoLocation, path.getFileName().toString());
        }
    }

    public String toJson() {
        return jsonBuilder.build();
    }

}
