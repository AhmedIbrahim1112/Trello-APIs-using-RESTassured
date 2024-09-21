package UserData;

import java.util.HashMap;

public class User {
    public static String key = "a034dd9adc0b68d0f05f4df8783ed441";
    public static String token = "ATTAbe62033abd7a28f3ff73dc1f68605ced2f19234ccc157059bb2a50ad6a3bbbbd6F568BC9";
    public static HashMap<String,String> userQueries = new HashMap<String,String>();

    public static void setUserData()
    {
        userQueries.put("key",key);
        userQueries.put("token",token);
    }
}
