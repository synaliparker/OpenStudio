package edu.mills.openstudio;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Handles sending HTTP requests and getting responses.
 * Class based on Ravi Ramada's <a href="http://www.androidhive.info/2012/05/how-to-connect-android-with-php-mysql/">JSONParser class</a>
 * and Gagandeep Singh's <a href="http://stackoverflow.com/questions/30740359/namevaluepair-httpparams-httpconnection-params-deprecated-on-server-request-cl">StackOverflow answer</>.
 */
class HttpRequestHandler {
    private static final String OPENSTUDIO_URL = "http://open-studio.herokuapp.com/";
    private static final String POST = "POST";
    private static final String GET = "GET";
    private static JSONObject jObj = null;
    private static String json = "";

    /**
     * Makes a GET or POST HTTP request.
     *
     * @param path URL path of the request
     * @param method HTTP request method
     * @param params parameters of the HTTP request
     * @return JSON returned by HTTP request
     */
    JSONObject makeHttpRequest(String path, String method, Map<String, String> params) {

        String encodedParams = getEncodedData(params);
        HttpURLConnection con = null;
        try {
            if(method.equals(POST)) {
                URL url = new URL(path);
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod(POST);
                con.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                writer.write(encodedParams);
                writer.flush();

            } else if(method.equals(GET)){
                URL url = new URL(path + "?" + encodedParams);
                Log.d("URL", url.toString());
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod(GET);
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            Log.d("Input", con.getInputStream().toString());
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
                Log.d("line", line);
            }
            json = sb.toString();
            con.disconnect();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        Log.d("object", jObj.toString());
        return jObj;
    }

    private String getEncodedData(Map<String,String> data) {
        StringBuilder sb = new StringBuilder();
        for(String key : data.keySet()) {
            String value = null;
            try {
                value = URLEncoder.encode(data.get(key),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if(sb.length()>0)
                sb.append("&");

            sb.append(key + "=" + value);
        }
        return sb.toString();
    }
}
