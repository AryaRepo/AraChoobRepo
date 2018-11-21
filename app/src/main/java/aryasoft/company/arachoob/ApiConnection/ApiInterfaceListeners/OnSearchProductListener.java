package aryasoft.company.arachoob.ApiConnection.ApiInterfaceListeners;

import java.util.ArrayList;

import aryasoft.company.arachoob.ApiConnection.ApiModels.ProductDataModel;

public interface OnSearchProductListener
{
    void OnSearchProduct(ArrayList<ProductDataModel> searchData);
}
