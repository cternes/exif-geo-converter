package de.slackspace.geoconverter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;

import de.slackspace.geoconverter.domain.Photo;
import de.slackspace.geoconverter.exif.ExifReader;
import de.slackspace.geoconverter.exif.MetadataExtractor;
import de.slackspace.geoconverter.reader.ImageProvider;
import de.slackspace.geoconverter.writer.JsonBuilder;

public class ExifGeoConverter {

    public static void main(String[] args) throws ImageProcessingException, IOException, MetadataException {
        String sourceDirectory = args[0];
        ImageProvider imageProvider = new ImageProvider(sourceDirectory);

        MetadataExtractor extractor = new MetadataExtractor(imageProvider, new ExifReader());
        List<Photo> photos = extractor.enrichPhotosWithGeoLocation();

        JsonBuilder builder = new JsonBuilder();
        for (Photo photo : photos) {
            builder.addData(photo);
        }

        String targetFile = sourceDirectory + "/geocoordinates.json";
        Path targetPath = Paths.get(targetFile);
        try (BufferedWriter writer = Files.newBufferedWriter(targetPath)) {
            writer.write(builder.build());
            System.out.println("File written to " + targetFile);
        }
    }
}
