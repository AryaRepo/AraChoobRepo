package aryasoft.company.arachoob.ApiConnection.ApiInterfaceListeners;

import java.util.ArrayList;

import aryasoft.company.arachoob.ApiConnection.ApiModels.ProductGroupsApiModel;

public interface OnGetProductGroupsListener
{
    void OnGetProductGroups(ArrayList<ProductGroupsApiModel> productGroups);
}
