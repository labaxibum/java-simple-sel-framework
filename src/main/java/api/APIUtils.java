package api;

import driverManager.DriverManager;
import log.LogHelper;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.Cookie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Set;

public class APIUtils {

    private static final String ACCEPT_ANY = "*/*";

    /**
     * Sending an API POST request to get HttpResponse
     * @param url    API url
     * @param headerParam "Content-Type"
     * @param headerValue "application/json;charset=UTF-8" or "application/json"
     * @param json    {"name" : "value", "name1":"value1"}
     * @return HttpResponse
     * @throws IOException
     */
    public static HttpResponse sendPOSTRequest(String url, String contentType, String json,
                                               String accepted, String headerParam, String headerValue, String cookies) throws IOException {
        HttpHelper helper = new HttpHelper();
        boolean isSecure = url.contains("https://");
        CookieStore cookieStore = helper.getBrowserCookies(DriverManager.getWebDriver().manage().getCookies(), isSecure);
        return helper.sendPostRequest(url, contentType, json, cookieStore, accepted, headerParam, headerValue, cookies);
    }

    public static HttpResponse sendPOSTRequestDynamicHeaders(String url, String jsn, Map<String, String> headers) throws IOException {
        HttpHelper helper = new HttpHelper();
        return helper.sendPostRequestWithCookiesHasDynamicHeaders(url, jsn, headers);
    }

    public static JSONObject getPOSTJSONObjectWithCookies(String url, String contentType, String json,
                                                          String accepted, String headerParam, String headerValue, String cookies) {
        try {
            HttpResponse response = APIUtils.sendPOSTRequest(url, contentType, json, accepted, headerParam, headerValue, cookies);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder strBuilder = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                strBuilder.append(line);
            }
            return new JSONObject(strBuilder.toString());
        } catch (IOException ex) {
            LogHelper.error(String.format(" Exception: IOException occurs at getGETJSONResponse: [%s]", ex));
            return null;
        }
    }

    public static JSONObject getPOSTJSONObjectWithCookiesHasHeader(String url, String headers, String jsn, String cookies, String accept, String headerPram, String headerValue) {
        try {
            HttpResponse response = APIUtils.sendPOSTRequest(url, headers, jsn, cookies, accept, headerPram, headerValue);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder strBuilder = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                strBuilder.append(line);
            }
            return new JSONObject(strBuilder.toString());
        } catch (IOException ex) {
            LogHelper.error("Exception: IOException occurs at getGETJSONResponse");
            return null;
        }
    }

    public static JSONObject getPOSTJSONObjectWithDynamicHeaders(String url, String jsn, Map<String, String> headers) {
        try {
            HttpResponse response = APIUtils.sendPOSTRequestDynamicHeaders(url, jsn, headers);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder strBuilder = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                strBuilder.append(line);
            }
            return new JSONObject(strBuilder.toString());
        } catch (IOException ex) {
            LogHelper.error("Exception: IOException occurs at getGETJSONResponse");
            return null;
        }
    }

    public static JSONArray getPOSTJSONArrayWithCookies(String url, String contentType, String json,
                                                        String accepted, String headerParam, String headerValue, String cookies) {
        try {
            HttpResponse response = APIUtils.sendPOSTRequest(url, contentType, json, accepted, headerParam, headerValue, cookies);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder strBuilder = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                strBuilder.append(line);
            }
            String output = strBuilder.toString();
            return new JSONArray(output);
        } catch (IOException ex) {
            System.out.println("Exception: IOException occurs at getPOSTJSONArrayWithCookies");
            return null;
        }
    }

    public static JSONArray getPOSTJSONArrayWithDynamicHeaders(String url, String json, Map<String, String> headers) {
        try {
            HttpResponse response = APIUtils.sendPOSTRequestDynamicHeaders(url, json, headers);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder strBuilder = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                strBuilder.append(line);
            }
            String output = strBuilder.toString();
            return new JSONArray(output);
        } catch (IOException ex) {
            System.out.println("Exception: IOException occurs at sendGETRequestDynamicHeaders");
            return null;
        }
    }

    public static JSONArray getPOSTJSONArrayResponse(String url, String contentType, String json,
                                                     String accepted, String headerParam, String headerValue, String cookies) {
        try {
            HttpResponse response = APIUtils.sendPOSTRequest(url, contentType, json, accepted, headerParam, headerValue, cookies);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder strBuilder = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                strBuilder.append(line);
            }
            String output = strBuilder.toString();
            return new JSONArray(output);
        } catch (IOException ex) {
            System.out.println("Exception: IOException occurs at getGETJSONResponse");
            return null;
        }
    }

    public static JSONObject getPOSTJSONObjectResponse(String url, String contentType, String json,
                                                        String headerParam, String headerValue, String cookies) {
        try {
            HttpResponse response = APIUtils.sendPOSTRequest(url, contentType, json, ACCEPT_ANY, headerParam, headerValue, cookies);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder strBuilder = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                strBuilder.append(line);
            }
            return new JSONObject(strBuilder.toString());
        } catch (IOException ex) {
            System.out.println("Exception: IOException occurs at getGETJSONResponse");
            return null;
        }
    }


    public static HttpResponse sendGETRequestDynamicHeaders(String url, Map<String, String> headers) throws IOException {
        HttpHelper helper = new HttpHelper();
        return helper.sendGetRequestWithCookiesHasDynamicHeaders(url, headers);
    }


    public static JSONObject getGETJSONObjectWithDynamicHeaders(String url, Map<String, String> headers) {
        try {
            HttpResponse response = APIUtils.sendGETRequestDynamicHeaders(url, headers);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder strBuilder = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                strBuilder.append(line);
            }
            return new JSONObject(strBuilder.toString());
        } catch (IOException ex) {
            LogHelper.error("Exception: IOException occurs at getGETJSONResponse");
            return null;
        }
    }



    public static JSONObject getGETJSONObjectWithCookies(String url, String contentType, String cookies) {
        return getGETJSONObjectWithCookies(url, contentType, cookies, null);
    }

    public static JSONObject getGETJSONObjectWithCookies(String url, String contentType, String cookies, String accept) {
        try {
            HttpResponse response = APIUtils.sendGETRequest(url);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder strBuilder = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                strBuilder.append(line);
            }
            return new JSONObject(strBuilder.toString());
        } catch (IOException ex) {
            System.out.println("Exception: IOException occurs at getGETJSONObjectWithCookies");
            return null;
        }
    }


    public static JSONArray getGETJSONArrayWithDynamicHeaders(String url, Map<String, String> headers) {
        try {
            HttpResponse response = APIUtils.sendGETRequestDynamicHeaders(url, headers);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder strBuilder = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                strBuilder.append(line);
            }
            String output = strBuilder.toString();
            return new JSONArray(output);
        } catch (IOException ex) {
            System.out.println("Exception: IOException occurs at sendGETRequestDynamicHeaders");
            return null;
        }
    }

    /**
     * Getting GET response from URL
     *
     * @param url is sent to the server
     * @return HttpResponse
     * @throws IOException
     */
    public static HttpResponse sendGETRequest(String url) throws IOException {
        return APIUtils.sendGETRequest(url, ACCEPT_ANY);
    }

    /**
     * Getting HttpResponse by sending GET request
     *
     * @param url         is sent to the server
     * @param contentType "application/json;charset=UTF-8" or "application/json"
     * @throws IOException
     */
    public static HttpResponse sendGETRequest(String url, String contentType) throws IOException {
        return sendGETRequest(url, contentType, null);
    }

    public static HttpResponse sendGETRequest(String url, String contentType, String accepted) throws IOException {
        HttpHelper helper = new HttpHelper();
        boolean isSecure = url.contains("https://");
        CookieStore cookieStore = helper.getBrowserCookies((Set<Cookie>) DriverManager.getCookie(), isSecure);
        return helper.sendGetRequest(url, contentType, cookieStore, accepted);
    }

    /**
     * Getting JSONObject when sending GET request
     *
     * @param url         to get data
     * @param contentType "application/json;charset=UTF-8" or "application/json" or null
     * @return JSONObject
     */
    public static JSONObject getGETJSONResponse(String url, String contentType) {
        return getGETJSONResponse(url, contentType, null);
    }

    public static JSONObject getGETJSONResponse(String url, String contentType, String accept) {
        try {
            HttpResponse response = APIUtils.sendGETRequest(url, contentType, accept);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder strBuilder = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                strBuilder.append(line);
            }
            return new JSONObject(strBuilder.toString());
        } catch (IOException ex) {
            System.out.println("Exception: IOException occurs at getGETJSONResponse");
            return null;
        }
    }

    /**
     * Getting JSONObject when sending GET request
     *
     * @param url         to get data
     * @param contentType "application/json;charset=UTF-8" or "application/json" or null
     * @return JSONArray
     */
    public static JSONArray getGETJSONArrayResponse(String url, String contentType, String accept) {
        try {
            HttpResponse response = APIUtils.sendGETRequest(url, contentType, accept);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder strBuilder = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                strBuilder.append(line);
            }
            return new JSONArray(strBuilder.toString());
        } catch (IOException ex) {
            System.out.println("Exception: IOException occurs at getGETJSONResponse");
            return null;
        }
    }

    /**
     * Getting JSONObject when sending GET request
     *
     * @param url         to get data
     * @param contentType "application/json;charset=UTF-8" or "application/json" or null
     * @return JSONArray
     */
    public static JSONArray getGETJSONArrayWithCookies(String url, String contentType) {
        try {
            //HttpResponse response = APIUtils.sendGETRequest(url, contentType);
            HttpResponse response = APIUtils.sendGETRequest(url, contentType);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder strBuilder = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                strBuilder.append(line);
            }
            return new JSONArray(strBuilder.toString());

        } catch (IOException ex) {
            System.out.println("Exception: IOException occurs at getGETJSONResponse");
            return null;
        }
    }

    public static HttpResponse sendPUTRequestDynamicHeaders(String url, String jsn, Map<String, String> headers) throws IOException {
        HttpHelper helper = new HttpHelper();
        return helper.sendPutRequest(url, jsn, headers);
    }

    public static JSONObject getPUTJSONObjectWithDynamicHeaders(String url, String jsn, Map<String, String> headers) {
        try {
            HttpResponse response = APIUtils.sendPUTRequestDynamicHeaders(url, jsn, headers);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder strBuilder = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                strBuilder.append(line);
            }
            return new JSONObject(strBuilder.toString());
        } catch (IOException ex) {
            LogHelper.error("Exception: IOException occurs at getGETJSONResponse");
            return null;
        }
    }

    public static HttpResponse sendDELETERequest(String url) throws IOException {
        HttpHelper helper = new HttpHelper();
        boolean isSecure = url.contains("https://");
        CookieStore cookieStore = helper.getBrowserCookies((Set<Cookie>) DriverManager.getCookie(), isSecure);
        return helper.sendDeleteRequest(url, null, cookieStore);
    }

    public static int getDELETEResponseCode(String url) {
        try {
            HttpResponse response = APIUtils.sendDELETERequest(url);
            return response.getStatusLine().getStatusCode();
        } catch (IOException ex) {
            return 0;
        }
    }
}
