package aryasoft.company.arachoob.ApiConnection.ApiInterfaceListeners;

import java.util.ArrayList;

import aryasoft.company.arachoob.ApiConnection.ApiModels.GetUserOrderApiModel;

public interface OnGetUserOrderListener
{
    void OnGetUserOrder(ArrayList<GetUserOrderApiModel> userOrdersList);
}
