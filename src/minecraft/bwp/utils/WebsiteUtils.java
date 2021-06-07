package bwp.utils;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.List;

public class WebsiteUtils {

    //All bases, centered to give readability and easy changes

    private static String API = "https://voxylclient.club/api";

    public static String getApiBase(){
        return API;
    }
    public static String getUUIDDB(){
        return API + "mapUUID/";
    }
    public static String getBanned(){
        return API + "isBanned/";
    }
    public static String getWhitelist (){
        return API + "isWhitelisted/";

    }
    public static String getCosmetics(){
        return API + "cosmetics/";
    }

    //TODO - ADD PLAYERS NAME

    public static HTTPResponse sendGet(String point){
        return sendGet(point, null);
    }
    public static HTTPResponse sendGet(String point, List<NameValuePair> params){
        try{
            if(params != null){
                point += "?" + URLEncodedUtils.format(params, "UTF-8");
            }
            HttpGet httpGet = new HttpGet(point);
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpResponse httpResponse = httpClient.execute(httpGet);
            return new HTTPResponse(httpResponse);

        }catch(Exception e){
            e.printStackTrace();
        }
        return new HTTPResponse(null);
    }

    public static HTTPResponse sendPost(String point){
        return sendPost(point,null);
    }
    public static HTTPResponse sendPost(String point, List<NameValuePair> params){
        try{

        }catch(Exception e){
            e.printStackTrace();
            return new 
        }
    }



}
