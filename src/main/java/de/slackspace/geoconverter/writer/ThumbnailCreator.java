package de.slackspace.geoconverter.writer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import de.slackspace.geoconverter.domain.Photo;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ThumbnailCreator {

    private static final int THUMBNAIL_SIZE = 125;
    private static final String JPG = "jpg";

    public List<Photo> createThumbnails(List<Photo> photos, String targetDirectory) {
        for (Photo photo : photos) {
            try {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                Thumbnails.of(photo.getSourcePath().toFile())
                .size(THUMBNAIL_SIZE, THUMBNAIL_SIZE)
                .crop(Positions.CENTER)
                .outputFormat(JPG)
                .toOutputStream(outputStream);

                photo.setThumbnail(outputStream.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return photos;
    }

}
