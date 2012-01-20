package Helpdesk.java.helpdesk.mvc.Model;

import java.util.ArrayList;

public class CategoryModel {
        private ArrayList<Category> arr_data;
        public void CategoryModel () {
            this.arr_data = Category.showAll();
        }
        
          public Integer getCategoryObjectID (String name) {
            Integer data = null;
            for (int i = 0; i < this.arr_data.size(); i++) {
                if (name.equals(this.arr_data.get(i).getDescr()))
                    data = this.arr_data.get(i).getID();
                }
            return data;
          }
          
          public String getCategoryObjectDescr (Integer ID) {
              String data = "";
              for (int i = 0; i < this.arr_data.size(); i++) {
                  if (ID == this.arr_data.get(i).getID()) {
                      data = this.arr_data.get(i).getDescr();
                  }
              }
              return data;
          }
}
