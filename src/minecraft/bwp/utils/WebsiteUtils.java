package bwp.utils;


import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class WebsiteUtils {

    //All bases, centered to give readability and easy changes

    private static String API = "https://voxylclient.club/api";

    public static String getApiBase() {
        return API;
    }

    public static String mapUUID() {
        return API + "mapUUID/";
    }

    public static String getBanned() {
        return API + "isBanned/";
    }

    public static String getWhitelist() {
        return API + "isWhitelisted/";

    }
    public static String getGlobalSettings() {
        return API + "globalSettings/";
    }

    public static String getCosmetics() {
        return API + "cosmetics/";
    }

    //TODO - ADD PLAYERS NAME

    public static HTTPResponse sendGet(String point) {
        return sendGet(point, null);
    }

    public static HTTPResponse sendGet(String point, List<NameValuePair> params) {
        try {
            if (params != null) {
                point += "?" + URLEncodedUtils.format(params, "UTF-8");
            }
            HttpGet httpGet = new HttpGet(point);
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpResponse httpResponse = httpClient.execute(httpGet);
            return new HTTPResponse(httpResponse);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HTTPResponse(null);
    }

    public static HTTPResponse sendPost(String point) {
        return sendPost(point, null);
    }

    public static HTTPResponse sendPost(String point, List<NameValuePair> params) {
        try {
            HttpPost httpPost = new HttpPost(point);
            if(params != null){
                httpPost.setEntity(new UrlEncodedFormEntity(params));
            }
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpResponse httpResponse = httpClient.execute(httpPost);
            return new HTTPResponse(httpResponse);


        } catch (Exception e) {
            e.printStackTrace();
            return new HTTPResponse(null);
        }

    }
    public static void sendPostAsync(String endpoint , List<NameValuePair> params){
        new Thread(){
            @Override
            public void run(){
                sendPost(endpoint, params);
            }
        }.start();
    }

    public static boolean downloadFile(String endpoint, File path){
        try{
            InputStream inputStream = new URL(endpoint).openStream();
            FileOutputStream outputStream = new FileOutputStream(path);
            IOUtils.copy(inputStream, outputStream);
            IOUtils.closeQuietly(inputStream);
            return true;

        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }





}
