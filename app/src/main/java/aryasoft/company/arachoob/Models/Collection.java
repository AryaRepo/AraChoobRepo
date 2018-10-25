package aryasoft.company.arachoob.Models;

import java.util.ArrayList;
import java.util.List;

public class Collection {
    private String Title;
    private ArrayList<Product> ProductsList;

    public Collection(String title, ArrayList<Product> productsList) {
        Title = title;
        ProductsList = productsList;
    }

    public String getTitle() {
        return Title;
    }

    public ArrayList<Product> getProductsList() {
        return ProductsList;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setProductsList(ArrayList<Product> productsList) {
        ProductsList = productsList;
    }
}
