package report;

import extensions.FileExtensions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;

public class HtmlReportDirectory {
    private static String REPORT_ROOT_FOLDER;
    private static String REPORT_FOLDER;
    private static String SCREENSHOT_FOLDER;
    private static String REPORT_FILE_PATH;

    public static void initReportDirectory() throws IOException {
        REPORT_ROOT_FOLDER = FileExtensions.getRootProject() + File.separator + "Reports";
        REPORT_FOLDER = REPORT_ROOT_FOLDER + File.separator + "Latest Report";
        checkExistReportAndReName(REPORT_FOLDER);
        SCREENSHOT_FOLDER = REPORT_FOLDER + File.separator + "Screenshots";
        REPORT_FILE_PATH = REPORT_FOLDER + File.separator + "Report.html";
        FileExtensions.createDirectory(REPORT_ROOT_FOLDER);
        FileExtensions.createDirectory(REPORT_FOLDER);
        FileExtensions.createDirectory(SCREENSHOT_FOLDER);
    }
    private static void checkExistReportAndReName(String path) throws IOException {
        if (FileExtensions.pathExist(path)) {
            File oldReport = new File(path);
            BasicFileAttributes oldReportAttribute = Files.readAttributes(oldReport.toPath(),
                    BasicFileAttributes.class);
            File renameOldReport = new File(FileExtensions.getRootProject() + File.separator + "Reports" + File.separator + "Report_"
                    + oldReportAttribute.creationTime().toString().replace(":", "."));
            if (!renameOldReport.toString().isEmpty()){
                oldReport.renameTo(renameOldReport);
            }
        }
    }
    public static String getReportFolder() {
        return REPORT_FOLDER;
    }


    public static String getScreenshotFolder() {
        return SCREENSHOT_FOLDER;
    }


    public static String getReportFilePath() {
        return REPORT_FILE_PATH;
    }
}
