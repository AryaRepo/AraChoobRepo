package aryasoft.company.arachoob.ApiConnection.ApiInterfaceListeners;

import java.util.ArrayList;

import aryasoft.company.arachoob.ApiConnection.ApiModels.ProductDataModel;

public interface OnGetOrderBasketInfoListener
{
    void OnGetOrderBasketInfo(ArrayList<ProductDataModel> productBasketList);
}
