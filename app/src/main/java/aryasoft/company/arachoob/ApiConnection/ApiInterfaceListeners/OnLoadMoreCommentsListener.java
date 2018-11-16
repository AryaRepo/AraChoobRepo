package aryasoft.company.arachoob.ApiConnection.ApiInterfaceListeners;

import java.util.ArrayList;

import aryasoft.company.arachoob.ApiConnection.ApiModels.ProductCommentApiModel;

public interface OnLoadMoreCommentsListener
{
   void OnLoadMoreComments(ArrayList<ProductCommentApiModel> newCommentsList);
}
