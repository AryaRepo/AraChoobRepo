package aryasoft.company.arachoob.Utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public  class RecyclerLoadMore extends RecyclerView.OnScrollListener
{

  /*  @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
    {
        if (!isLoadMoreEnd)
        {
            if (productAdapter.getItemCount() >= 10)
            {
                if (IsLoading)
                {
                    return;
                }
                int VisibleItemCount = recyclerGridLayoutManager.getChildCount();
                int TotalItemCount = recyclerGridLayoutManager.getItemCount();
                int PastVisibleItem = recyclerGridLayoutManager.findFirstVisibleItemPosition();
                if ((VisibleItemCount + PastVisibleItem) >= TotalItemCount)
                {
                    searchProducts(edtSearch.getText().toString());
                }
            }
        }
        super.onScrolled(recyclerView, dx, dy);

    }*/
}
