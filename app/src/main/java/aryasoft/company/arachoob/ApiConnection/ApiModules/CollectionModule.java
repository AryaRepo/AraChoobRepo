package aryasoft.company.arachoob.ApiConnection.ApiModules;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import aryasoft.company.arachoob.ApiConnection.ApiInterfaceListeners.OnGetSectionedCollectionDataListener;
import aryasoft.company.arachoob.ApiConnection.ApiModels.ProductDataModel;
import aryasoft.company.arachoob.ApiConnection.ApiServiceGenerator;
import aryasoft.company.arachoob.ApiConnection.ApiServiceRequest;
import aryasoft.company.arachoob.Utils.Listeners.OnDataReceiveStateListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionModule
{
    private ApiServiceRequest apiServiceRequest;
    private OnDataReceiveStateListener onDataReceiveStateListener;
    private OnGetSectionedCollectionDataListener onGetSectionedCollectionDataListener;

    public void setOnDataReceiveStateListener(OnDataReceiveStateListener onDataReceiveStateListener)
    {
        this.onDataReceiveStateListener = onDataReceiveStateListener;
    }

    public void setOnGetSectionedCollectionDataListener(OnGetSectionedCollectionDataListener onGetSectionedCollectionDataListener)
    {
        this.onGetSectionedCollectionDataListener = onGetSectionedCollectionDataListener;
    }

    public CollectionModule()
    {
        apiServiceRequest = ApiServiceGenerator.getApiService();
    }

    public void getTopSeals(int skipItem, int takeItem)
    {
        Call<ArrayList<ProductDataModel>> CallGetTopSeals = apiServiceRequest.GetTopSeals(skipItem, takeItem);
        CallGetTopSeals.enqueue(new Callback<ArrayList<ProductDataModel>>()
        {
            @Override
            public void onResponse(@NonNull Call<ArrayList<ProductDataModel>> call, Response<ArrayList<ProductDataModel>> response)
            {
                onGetSectionedCollectionDataListener.OnGetSectionedCollectionData(response.body()!=null?response.body():new ArrayList<ProductDataModel>());
            }

            @Override
            public void onFailure(Call<ArrayList<ProductDataModel>> call, Throwable t)
            {
                onDataReceiveStateListener.OnDataReceiveState(t);
            }
        });
    }
    public void getCollectionById(int collectionId,int skipItem, int takeItem)
    {
        Call<ArrayList<ProductDataModel>> CallGetCollectionById = apiServiceRequest.GetCollectionById(collectionId,skipItem, takeItem);
        CallGetCollectionById.enqueue(new Callback<ArrayList<ProductDataModel>>()
        {
            @Override
            public void onResponse(@NonNull Call<ArrayList<ProductDataModel>> call, Response<ArrayList<ProductDataModel>> response)
            {
                onGetSectionedCollectionDataListener.OnGetSectionedCollectionData(response.body()!=null?response.body():new ArrayList<ProductDataModel>());
            }

            @Override
            public void onFailure(Call<ArrayList<ProductDataModel>> call, Throwable t)
            {
                onDataReceiveStateListener.OnDataReceiveState(t);
            }
        });
    }

    public void getDiscountBasketById(int collectionId,int skipItem, int takeItem)
    {
        Call<ArrayList<ProductDataModel>> CallGetDiscountBasketById = apiServiceRequest.GetDiscountBasketById(collectionId,skipItem, takeItem);
        CallGetDiscountBasketById.enqueue(new Callback<ArrayList<ProductDataModel>>()
        {
            @Override
            public void onResponse(@NonNull Call<ArrayList<ProductDataModel>> call, Response<ArrayList<ProductDataModel>> response)
            {
                onGetSectionedCollectionDataListener.OnGetSectionedCollectionData(response.body()!=null?response.body():new ArrayList<ProductDataModel>());
            }

            @Override
            public void onFailure(Call<ArrayList<ProductDataModel>> call, Throwable t)
            {
                onDataReceiveStateListener.OnDataReceiveState(t);
            }
        });
    }
}
