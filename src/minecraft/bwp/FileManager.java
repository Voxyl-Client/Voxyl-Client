package bwp;

import com.google.gson.Gson;

import java.io.*;

public class FileManager {

    private static Gson gson = new Gson();

    private static File ROOT_DIR = new File(".bwp");
    private static File MODS_DIR = new File(ROOT_DIR, "Mods");
    private static File CACHE_DIR = new File(ROOT_DIR, "Cache");
    public static File LOGIN_CACHE = new File(CACHE_DIR, "temp_name.json");

    public static void init(){
        if(ROOT_DIR.exists()){ ROOT_DIR.mkdirs(); }
        if(!MODS_DIR.exists()){ MODS_DIR.mkdirs(); }
        if(!CACHE_DIR.exists()){CACHE_DIR.mkdirs(); }
    }
    public static Gson getGson(){
        return gson;
    }
    public static File getModsDirectory(){
        return MODS_DIR;
    }
    public static File getCacheDirectory() {
    	return CACHE_DIR;
    }
    public static boolean doesCacheDirExist() {
    	return CACHE_DIR.exists();
    }
    public static boolean doesLoginFileExist() {
    	return LOGIN_CACHE.exists();
    }

    public static void writeJsonToFile(File file, Object obj) {
        try {
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    return;
                }
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(gson.toJson(obj));

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> T readFromJson(File file, Class<T> c){
        try{
            StringBuilder builder = new StringBuilder();

            BufferedReader reader = new BufferedReader(new FileReader(file));

            String nextLine;
            while((nextLine = reader.readLine()) != null ){
                builder.append(nextLine);
            }

            reader.close();

            return gson.fromJson(builder.toString(), c);
        }catch(IOException e){
            return null;
        }

    }
}
