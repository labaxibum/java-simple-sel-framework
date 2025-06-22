package extensions;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class FileExtensions {
    public static final String GAMELIST_PATH = correctPath(getRootProject()+ "/Game list/");


    public static String getRootProject()
    {
        return System.getProperty("user.dir");
    }

    public static String correctPath(String path) {
        return path.replaceAll("[\\\\|/]", "\\" + System.getProperty("file.separator"));
    }

    public static boolean pathExist(String path) {
        return Files.exists(new File(path).toPath());
    }
    public static void createFile(String path) throws IOException {

        if (!pathExist(path))
            Files.createFile(new File(path).toPath());
    }
    public static void createDirectory(String path) throws IOException {

        if (!pathExist(path))
            Files.createDirectory(new File(path).toPath());
    }
    public static void copyFile(File src, File dest) throws IOException {
        FileUtils.copyFile(src, dest);
    }
    public static void copyFile(String src, String dest) throws IOException {

        File sourceFile = new File(src);
        File destFile = new File(dest);
        Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

    }
    public static void copyDirectory(String src, String dest) throws IOException {
        File sourceFile = new File(src);
        File destFile = new File(dest);
        createDirectory(dest);
        FileUtils.copyDirectory(sourceFile, destFile, true);
    }

}
