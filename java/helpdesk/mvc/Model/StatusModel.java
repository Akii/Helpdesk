package Helpdesk.java.helpdesk.mvc.Model;

import java.util.ArrayList;

public class StatusModel {
        private ArrayList<Status> arr_data;
        public void StatusModel () {
            this.arr_data = Status.showAll();
        }
        
          public Integer getStatusObjectID (String name) {
            Integer data = null;
            for (int i = 0; i < this.arr_data.size(); i++) {
                if (name.equals(this.arr_data.get(i).getDescr()))
                    data = this.arr_data.get(i).getID();
                }
            return data;
          }
          
          public String getStatusObjectDescr (Integer ID) {
              String data = "";
              for (int i = 0; i < this.arr_data.size(); i++) {
                  if (ID == this.arr_data.get(i).getID()) {
                      data = this.arr_data.get(i).getDescr();
                  }
              }
              return data;
          }
}
