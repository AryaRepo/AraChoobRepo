package aryasoft.company.arachoob.ApiConnection.ApiModules;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import aryasoft.company.arachoob.ApiConnection.ApiInterfaceListeners.OnLoadMoreCommentsListener;
import aryasoft.company.arachoob.ApiConnection.ApiInterfaceListeners.OnSubmitCommentListener;
import aryasoft.company.arachoob.ApiConnection.ApiModels.ProductCommentApiModel;
import aryasoft.company.arachoob.ApiConnection.ApiServiceGenerator;
import aryasoft.company.arachoob.ApiConnection.AraApi;
import aryasoft.company.arachoob.Models.CommentDataModel;
import aryasoft.company.arachoob.Utils.Listeners.OnDataReceiveStateListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentModule
{
    private AraApi araApi;
    private OnSubmitCommentListener onSubmitCommentListener;
    private OnDataReceiveStateListener onDataReceiveStateListener;
    private OnLoadMoreCommentsListener onLoadMoreCommentsListener;

    public void setOnDataReceiveStateListener(OnDataReceiveStateListener onDataReceiveStateListener)
    {
        this.onDataReceiveStateListener = onDataReceiveStateListener;
    }

    public void setOnLoadMoreCommentsListener(OnLoadMoreCommentsListener onLoadMoreCommentsListener)
    {
        this.onLoadMoreCommentsListener = onLoadMoreCommentsListener;
    }

    public CommentModule()
    {
        araApi = ApiServiceGenerator.getApiService();
    }

    public void setOnSubmitCommentListener(OnSubmitCommentListener onSubmitCommentListener)
    {
        this.onSubmitCommentListener = onSubmitCommentListener;
    }

    public void SubmitComment(CommentDataModel commentDataModel)
    {
        Call<Boolean> SubmitComment = araApi.SubmitComment(commentDataModel.userId, commentDataModel.commentTitle, commentDataModel.commentText, commentDataModel.pointValue, commentDataModel.productId);
        SubmitComment.enqueue(new Callback<Boolean>()
        {
            @Override
            public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response)
            {
                if (response.body() == null)
                {
                    onSubmitCommentListener.OnSubmitComment(false);
                    return;
                }
                onSubmitCommentListener.OnSubmitComment(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Boolean> call, @NonNull Throwable t)
            {
                onDataReceiveStateListener.OnDataReceiveState(t);
            }
        });
    }

    public void getProductComments(int productId, int offsetItem, int takeItem)
    {
        Call<ArrayList<ProductCommentApiModel>> callGetProductComments = araApi.GetProductComments(productId, offsetItem, takeItem);
        callGetProductComments.enqueue(new Callback<ArrayList<ProductCommentApiModel>>()
        {
            @Override
            public void onResponse(@NonNull Call<ArrayList<ProductCommentApiModel>> call, @NonNull Response<ArrayList<ProductCommentApiModel>> response)
            {

                onLoadMoreCommentsListener.OnLoadMoreComments(response.body()!=null?response.body():new ArrayList<ProductCommentApiModel>());
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<ProductCommentApiModel>> call, @NonNull Throwable t)
            {
                onDataReceiveStateListener.OnDataReceiveState(t);
            }
        });
    }
}
