package aryasoft.company.arachoob.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.eugeneek.smilebar.SmileBar;

import java.util.ArrayList;

import aryasoft.company.arachoob.Adapters.CommentsAdapter;
import aryasoft.company.arachoob.ApiConnection.ApiInterfaceListeners.OnLoadMoreCommentsListener;
import aryasoft.company.arachoob.ApiConnection.ApiInterfaceListeners.OnSubmitCommentListener;
import aryasoft.company.arachoob.ApiConnection.ApiModels.ProductCommentApiModel;
import aryasoft.company.arachoob.ApiConnection.ApiModules.CommentModule;
import aryasoft.company.arachoob.Models.CommentDataModel;
import aryasoft.company.arachoob.R;
import aryasoft.company.arachoob.Utils.CuteToast;
import aryasoft.company.arachoob.Utils.Listeners.OnDataReceiveStateListener;
import aryasoft.company.arachoob.Utils.SharedPreferencesHelper;
import aryasoft.company.arachoob.Utils.SweetLoading;

public class CommentsTabFragment extends Fragment
{
    private Context fragmentContext;
    private RelativeLayout relEmptyCommentProduct;
    private RecyclerView recyclerComments;
    private LinearLayoutManager recyclerLayoutManager;
    private CommentsAdapter recyclerCommentAdapter;
    private FloatingActionButton btnAddComment;
    private AlertDialog commentDialogAlertHolder;
    private ArrayList<ProductCommentApiModel> productComments;
    private int productId;
    private int UserId;
    private SweetLoading Loading;
    private boolean IsLoading;
    private boolean isLoadMoreEnd = false;
    private CommentModule commentModule;

    public CommentsTabFragment()
    {

    }

    public CommentsTabFragment(int productId, ArrayList<ProductCommentApiModel> productComments)
    {
        this.productComments = productComments;
        this.productId = productId;
        this.commentModule = new CommentModule();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_comments_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        fragmentContext = view.getContext();
        Loading = new SweetLoading.Builder().build(fragmentContext);
        UserId = SharedPreferencesHelper.readInt("user_id");
        initViews(view);
        initEvents();
    }

    private void initViews(View view)
    {
        relEmptyCommentProduct = view.findViewById(R.id.relEmptyCommentProduct);
        recyclerComments = view.findViewById(R.id.recyclerComments);
        btnAddComment = view.findViewById(R.id.btnAddComment);
        if (productComments.size() == 0)
        {
            recyclerComments.setVisibility(View.GONE);
            relEmptyCommentProduct.setVisibility(View.VISIBLE);
            return;
        }
        recyclerLayoutManager = new LinearLayoutManager(fragmentContext, LinearLayoutManager.VERTICAL, false);
        recyclerComments.setLayoutManager(recyclerLayoutManager);
        recyclerCommentAdapter = new CommentsAdapter(fragmentContext);
        recyclerComments.setAdapter(recyclerCommentAdapter);
        recyclerCommentAdapter.addCommentList(productComments);
    }

    private void initEvents()
    {
        commentModule.setOnDataReceiveStateListener(new OnDataReceiveStateListener()
        {
            @Override
            public void OnDataReceiveState(Throwable ex)
            {
                IsLoading = false;
                if (Loading.isShowing())
                {
                    Loading.hide();
                }
                new CuteToast.Builder(getActivity()).setText(getString(R.string.noInternetText)).setDuration(Toast.LENGTH_LONG).show();
            }
        });
        recyclerHandleTouchEvent();
        recyclerCommentLoadMore();
        btnAddComment.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showRegisterComment();
            }
        });
    }

    private void recyclerCommentLoadMore()
    {
        recyclerComments.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
            {
                if (isLoadMoreEnd)
                {
                    return;
                }
                if (recyclerCommentAdapter.getItemCount() >= 10)
                {
                    if (IsLoading)
                    {
                        return;
                    }
                    int VisibleItemCount = recyclerLayoutManager.getChildCount();
                    int TotalItemCount = recyclerLayoutManager.getItemCount();
                    int PastVisibleItem = recyclerLayoutManager.findFirstVisibleItemPosition();
                    if ((VisibleItemCount + PastVisibleItem) >= TotalItemCount)
                    {
                        IsLoading = true;
                        getMoreComments(recyclerCommentAdapter.getItemCount() + 10);
                    }
                }
                super.onScrolled(recyclerView, dx, dy);

            }
        });
    }

    private void getMoreComments(int offsetItem)
    {
        Loading.show();
        commentModule.setOnLoadMoreCommentsListener(new OnLoadMoreCommentsListener()
        {
            @Override
            public void OnLoadMoreComments(ArrayList<ProductCommentApiModel> newCommentsList)
            {
                if(newCommentsList.size()==0)
                {
                    isLoadMoreEnd=true;
                }
                else
                {
                    productComments.addAll(newCommentsList);
                    recyclerCommentAdapter.addCommentList(productComments);
                    IsLoading = false;
                }
                Loading.hide();

            }
        });
        commentModule.getProductComments(productId, offsetItem, 10);

    }

    private void recyclerHandleTouchEvent()
    {

        recyclerComments.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        v.getParent().requestDisallowInterceptTouchEvent(true);

                        break;
                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);

                        break;
                }

                v.onTouchEvent(event);
                return true;
            }
        });
    }

    private void showRegisterComment()
    {
        if (UserId == 0)
        {
            Toast.makeText(fragmentContext, "کاربر گرامی لطفا از منوی ثبت نام/ورود در صفحه ی اصلی وارد حساب کاربری شوید.", Toast.LENGTH_SHORT).show();
        }

        android.support.v7.app.AlertDialog.Builder commentDialogAlert = new android.support.v7.app.AlertDialog.Builder(fragmentContext);
        View AlertView = View.inflate(fragmentContext, R.layout.add_comment_dialog_layout, null);
        final SmileBar commentRate = AlertView.findViewById(R.id.commentRate);
        final EditText edtCommentTitle = AlertView.findViewById(R.id.edtCommentTitle);
        final EditText edtCommentText = AlertView.findViewById(R.id.edtCommentText);
        final Button btnRegisterComment = AlertView.findViewById(R.id.btnRegisterComment);
        btnRegisterComment.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (commentRate.getRating() == 0 && edtCommentTitle.getText().toString().length() == 0 && edtCommentText.getText().toString().length() == 0)
                {
                    Toast.makeText(fragmentContext, "لطفا موارد خواسته شده را کامل کنید.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (commentRate.getRating() == 0)
                {
                    Toast.makeText(fragmentContext, "لطفا ایموجی مورد نظر را انتخاب کنید(از قسمت بالا)", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (edtCommentTitle.getText().toString().length() == 0)
                {
                    Toast.makeText(fragmentContext, "لطفا عنوان نظر را وارد کنید.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (edtCommentText.getText().toString().length() == 0)
                {
                    Toast.makeText(fragmentContext, "لطفا متن نظر را وارد کنید.", Toast.LENGTH_SHORT).show();
                    return;
                }
                CommentDataModel commentDataModel = new CommentDataModel();
                commentDataModel.userId = UserId;
                commentDataModel.productId = productId;
                commentDataModel.commentTitle = edtCommentTitle.getText().toString();
                commentDataModel.commentText = edtCommentText.getText().toString();
                commentDataModel.pointValue = commentRate.getRating();
                commentDataModel.commentTitle = edtCommentTitle.getText().toString();
                CommentModule commentModule = new CommentModule();
                commentDialogAlertHolder.dismiss();
                commentModule.setOnSubmitCommentListener(new OnSubmitCommentListener()
                {
                    @Override
                    public void OnSubmitComment(boolean submitState)
                    {
                        Loading.hide();
                        if (submitState)
                        {
                            Toast.makeText(getContext(), "کاربر گرامی نظر شما در سیستم ثبت شد.\n و پس از تایید نمایش داده خواهد شد.", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(getContext(), "کاربر گرامی ثبت نظر شما در سیستم با مشکل مواجه شد.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                commentModule.setOnDataReceiveStateListener(new OnDataReceiveStateListener()
                {
                    @Override
                    public void OnDataReceiveState(Throwable ex)
                    {
                        Loading.hide();
                        new CuteToast.Builder(getActivity()).setText(getString(R.string.noInternetText)).setDuration(Toast.LENGTH_LONG).show();
                    }
                });
                commentModule.SubmitComment(commentDataModel);
                Loading.show();

            }
        });
        commentDialogAlert.setView(AlertView);
        commentDialogAlertHolder = commentDialogAlert.show();
    }
}
