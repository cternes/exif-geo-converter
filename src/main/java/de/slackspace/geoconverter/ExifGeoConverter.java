package de.slackspace.geoconverter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;

import de.slackspace.geoconverter.domain.Photo;
import de.slackspace.geoconverter.exif.ExifReader;
import de.slackspace.geoconverter.exif.MetadataExtractor;
import de.slackspace.geoconverter.reader.ImageProvider;
import de.slackspace.geoconverter.writer.JsonBuilder;
import de.slackspace.geoconverter.writer.ThumbnailCreator;

public class ExifGeoConverter {

    private static final String TARGET_DIRECTORY = "img_map";
    private static final String JSON_OUTPUT_FILE = "geocoordinates.json";

    public static void main(String[] args) throws ImageProcessingException, IOException, MetadataException {
        String sourceDirectory = args[0];
        Path outputPath = getOutputPath(sourceDirectory);

        ImageProvider imageProvider = new ImageProvider(sourceDirectory);

        MetadataExtractor extractor = new MetadataExtractor(imageProvider, new ExifReader());
        List<Photo> photos = extractor.enrichPhotosWithGeoLocation();
        photos = new ThumbnailCreator().createThumbnails(photos, TARGET_DIRECTORY);

        writeThumbnails(outputPath, photos);
        writeJson(outputPath, photos);
    }

    private static void writeThumbnails(Path outputPath, List<Photo> photos) {

        for (Photo photo : photos) {
            Path targetPath = outputPath.resolve(photo.getThumbnailName());

            try {
                System.out.println("File written to " + targetPath);
                Files.write(targetPath, photo.getThumbnail(), StandardOpenOption.CREATE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static Path getOutputPath(String sourceDirectory) {
        Path sourcePath = Paths.get(sourceDirectory);
        Path targetPath = sourcePath.resolve(TARGET_DIRECTORY);

        targetPath.toFile().mkdir();

        return targetPath;
    }

    private static void writeJson(Path outputPath, List<Photo> photos) throws IOException {
        JsonBuilder builder = new JsonBuilder();
        for (Photo photo : photos) {
            builder.addData(photo);
        }

        Path targetFile = outputPath.resolve(JSON_OUTPUT_FILE);

        try (BufferedWriter writer = Files.newBufferedWriter(targetFile)) {
            writer.write(builder.build());
            System.out.println("File written to " + targetFile);
        }
    }
}
