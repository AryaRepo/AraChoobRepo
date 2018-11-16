package aryasoft.company.arachoob.ApiConnection.ApiInterfaceListeners;

import java.util.ArrayList;

import aryasoft.company.arachoob.ApiConnection.ApiModels.GetUserOrderDetail;

public interface OnGetUserDetailListener
{
    void OnGetUserDetail(ArrayList<GetUserOrderDetail>userOrderDetailList);
}
