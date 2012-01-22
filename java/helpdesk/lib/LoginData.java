package Helpdesk.java.helpdesk.lib;
/******************
 * Imports
 ******************/
import Helpdesk.java.helpdesk.mvc.View.Error_Frame;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginData {
    
    /**********************************************************
    *  Check textfield then write data into a textfile (pw encrypt)
    *************************************************************/
    public static void writeSQL (String host, String port, String db, String user, String newPW) {
        try {
            if ("".equals(host) || "".equals(port) || "".equals(db) || "".equals(user) || "".equals(newPW)) {
                Error_Frame.Error("Please fill in completely");
            } else {
                BufferedWriter out =
                new BufferedWriter(new FileWriter("database.txt"));
                
                out.write("Host:\""+host+"\"Port:\""+port+"\"Database:\""+db+"\"User:\""+user+"\"Password:\""+newPW+"\"");
                out.close();
            }
        } catch (Exception e) {
             Error_Frame.Error(e.getMessage());
        }
    }
    
    
    /**************************************
    *  Read DB connection data from textfile
    ***************************************/
    public static String[] readSQL () {
        String[] Array = new String [5];
        try {
            BufferedReader in = new BufferedReader(new FileReader("database.txt")); 
            String Host = in.readLine(); 
        
            Matcher host = Pattern.compile("Host:\"[.[^\"]]+\"").matcher(Host);
            Matcher port = Pattern.compile("Port:\"[.[^\"]]+\"").matcher(Host);
            Matcher db = Pattern.compile("Database:\"[.[^\"]]+\"").matcher(Host);
            Matcher user = Pattern.compile("User:\"[.[^\"]]+\"").matcher(Host);
            Matcher pw = Pattern.compile("Password:\"[.[^\"]]+\"").matcher(Host);
        
        
         while (host.find()){
             Array[0] = (host.group().replace("Host:" , "").replaceAll("\"",""));
         }
         while (port.find()){
             Array[1] = (port.group().replace("Port:" , "").replaceAll("\"",""));
         }
         while (db.find()){
             Array[2] = (db.group().replace("Database:" , "").replaceAll("\"",""));
         }
         while (user.find()){
             Array[3] = (user.group().replace("User:" , "").replaceAll("\"",""));
         }
         while (pw.find()){
            Array[4] = (pw.group().replace("Password:" , "").replaceAll("\"",""));
         }
            Array[4] = DesEncrypter.SQLdecrypted(Array[4]);
        } catch (Exception e) {
            Error_Frame.Error(e.getMessage());
        }
        return Array;
    }
}
