package aryasoft.company.arachoob.ApiConnection.ApiModules;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

import aryasoft.company.arachoob.ApiConnection.ApiInterfaceListeners.OnFetchCollectionListener;
import aryasoft.company.arachoob.ApiConnection.ApiInterfaceListeners.OnGetOrderBasketInfoListener;
import aryasoft.company.arachoob.ApiConnection.ApiInterfaceListeners.OnGetProductByGroupIdListener;
import aryasoft.company.arachoob.ApiConnection.ApiInterfaceListeners.OnGetProductGroupsListener;
import aryasoft.company.arachoob.ApiConnection.ApiInterfaceListeners.OnGetSliderListener;
import aryasoft.company.arachoob.ApiConnection.ApiModels.CollectionDataModel;
import aryasoft.company.arachoob.ApiConnection.ApiModels.ProductDataModel;
import aryasoft.company.arachoob.ApiConnection.ApiModels.ProductGroupsApiModel;
import aryasoft.company.arachoob.ApiConnection.ApiModels.SliderApiModel;
import aryasoft.company.arachoob.ApiConnection.ApiServiceGenerator;
import aryasoft.company.arachoob.ApiConnection.AraApi;
import aryasoft.company.arachoob.Utils.Listeners.OnDataReceiveStateListener;
import aryasoft.company.arachoob.Utils.Networking;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductModule
{
    private AraApi araApi;
    private OnDataReceiveStateListener onDataReceiveStateListener;
    private OnGetProductByGroupIdListener onGetProductByGroupIdListener;
    private OnFetchCollectionListener onFetchCollectionListener;
    private OnGetSliderListener onGetSliderListener;
    private OnGetProductGroupsListener onGetProductGroupsListener;

    public void setOnDataReceiveStateListener(OnDataReceiveStateListener onDataReceiveStateListener)
    {
        this.onDataReceiveStateListener = onDataReceiveStateListener;
    }


    //region Interface Setters

    public void setOnGetProductByGroupIdListener(OnGetProductByGroupIdListener onGetProductByGroupIdListener)
    {
        this.onGetProductByGroupIdListener = onGetProductByGroupIdListener;
    }

    public void setOnGetSliderListener(OnGetSliderListener onGetSliderListener)
    {
        this.onGetSliderListener = onGetSliderListener;
    }

    public void setOnFetchCollectionListener(OnFetchCollectionListener onFetchCollectionListener)
    {
        this.onFetchCollectionListener = onFetchCollectionListener;
    }


    public void setOnGetProductGroupsListener(OnGetProductGroupsListener onGetProductGroupsListener)
    {
        this.onGetProductGroupsListener = onGetProductGroupsListener;
    }

    //endregion-


    public ProductModule()
    {
        araApi = ApiServiceGenerator.getApiService();
    }

    public void getSliders()
    {
        Call<ArrayList<SliderApiModel>> callGetSlider = araApi.GetSliders();
        callGetSlider.enqueue(new Callback<ArrayList<SliderApiModel>>()
        {
            @Override
            public void onResponse(@NonNull Call<ArrayList<SliderApiModel>> call, @NonNull Response<ArrayList<SliderApiModel>> response)
            {

                onGetSliderListener.OnGetSlider(response.body() == null ? new ArrayList<SliderApiModel>() : response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<SliderApiModel>> call, @NonNull Throwable t)
            {
                onDataReceiveStateListener.OnDataReceiveState(t);
                Log.i("getSliders", t.getMessage());
            }
        });
    }


    public void getCollections()
    {
        Call<ArrayList<CollectionDataModel>> GetCollections = araApi.GetCollections();
        GetCollections.enqueue(new Callback<ArrayList<CollectionDataModel>>()
        {
            @Override
            public void onResponse(Call<ArrayList<CollectionDataModel>> call, Response<ArrayList<CollectionDataModel>> response)
            {
                onFetchCollectionListener.OnFetchCollection(response.body() == null ? new ArrayList<CollectionDataModel>() : response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<CollectionDataModel>> call, Throwable t)
            {
                onDataReceiveStateListener.OnDataReceiveState(t);
                Log.i("getCollections", t.getMessage());
            }
        });
    }

    public void getProductGroups(int productGroupId)
    {
        Call<ArrayList<ProductGroupsApiModel>> GetCollections = araApi.GetProductGroups(productGroupId);
        GetCollections.enqueue(new Callback<ArrayList<ProductGroupsApiModel>>()
        {
            @Override
            public void onResponse(@NonNull Call<ArrayList<ProductGroupsApiModel>> call, @NonNull Response<ArrayList<ProductGroupsApiModel>> response)
            {
                onGetProductGroupsListener.OnGetProductGroups(response.body() == null ? new ArrayList<ProductGroupsApiModel>() : response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<ProductGroupsApiModel>> call, Throwable t)
            {
                onDataReceiveStateListener.OnDataReceiveState(t);
                Log.i("getProductGroups", t.getMessage());
            }
        });
    }

    public void getProductByGroupId(int productGroupId, int offsetNumber, int takeNumber)
    {
        Call<ArrayList<ProductDataModel>> getProductByGroupId = araApi.GetProductByGroupId(productGroupId, takeNumber, offsetNumber);
        getProductByGroupId.enqueue(new Callback<ArrayList<ProductDataModel>>()
        {
            @Override
            public void onResponse(Call<ArrayList<ProductDataModel>> call, Response<ArrayList<ProductDataModel>> response)
            {
                onGetProductByGroupIdListener.OnGetProductByGroupId(response.body() == null ? new ArrayList<ProductDataModel>() : response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<ProductDataModel>> call, Throwable t)
            {
                onDataReceiveStateListener.OnDataReceiveState(t);
                Log.i("getProductByGroupId", t.getMessage());
            }
        });
    }
}