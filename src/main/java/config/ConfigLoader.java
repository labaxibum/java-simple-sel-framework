package config;

import log.LogHelper;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;

public class ConfigLoader {
    private static JSONObject environmentConfiguration;
    private static JSONObject frameworkConfiguration;


    private static void initConfig() {
        if (environmentConfiguration == null && frameworkConfiguration == null)
        {
            readConfiguration();
        }
    }

    private static void readConfiguration() {
        InputStream iev = ConfigLoader.class.getResourceAsStream("/environment.json");
        InputStream icf = ConfigLoader.class.getResourceAsStream("/config.json");

        environmentConfiguration = new JSONObject(new JSONTokener(iev));
        frameworkConfiguration = new JSONObject(new JSONTokener(icf));
        String configEnv = System.getProperty("environment");
        if (configEnv != null){
            environmentConfiguration = environmentConfiguration.getJSONObject(configEnv);
        } else {
            String env = environmentConfiguration.getString("environment");
            environmentConfiguration = environmentConfiguration.getJSONObject(env);
        }

        LogHelper.info("test env: " + environmentConfiguration.getString("url"));
    }

    public static String getEnv(String key){
        initConfig();
        String config = System.getProperty(key);
        if (config != null) {
            return config;
        }
        LogHelper.info("Get attribute [" + key + "] in env file: " + environmentConfiguration.get(key).toString());
        return environmentConfiguration.get(key).toString();

    }

    public static String getConfig(String key) {
        initConfig();
        String config = System.getProperty(key);
        if (config != null) {
            return config;
        }
        LogHelper.info("Get attribute [" + key + "] in config file: " + frameworkConfiguration.get(key).toString());
        return frameworkConfiguration.get(key).toString();
    }
}
