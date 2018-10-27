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
import android.widget.Toast;
import com.eugeneek.smilebar.SmileBar;
import aryasoft.company.arachoob.Adapters.CommentsAdapter;
import aryasoft.company.arachoob.R;

public class CommentsTabFragment extends Fragment
{
    private Context fragmentContext;
    private RecyclerView recyclerComments;
    private LinearLayoutManager recyclerLayoutManager;
    private CommentsAdapter recyclerCommentAdapter;
    private FloatingActionButton btnAddComment;
    private AlertDialog commentDialogAlertHolder;

    public CommentsTabFragment()
    {

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
        initViews(view);
        initEvents();
    }

    private void initViews(View view)
    {
        recyclerComments = view.findViewById(R.id.recyclerComments);
        btnAddComment = view.findViewById(R.id.btnAddComment);
        recyclerLayoutManager = new LinearLayoutManager(fragmentContext, LinearLayoutManager.VERTICAL, false);
        recyclerComments.setLayoutManager(recyclerLayoutManager);
        recyclerCommentAdapter = new CommentsAdapter(fragmentContext);
        recyclerComments.setAdapter(recyclerCommentAdapter);

    }

    private void initEvents()
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
        btnAddComment.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showRegisterComment();
            }
        });
    }

    private void showRegisterComment()
    {
        //check for user id
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
                }
                else if (commentRate.getRating() == 0)
                {
                    Toast.makeText(fragmentContext, "لطفا ایموجی مورد نظر را انتخاب کنید(از قسمت بالا)", Toast.LENGTH_SHORT).show();
                }
                else if (edtCommentTitle.getText().toString().length() == 0)
                {
                    Toast.makeText(fragmentContext, "لطفا عنوان نظر را وارد کنید.", Toast.LENGTH_SHORT).show();
                }
                else if (edtCommentText.getText().toString().length() == 0)
                {
                    Toast.makeText(fragmentContext, "لطفا متن نظر را وارد کنید.", Toast.LENGTH_SHORT).show();
                }

               // commentDialogAlertHolder.dismiss();
            }
        });
        commentDialogAlert.setView(AlertView);
        commentDialogAlertHolder = commentDialogAlert.show();
    }
}
