package cn.edu.hbuas.sl.util;

import com.baidu.mapapi.http.HttpClient;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class HttpHelper {
    private static final int REQUEST_TIMEOUT = 15 * 1000;
    private static final int SO_TIMEOUT = 15 * 1000;

    public static String sendHttpRequest(String pUrl) {
        String result = "";
//        result = doGet(pUrl);
        return result;
    }

    public static String GetJsonListValue(String str) {
        String strError = "";
        try {
            JSONObject json1 = new JSONObject(str);
            JSONObject tmp = json1.getJSONObject("d");
            return tmp.getString("a");
        } catch (Exception e) {
            strError = e.toString();
        }
        return strError;

    }

    public static JSONArray GetJsonValue(String str) {

        try {
            JSONArray json1 = new JSONArray(str);
            return json1;
        } catch (Exception e) {
            //strError=e.toString();
        }
        return null;
    }
}
