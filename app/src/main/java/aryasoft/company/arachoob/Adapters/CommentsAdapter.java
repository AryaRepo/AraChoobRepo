package aryasoft.company.arachoob.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

import aryasoft.company.arachoob.Models.CommentModel;
import aryasoft.company.arachoob.R;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.SimilarProductAdapterViewHolder>
{
    private Context context;
    private ArrayList<CommentModel> commentList;

    public CommentsAdapter(Context context)
    {
        this.context = context;
        this.commentList = new ArrayList<>();
    }

    @NonNull
    @Override
    public SimilarProductAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new SimilarProductAdapterViewHolder(LayoutInflater.from(context).inflate(R.layout.comment_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarProductAdapterViewHolder holder, int position)
    {

        if(commentList.size()==0)
            return;
        holder.txtUserProfileName.setText(commentList.get(position).UserNameFamily);
        holder.txtCommentTitle.setText(commentList.get(position).CommentTitle);
        holder.txtCommentText.setText(commentList.get(position).CommentText);
        Glide.with(context).load(context.getString(R.string.BaseUrl)+context.getString(R.string.ProductImageFolder)).into(holder.imgUserProfileThumb);

    }

    @Override
    public int getItemCount()
    {
        return 10;
      //  return similarProductList.size();
    }

    public void addSimilarProduct(ArrayList<CommentModel> commentList)
    {
        this.commentList.addAll(commentList);
        this.notifyDataSetChanged();
    }

    class SimilarProductAdapterViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView imgUserProfileThumb;
        private TextView txtCommentTitle;
        private TextView txtCommentText;
        private TextView txtUserProfileName;
        SimilarProductAdapterViewHolder(@NonNull View itemView)
        {
            super(itemView);
            //------------------
            imgUserProfileThumb=itemView.findViewById(R.id.imgUserProfileThumb);
            txtCommentTitle=itemView.findViewById(R.id.txtCommentTitle);
            txtCommentText=itemView.findViewById(R.id.txtCommentText);
            txtUserProfileName=itemView.findViewById(R.id.txtUserProfileName);
        }
    }
}
