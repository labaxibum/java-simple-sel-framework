package api;

import org.json.JSONObject;

public class APIHelper {
    public static String parseCookieToJson(String cookieString, String objectKey , String key) {
        JSONObject jsonObject = new JSONObject(cookieString);
        // Get the first object from the array
        if(!objectKey.isEmpty()){
            JSONObject jsnObj = jsonObject.getJSONObject(objectKey);
            return jsnObj.getString(key);
        }
        else {
            JSONObject accessToken = jsonObject.getJSONObject("access_token");
            return accessToken.getString(key);
        }
    }
}
