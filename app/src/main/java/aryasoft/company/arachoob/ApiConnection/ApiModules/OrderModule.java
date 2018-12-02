package aryasoft.company.arachoob.ApiConnection.ApiModules;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

import aryasoft.company.arachoob.ApiConnection.ApiInterfaceListeners.OnGetOrderBasketInfoListener;
import aryasoft.company.arachoob.ApiConnection.ApiInterfaceListeners.OnGetUserDetailListener;
import aryasoft.company.arachoob.ApiConnection.ApiInterfaceListeners.OnGetUserOrderListener;
import aryasoft.company.arachoob.ApiConnection.ApiInterfaceListeners.OnSubmitOrderListener;
import aryasoft.company.arachoob.ApiConnection.ApiModels.GetUserOrderApiModel;
import aryasoft.company.arachoob.ApiConnection.ApiModels.GetUserOrderDetail;
import aryasoft.company.arachoob.ApiConnection.ApiModels.ProductDataModel;
import aryasoft.company.arachoob.ApiConnection.ApiModels.SubmitOrderApiModel;
import aryasoft.company.arachoob.ApiConnection.ApiServiceGenerator;
import aryasoft.company.arachoob.ApiConnection.ApiServiceRequest;
import aryasoft.company.arachoob.Utils.Listeners.OnDataReceiveStateListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderModule
{
    private ApiServiceRequest apiServiceRequest;
    private OnSubmitOrderListener onSubmitOrderListener;
    private OnGetOrderBasketInfoListener onGetOrderBasketInfoListener;
    private OnDataReceiveStateListener onDataReceiveStateListener;
    private OnGetUserOrderListener onGetUserOrderListener;
    private OnGetUserDetailListener onGetUserDetailListener;

    public void setOnGetUserDetailListener(OnGetUserDetailListener onGetUserDetailListener)
    {
        this.onGetUserDetailListener = onGetUserDetailListener;
    }

    public void setOnGetUserOrderListener(OnGetUserOrderListener onGetUserOrderListener)
    {
        this.onGetUserOrderListener = onGetUserOrderListener;
    }

    public void setOnDataReceiveStateListener(OnDataReceiveStateListener onDataReceiveStateListener)
    {
        this.onDataReceiveStateListener = onDataReceiveStateListener;
    }

    public void setOnSubmitOrderListener(OnSubmitOrderListener onSubmitOrderListener)
    {
        this.onSubmitOrderListener = onSubmitOrderListener;
    }

    public void setOnGetOrderBasketInfoListener(OnGetOrderBasketInfoListener onGetOrderBasketInfoListener)
    {
        this.onGetOrderBasketInfoListener = onGetOrderBasketInfoListener;
    }

    public OrderModule()
    {
        apiServiceRequest = ApiServiceGenerator.getApiService();
    }


    public void SubmitOrder(SubmitOrderApiModel submitOrderModel)
    {
        Call<Boolean> SubmitOrder = apiServiceRequest.SubmitOrder(submitOrderModel);
        SubmitOrder.enqueue(new Callback<Boolean>()
        {
            @Override
            public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response)
            {
                onSubmitOrderListener.OnSubmitOrder(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Boolean> call, @NonNull Throwable t)
            {
                onDataReceiveStateListener.OnDataReceiveState(t);
            }
        });
    }

    public void getOrderBasketInfo(ArrayList<Integer> products)
    {
        Call<ArrayList<ProductDataModel>> GetOrderBasketInfo = apiServiceRequest.GetOrderBasketInfo(products);
        GetOrderBasketInfo.enqueue(new Callback<ArrayList<ProductDataModel>>()
        {
            @Override
            public void onResponse(Call<ArrayList<ProductDataModel>> call, Response<ArrayList<ProductDataModel>> response)
            {
                onGetOrderBasketInfoListener.OnGetOrderBasketInfo(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<ProductDataModel>> call, Throwable t)
            {
                Log.i("getOrderBasketInfo", t.getMessage());
                onDataReceiveStateListener.OnDataReceiveState(t);
            }
        });
    }

    public void getUserOrder(int userId, int skipItem, int takeItem)
    {
        Call<ArrayList<GetUserOrderApiModel>> callGetUserOrder = apiServiceRequest.GetUserOrder(userId, skipItem, takeItem);
        callGetUserOrder.enqueue(new Callback<ArrayList<GetUserOrderApiModel>>()
        {
            @Override
            public void onResponse(@NonNull Call<ArrayList<GetUserOrderApiModel>> call, @NonNull Response<ArrayList<GetUserOrderApiModel>> response)
            {
                onGetUserOrderListener.OnGetUserOrder(response.body()!=null?response.body():new ArrayList<GetUserOrderApiModel>());
            }

            @Override
            public void onFailure(Call<ArrayList<GetUserOrderApiModel>> call, Throwable t)
            {
                onDataReceiveStateListener.OnDataReceiveState(t);
            }
        });
    }

    public void getUserOrderDetail(int orderId)
    {
        Call<ArrayList<GetUserOrderDetail>> callGetUserOrderDetail = apiServiceRequest.GetUserOrderDetail(orderId);
        callGetUserOrderDetail.enqueue(new Callback<ArrayList<GetUserOrderDetail>>()
        {
            @Override
            public void onResponse(@NonNull Call<ArrayList<GetUserOrderDetail>> call, @NonNull Response<ArrayList<GetUserOrderDetail>> response)
            {
                onGetUserDetailListener.OnGetUserDetail(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<GetUserOrderDetail>> call, Throwable t)
            {
                onDataReceiveStateListener.OnDataReceiveState(t);
            }
        });
    }
}
