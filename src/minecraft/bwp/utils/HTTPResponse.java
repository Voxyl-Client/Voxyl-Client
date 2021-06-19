package bwp.utils;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

public class HTTPResponse {

    private String normal;
    private int code;

    public HTTPResponse(HttpResponse e){
        if(e == null){
            normal = "";
            code = -1;
        }
        else{
            code = e.getStatusLine().getStatusCode();
            try{
                normal = EntityUtils.toString(e.getEntity());


            }catch(Exception f){
                normal = "Error while communicating with web servers.";
            }
        }

    }
    public String getNormal(){
        return normal;
    }
    public int getCode(){
        return code;
    }
}
