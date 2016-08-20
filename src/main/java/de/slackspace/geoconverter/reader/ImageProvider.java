package de.slackspace.geoconverter.reader;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import de.slackspace.geoconverter.domain.Photo;

public class ImageProvider {

    private Path dir;

    public ImageProvider(String directory) {
        this.dir = Paths.get(directory);
    }

    public List<Photo> readImages() {
        List<Photo> list = new ArrayList<>();

        DirectoryStream<Path> directoryStream;
        try {
            directoryStream = Files.newDirectoryStream(dir, "*.{jpg,jpeg}");
            directoryStream.forEach(path -> list.add(new Photo(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }
}
