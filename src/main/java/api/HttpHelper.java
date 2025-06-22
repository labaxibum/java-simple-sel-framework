package api;

import com.google.common.net.HttpHeaders;
import log.LogHelper;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.HTTP;
import org.openqa.selenium.Cookie;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Class for HTTP helper methods to perform HTTP requests and manage cookies.
 */
public class HttpHelper {
    private static final String NO_BROWSER_COOKIES = "browser cookies is null";
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String COOKIE = "Cookie";
    private static final String SESSION_STRING = "any";

    /**
     * Sends a POST request with specified parameters.
     *
     * @param url         the target URL
     * @param contentType header content type
     * @param json        JSON payload for the request body
     * @param cookieStore CookieStore for managing cookies
     * @param accepted    ACCEPT header value
     * @param headerParam custom header name
     * @param headerValue custom header value
     * @param cookies     cookie string to set in header
     * @return HttpResponse from the server
     * @throws IOException if an I/O error occurs
     */
    public HttpResponse sendPostRequest(String url, String contentType, String json, CookieStore cookieStore,
                                        String accepted, String headerParam, String headerValue, String cookies)
            throws IOException {
        try {
            HttpClientBuilder builder = HttpClientBuilder.create();
            if (cookieStore != null) {
                builder.setDefaultCookieStore(cookieStore);
            }

            HttpClient client = builder.build();
            HttpPost post = new HttpPost(url);

            // Set JSON body if provided
            if (json != null) {
                post.setEntity(new StringEntity(json));
            }

            // Set standard headers
            if (accepted != null) {
                post.setHeader(HttpHeaders.ACCEPT, accepted);
            }
            if (contentType != null) {
                post.setHeader(HttpHeaders.CONTENT_TYPE, contentType);
            }

            // Set custom header if provided
            if (headerParam != null && !headerParam.isEmpty() && headerValue != null) {
                post.setHeader(headerParam, headerValue);
            }

            // Set cookie header if provided (overrides CookieStore for this request if needed)
            if (cookies != null && !cookies.isEmpty()) {
                post.setHeader(COOKIE, cookies);
            }

            // Send the request
            return client.execute(post);
        } catch (Exception ex) {
            LogHelper.error(String.format(
                    "Error: Exception occurs at sendPostRequest URL '%s' by '%s'",
                    url, ex.getMessage()
            ));
            return null;
        }
    }

    /**
     * Sends a POST request with dynamic headers.
     *
     * @param url     the target URL
     * @param json    JSON payload for the request body
     * @param headers map of header key-value pairs
     * @return HttpResponse from the server
     */
    public HttpResponse sendPostRequestWithCookiesHasDynamicHeaders(String url, String json,
                                                                    Map<String, String> headers) {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(url);

            post.addHeader(HTTP.USER_AGENT, USER_AGENT);
            post.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            post.setHeader(HttpHeaders.ACCEPT, "*/*");
            if (json != null) {
                StringEntity stringEntity = new StringEntity(json);
                post.setEntity(stringEntity);
            }

            for (Map.Entry<String, String> _header : headers.entrySet()) {
                post.setHeader(_header.getKey(), _header.getValue());
            }
            // send post request
            return client.execute(post);
        } catch (Exception ex) {
            LogHelper.error(String.format(
                    "ERROR: Exception occurs at sendPostRequestWithCookiesHasDynamicHeaders at request ['%s'] because exception message ['%s']",
                    url, ex.getMessage()
            ));
            return null;
        }
    }

    /**
     * Sends a GET request with specified content type, cookies, and accept header.
     *
     * @param url         the target URL
     * @param contentType header content type
     * @param cookies     CookieStore for managing cookies
     * @param accept      ACCEPT header value
     * @return HttpResponse from the server
     * @throws IOException if an I/O error occurs
     */
    public HttpResponse sendGetRequest(String url, String contentType, CookieStore cookies, String accepted)
            throws IOException {
        HttpClient client = HttpClientBuilder.create().setDefaultCookieStore(cookies).build();
        HttpGet get = new HttpGet(url);

        // add request header
        get.addHeader(HTTP.USER_AGENT, USER_AGENT);
        if (contentType != null) {
            get.setHeader(HttpHeaders.CONTENT_TYPE, contentType);
        }
        if (accepted != null) {
            get.setHeader(HttpHeaders.ACCEPT, accepted);
        }
        return client.execute(get);
    }

    /**
     * Sends a GET request with dynamic headers.
     *
     * @param url     the target URL
     * @param headers map of header key-value pairs
     * @return HttpResponse from the server
     */
    public HttpResponse sendGetRequestWithCookiesHasDynamicHeaders(String url, Map<String, String> headers) {
        try {
            HttpClient client = HttpClientBuilder.create().build();

            HttpGet get = new HttpGet(url);
            for (Map.Entry<String, String> _header : headers.entrySet()) {
                get.setHeader(_header.getKey(), _header.getValue());
            }
            get.addHeader(HTTP.USER_AGENT, USER_AGENT);
            get.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            get.setHeader(HttpHeaders.ACCEPT, "*/*");
            // send get request
            return client.execute(get);
        } catch (Exception ex) {
            LogHelper.error(String.format(
                    "ERROR: Exception occurs at sendGetRequestWithCookiesHasDynamicHeaders at request ['%s'] because exception message ['%s']",
                    url, ex.getMessage()
            ));
            return null;
        }
    }

    /**
     * Sends a PUT request with specified JSON payload and headers.
     *
     * @param url     the target URL
     * @param json    JSON payload for the request body
     * @param headers map of header key-value pairs
     * @return HttpResponse from the server
     */
    public HttpResponse sendPutRequest(String url, String json, Map<String, String> headers) {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPut put = new HttpPut(url);
            for (Map.Entry<String, String> _header : headers.entrySet()) {
                put.setHeader(_header.getKey(), _header.getValue());
            }
            if (json != null) {
                StringEntity stringEntity = new StringEntity(json);
                put.setEntity(stringEntity);
            }
            return client.execute(put);
        } catch (Exception ex) {
            LogHelper.error(String.format(
                    "Error: Exception at sendPutRequest at request ['%s'] because exception message ['%s']",
                    url, ex.getMessage()
            ));
            return null;
        }
    }

    /**
     * Sends a DELETE request with specified content type and cookies.
     *
     * @param url         the target URL
     * @param contentType header content type
     * @param cookies     CookieStore for managing cookies
     * @return HttpResponse from the server
     * @throws IOException if an I/O error occurs
     */
    public HttpResponse sendDeleteRequest(String url, String contentType, CookieStore cookies) throws IOException {
        try {
            HttpClient client = HttpClientBuilder.create().setDefaultCookieStore(cookies).build();
            HttpDelete delete = new HttpDelete(url);
            // add request header
            delete.addHeader(HTTP.USER_AGENT, USER_AGENT);
            if (contentType != null) {
                delete.setHeader(HttpHeaders.CONTENT_TYPE, contentType);
            }
            return client.execute(delete);
        } catch (Exception e) {
            LogHelper.error(String.format(
                    "Error: Exception at sendDeleteRequest at request ['%s'] because exception message ['%s']",
                    url, e
            ));
            return null;
        }
    }

    /**
     * Converts Selenium browser cookies to an HTTP CookieStore.
     *
     * @param browserCookies set of Selenium Cookie objects
     * @return CookieStore containing the converted cookies
     */
    public CookieStore getBrowserCookies(Set<Cookie> browserCookies, boolean isSecure) {
        CookieStore cookieStore = new BasicCookieStore();
        if (browserCookies == null) {
            LogHelper.info(NO_BROWSER_COOKIES);
            return null;
        } else {
            for (Cookie cookie : browserCookies) {
                BasicClientCookie basicCookie = new BasicClientCookie(cookie.getName(), cookie.getValue());
                basicCookie.setDomain(cookie.getDomain());
                if (cookie.getName().contains(SESSION_STRING)) {
                    basicCookie.setPath("any");
                } else {
                    basicCookie.setPath(cookie.getPath());
                }
                if(isSecure) {
                    if (cookie.getName().contains("SESSION") || cookie.getName().contains(SESSION_STRING)) {
                        basicCookie.setSecure(true);
                    } else {
                        basicCookie.setSecure(false);
                    }
                }
            }
        }
        return cookieStore;
    }
}