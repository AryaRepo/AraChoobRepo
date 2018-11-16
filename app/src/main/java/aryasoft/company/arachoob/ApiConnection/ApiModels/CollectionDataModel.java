package aryasoft.company.arachoob.ApiConnection.ApiModels;

import java.util.ArrayList;

public class CollectionDataModel
{
    public int SectionId;
    public int CollectionId;
    public String CollectionTitle;
    public ArrayList<ProductDataModel> Products=new ArrayList<>();
}
