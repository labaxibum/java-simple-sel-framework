package extensions;

import com.google.gson.Gson;
import dataobjects.DevicesOnBS;
import log.LogHelper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JsonReaderUtils {

    private List<DevicesOnBS> devicesOnBS;
    private final static String JSON_FILE_NOT_FOUND_MSG = "Json file not found at path :";


    public JsonReaderUtils(String testName){
        if (testName.contains("DeviceOnBS")) {
            devicesOnBS = getDeviceListOnBS();
        }
    }

    public final DevicesOnBS getDeviceByDeviceTest(String deviceTest) {
        return devicesOnBS.stream()
                .filter(x -> x.deviceTest.equalsIgnoreCase(deviceTest))
                .findAny()
                .get();
    }

    private List<DevicesOnBS> getDeviceListOnBS() {
        Gson gson = new Gson();
        BufferedReader bufferReader = null;
        try {
            bufferReader = new BufferedReader(new FileReader(FileExtensions.getRootProject() + "/src/test/resources/jsontestdata/deviceOnBS.json"));
            DevicesOnBS[] devicesOnBS = gson.fromJson(bufferReader, DevicesOnBS[].class);
            return Arrays.asList(devicesOnBS);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(JSON_FILE_NOT_FOUND_MSG + FileExtensions.getRootProject() + "/src/test/resources/jsontestdata/deviceOnBS.json");
        } finally {
            try {
                if (bufferReader != null) bufferReader.close();
            } catch (IOException ignore) {
                LogHelper.error(ignore);
            }
        }
    }
}
