package mvc.Model;
/******************
 * Imports
 ******************/
import mvc.View.Error_Frame;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HtmlModel {
    
     /**************************
     *  set string to fullticket 
     *  editor pane with html codes 
     **************************/
    public static StringBuilder Htmlfullticket (Integer integer) {
        // Display the selected item
                String [] Array = FullTicket.searchFullTicket(integer);
                //Set color for ticket status
                String color = "red";
                if (Array[1].equals("Open")) color = "green";
                else if (Array[1].equals("In process")) color = "blue";
                
                for (int i = 0; i < Array.length ; i++) {
                    if (Array[i] == null || "null".equals(Array [i])) {
                        Array[i] = "";
                    }
                }

                //html formatted text for Fullticket Editor pane
                StringBuilder builder = new StringBuilder("<h2>Ticket ID: " + integer +"</h2> "
                +"<table border='0'>"
                +"<tr>"
                +"<td width='140'><font color='#708090'>Created on:</font></td><td>"+ StringtoDate(Array[14]) +"</td>"
                +"</tr><tr>"
                +"<td><font color='#708090'>Last update:</font></td><td>"+ StringtoDate(Array[15]) +"</td>"
                +"</tr><tr>"      
                +"<td><font color='#708090'>Problem Category:</font></td><td>"+ Array[0] +"</td>" 
                +"</tr><tr>"
                +"<td><font color='#708090'>Ticket Status: </font></td><td><u><font color="+color+">"+ Array[1] +"</font></u></td>" 
                +"</tr><tr>"
                +"<td><font color='#708090'>Employee ID</font>  </td><td>"+ Array[2] +"</td>"
                +"</tr><tr>"
                +"<td><font color='#708090'>Employee Firstname: </font> </td><td>"+ Array[3] +"</td>"
                +"</tr><tr>"
                +"<td><font color='#708090'>Employee Lastname: </font> </td><td>"+ Array[4] +"</td>"
                +"</tr><tr>"
                +"<td><font color='#708090'>Customer ID: </font> </td><td>"+ Array[5] +"</td>"
                +"</tr><tr>"
                +"<td><font color='#708090'>Customer Firstname:</font>  </td><td>"+ Array[6] +"</td>"
                +"</tr><tr>"
                +"<td><font color='#708090'>Customer Lastname:</font>  </td><td>"+ Array[7] +"</td>" 
                +"</tr><tr>"
                +"<td><font color='#708090'>Telephone:</font></td><td>"+ Array[8] +"</td>"
                +"</tr><tr>"
                +"<td><font color='#708090'>Email:</font></td><td>"+ Array[9] +"</td>"
                +"</tr><tr>"
                +"<td><font color='#708090'>Topic:</font></td><td>"+ Array[10] +"</td>"
                +"</tr><tr>"
                +"<td><font color='#708090'>Problem:</font></td><td>"+ Array[11] +"</td>"
                +"</tr><tr>"
                +"<td><font color='#708090'>Note:</font></td><td>"+ Array[12] +"</td>"
                +"</tr><tr>"
                +"<td align='left'   valign='top'><font color='#708090'>Solution:</font></td><td>"+ Array[13] +"</td>"
                +"</tr>"
                );
                
    return builder;
}       
    
     /**************************
     *  set string to history 
     *  editor pane with html codes 
     **************************/
        public static StringBuilder Htmlhistory (Integer ID, String changed, String name) {
        // Display the selected item
                String [] Array = History.searchHistory(ID,changed,name);
                for (int i = 0; i < Array.length ; i++) {
                    if (Array[i] == null || "null".equals(Array [i])) {
                        Array[i] = "";
                    }
                }
                //html formatted text for history Editor pane
                StringBuilder builder = new StringBuilder("<h2>Ticket ID: " + ID +"</h2> "
                +"<table border='0'>"
                +"<tr>"
                +"<td width='100'><font color='#708090'>Created on:</font></td><td>"+ StringtoDate(Array[0]) +"</td>"
                +"</tr><tr>"
                +"<td><font color='#708090'>Name:</font></td><td>"+ Array[1] +"</td>"
                +"</tr><tr>"      
                +"<td align='left'valign='top'><font color='#708090'>Value:</font></td><td>"+ Array[2] +"</td>" 
                +"</tr>"
                );
    return builder;
}
    
     /**********************************************
     *  Change dateformat for better reading 
     *  e.g. 2012-03-01 21:22:01.123 to Saturday 14. Jan 2012 10:12:21
     *****************************************************/
   public static String StringtoDate(String timestamp){
        try {
            SimpleDateFormat sdfToDate = new SimpleDateFormat(
                             "yyyy-MM-dd HH:mm:ss.SSS");
            Date date = sdfToDate.parse(timestamp);
            SimpleDateFormat sdfTranslated = new SimpleDateFormat(
                             "EEEE dd.MMM yyyy HH:mm:ss", Locale.getDefault());
            timestamp = sdfTranslated.format(date).toString();
        } catch (ParseException e) {
            Error_Frame.Error(e.toString());
        }
        return timestamp;
    }
}
