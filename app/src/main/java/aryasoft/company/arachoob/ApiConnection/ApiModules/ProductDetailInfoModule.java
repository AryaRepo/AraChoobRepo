package aryasoft.company.arachoob.ApiConnection.ApiModules;

import android.support.annotation.NonNull;

import aryasoft.company.arachoob.ApiConnection.ApiInterfaceListeners.OnFetchProductDetailInfoListener;
import aryasoft.company.arachoob.ApiConnection.ApiModels.ProductDetailInfoApiModel;
import aryasoft.company.arachoob.ApiConnection.ApiServiceGenerator;
import aryasoft.company.arachoob.ApiConnection.AraApi;
import aryasoft.company.arachoob.Utils.Listeners.OnDataReceiveStateListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailInfoModule
{
    private AraApi araApi;
    private OnFetchProductDetailInfoListener onFetchProductDetailInfoListener;
    private OnDataReceiveStateListener onDataReceiveStateListener;

    public void setOnDataReceiveStateListener(OnDataReceiveStateListener onDataReceiveStateListener)
    {
        this.onDataReceiveStateListener = onDataReceiveStateListener;
    }

    public ProductDetailInfoModule()
    {
        araApi = ApiServiceGenerator.getApiService();
    }

    public void setOnFetchProductDetailInfoListener(OnFetchProductDetailInfoListener onFetchProductDetailInfoListener)
    {
        this.onFetchProductDetailInfoListener = onFetchProductDetailInfoListener;
    }


    public void getProductInfoById(int productId)
    {
        Call<ProductDetailInfoApiModel> callGetProductInfoById = araApi.GetProductInfoById(productId);
        callGetProductInfoById.enqueue(new Callback<ProductDetailInfoApiModel>()
        {
            @Override
            public void onResponse(@NonNull Call<ProductDetailInfoApiModel> call, @NonNull Response<ProductDetailInfoApiModel> response)
            {
                onFetchProductDetailInfoListener.OnFetchProductDetailInfo(response.body() == null ? new ProductDetailInfoApiModel() : response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ProductDetailInfoApiModel> call, @NonNull Throwable t)
            {
                onDataReceiveStateListener.OnDataReceiveState(t);
            }
        });
    }

}
