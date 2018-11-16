package aryasoft.company.arachoob.ApiConnection.ApiModels;


import java.util.ArrayList;

import aryasoft.company.arachoob.Models.SubmitOrderDetailModel;

public class SubmitOrderApiModel
{
    public int UserId;
    public int TotalPrice;
    public String OrderImageName;
    public ArrayList<SubmitOrderDetailModel> OrderDetailList;
}
