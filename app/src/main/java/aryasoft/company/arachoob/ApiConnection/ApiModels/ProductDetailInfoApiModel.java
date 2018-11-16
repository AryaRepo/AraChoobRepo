package aryasoft.company.arachoob.ApiConnection.ApiModels;

import java.util.ArrayList;

public class ProductDetailInfoApiModel
{
    public int ProductId ;
    public String ProductGroup ;
    public String Description ;
    public int  DiscountPercent;
    public String SummeryDescription ;
    public String UnitTitle ;
    public ArrayList<String> ImageList ;
    public ArrayList<SimilarProductApiModel> SimilarProduct ;
    public ArrayList<ProductCommentApiModel> ProductComments;
}
