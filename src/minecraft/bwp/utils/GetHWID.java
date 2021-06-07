package bwp.utils;

import java.util.Base64;

public class GetHWID {
    private static String hwid;
    public static String getHwid(){
        hwid = System.getProperty("os.name") + System.getProperty("user.name") + Runtime.getRuntime().availableProcessors() + System.getProperty("os.arch") + System.getenv("COMPUTERNAME");
        return Base64.getEncoder().encodeToString(hwid.getBytes());
    }
}
