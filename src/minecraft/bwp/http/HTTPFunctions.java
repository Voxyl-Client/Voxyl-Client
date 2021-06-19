package bwp.http;

import bwp.utils.GetHWID;
import bwp.utils.HTTPResponse;
import bwp.utils.WebsiteUtils;
import bwp.utils.json.ObjGlobalSettings;
import bwp.utils.json.ObjIsBanned;
import bwp.utils.json.ObjUserCosmetics;
import com.google.gson.Gson;
import net.minecraft.client.Minecraft;
import optifine.HttpUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class HTTPFunctions {
    private static final Gson gson = new Gson();


    public static void sendMap(){
        Minecraft mc = Minecraft.getMinecraft();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("uuid", mc.getSession().getProfile().getId().toString()));
        params.add(new BasicNameValuePair("username", mc.thePlayer.getName()));
        params.add(new BasicNameValuePair("hwid", GetHWID.getHwid()));
        WebsiteUtils.sendPostAsync(WebsiteUtils.mapUUID(), params);

    }
    public static boolean isAPIUp(){
        HTTPResponse reply = WebsiteUtils.sendGet(WebsiteUtils.getApiBase());
        return reply.getCode() == 200;
        //TODO - MAKE SURE TO SHUT CLIENT DOWN IF IT ISNT UP
    }
    public static boolean isBanned(){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("hwid", GetHWID.getHwid()));
        HTTPResponse response = WebsiteUtils.sendGet(WebsiteUtils.getBanned(), params);
        if(response.getCode() == 200){
            ObjIsBanned objBanned = gson.fromJson(response.getNormal(), ObjIsBanned.class);
            return objBanned.isBanned();

        }else{
            Minecraft mc = Minecraft.getMinecraft();
            mc.shutdown();
            return false;
            //TODO - create a new class?
        }
    }
    public static boolean isWhitelisted(){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("hwid", GetHWID.getHwid()));
        HTTPResponse response = WebsiteUtils.sendGet(WebsiteUtils.getWhitelist(), params);
        if(response.getCode() == 200){
            ObjIsBanned objBanned = gson.fromJson(response.getNormal(), ObjIsBanned.class);
            return objBanned.isBanned();

        }else{
            Minecraft mc = Minecraft.getMinecraft();
            mc.shutdown();
            return false;
            //TODO - create a new class?
        }
    }
    public static ObjUserCosmetics[] downloadCosmetics(){
        return gson.fromJson(WebsiteUtils.sendGet(WebsiteUtils.getCosmetics()).getNormal(), ObjUserCosmetics[].class);
    }
    public static ObjGlobalSettings downloadGlobalSettings(){
        return gson.fromJson(WebsiteUtils.sendGet(WebsiteUtils.getGlobalSettings()).getNormal(), ObjGlobalSettings.class);
    }
}
