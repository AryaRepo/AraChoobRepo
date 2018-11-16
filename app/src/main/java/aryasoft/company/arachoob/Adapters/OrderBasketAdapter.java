package aryasoft.company.arachoob.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import aryasoft.company.arachoob.Models.OrderBasketModel;
import aryasoft.company.arachoob.R;
import aryasoft.company.arachoob.Utils.Listeners.OnCartChangedListener;
import aryasoft.company.arachoob.Utils.ShoppingCartManger;
import aryasoft.company.arachoob.Utils.VectorDrawablePreLollipopHelper;
import aryasoft.company.arachoob.Utils.VectorView;
import cn.pedant.SweetAlert.SweetAlertDialog;


public class OrderBasketAdapter extends RecyclerView.Adapter<OrderBasketAdapter.OrderBasketViewHolder>
{
    private Dialog DescriptionDialog;
    private Context context;
    private ArrayList<OrderBasketModel> orderBasketList;
    private BounceInterpolator interpolator;
    private OnCartChangedListener onCartChangedListener;

    public void setOnCartChangedListener(OnCartChangedListener onCartChangedListener)
    {
        this.onCartChangedListener = onCartChangedListener;
    }

    public ArrayList<OrderBasketModel> getOrderBasketList()
    {
        return orderBasketList;
    }

    public OrderBasketAdapter(Context context, ArrayList<OrderBasketModel> orderBasketList)
    {
        this.context = context;
        this.orderBasketList = orderBasketList;
        interpolator = new BounceInterpolator();
    }

    @NonNull
    @Override
    public OrderBasketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new OrderBasketViewHolder(LayoutInflater.from(context).inflate(R.layout.order_basket_item_view, parent, false));
    }

    public void clearShoppingCart()
    {
        ShoppingCartManger.ClearCart();
        orderBasketList.clear();
        notifyDataSetChanged();
        onCartChangedListener.OnCartChanged(orderBasketList);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderBasketViewHolder holder, int position)
    {
        if (orderBasketList.size() == 0)
        {
            return;
        }
        if (orderBasketList.get(position).ProductImageName != null)
        {
            Glide.with(context).load(context.getString(R.string.BaseUrl) + context.getString(R.string.ProductImageFolder) + orderBasketList.get(position).ProductImageName).into(holder.imgProductImageBasket);
        }
        else
        {
            Glide.with(context).load(R.drawable.no_img).into(holder.imgProductImageBasket);
        }

        holder.txtProductTitle.setText(orderBasketList.get(position).ProductTitle);
        holder.txtProductPrice.setText(orderBasketList.get(position).ProductFinalPrice + " تومان ");
        holder.txtProductCountBasket.setText(orderBasketList.get(position).ProductCount + " عدد ");
        holder.btnDeleteProduct.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SweetAlertDialog deleteProductAlert = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
                deleteProductAlert.setTitle("حذف کالا ؟!");
                deleteProductAlert.setContentText("آیا کالا از سبد حذف شود!؟!");
                deleteProductAlert.setCancelText("خیر ، نشه!");
                deleteProductAlert.setConfirmText("باشه ، پاک بشه");
                deleteProductAlert.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener()
                {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog)
                    {
                        sweetAlertDialog.cancel();
                    }
                });
                deleteProductAlert.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog)
                    {
                        ShoppingCartManger.RemoveProductById(orderBasketList.get(holder.getAdapterPosition()).ProductId);
                        orderBasketList.remove(holder.getAdapterPosition());
                        notifyDataSetChanged();
                        onCartChangedListener.OnCartChanged(orderBasketList);
                        sweetAlertDialog.cancel();
                    }
                });
                deleteProductAlert.show();
            }
        });

        holder.btnAddDescription.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showAddDescriptionDialog(holder.getAdapterPosition());
            }
        });
        holder.btnIncreaseProductBasket.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                holder.txtProductCountBasket.setText(ShoppingCartManger.CartManagerIncrease(orderBasketList.get(holder.getAdapterPosition()).ProductId) + "");
                Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.scale_anim);
                myAnim.setInterpolator(interpolator);
                v.startAnimation(myAnim);
                OrderBasketModel product = orderBasketList.get(holder.getAdapterPosition());
                ++product.ProductCount;
                orderBasketList.set(holder.getAdapterPosition(), product);
                onCartChangedListener.OnCartChanged(orderBasketList);
            }
        });
        holder.btnDecreaseProductBasket.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.scale_anim);
                myAnim.setInterpolator(interpolator);
                v.startAnimation(myAnim);
                //----------------------------
                int productItemPosition = holder.getAdapterPosition();
                int count = ShoppingCartManger.CartManagerDecrease(orderBasketList.get(productItemPosition).ProductId);
                if (count != 0)
                {
                    holder.txtProductCountBasket.setText(count + "");
                    OrderBasketModel product = orderBasketList.get(holder.getAdapterPosition());
                    product.ProductCount = count;
                    orderBasketList.set(holder.getAdapterPosition(), product);
                    onCartChangedListener.OnCartChanged(orderBasketList);
                }
                else
                {
                    orderBasketList.remove(productItemPosition);
                    onCartChangedListener.OnCartChanged(orderBasketList);
                    notifyDataSetChanged();

                }
            }
        });
        holder.itemView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.scale_anim));
    }

    @Override
    public int getItemCount()
    {
        return orderBasketList.size();
    }


    private void showAddDescriptionDialog(final int adapterPosition)
    {
        android.support.v7.app.AlertDialog.Builder alertDescriptionDialog = new android.support.v7.app.AlertDialog.Builder(context);
        View alertView = View.inflate(context, R.layout.add_description_dialog_layout, null);
        Button btnAddDescriptionDialog = alertView.findViewById(R.id.btnAddDescriptionDialog);
        final EditText edtDescriptionDialog = alertView.findViewById(R.id.edtDescriptionDialog);
        btnAddDescriptionDialog.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //add text to model
                //or update the model
                OrderBasketModel order = orderBasketList.get(adapterPosition);
                order.AdditionalDescription = edtDescriptionDialog.getText().toString();
                orderBasketList.set(adapterPosition, order);
                ShoppingCartManger.UpdateCart(order.ProductId, order.ProductCount, order.AdditionalDescription);
                DescriptionDialog.dismiss();
            }
        });
        alertDescriptionDialog.setView(alertView);
        DescriptionDialog = alertDescriptionDialog.create();
        edtDescriptionDialog.setText(orderBasketList.get(adapterPosition).AdditionalDescription);
        //---------------------------
        DescriptionDialog.show();
    }

    //--------------------------------------------------------------------------------------------------------------
    class OrderBasketViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imgProductImageBasket;
        TextView txtProductTitle;
        TextView txtProductPrice;
        Button btnDeleteProduct;
        Button btnAddDescription;
        ImageButton btnIncreaseProductBasket;
        ImageButton btnDecreaseProductBasket;
        TextView txtProductCountBasket;

        OrderBasketViewHolder(@NonNull View itemView)
        {
            super(itemView);
            //-----
            imgProductImageBasket = itemView.findViewById(R.id.imgProductImageBasket);
            txtProductTitle = itemView.findViewById(R.id.txtProductTitle);
            txtProductPrice = itemView.findViewById(R.id.txtProductPrice);
            btnDeleteProduct = itemView.findViewById(R.id.btnDeleteProduct);
            btnAddDescription = itemView.findViewById(R.id.btnAddDescription);
            btnIncreaseProductBasket = itemView.findViewById(R.id.btnIncreaseProductBasket);
            btnDecreaseProductBasket = itemView.findViewById(R.id.btnDecreaseProductBasket);
            txtProductCountBasket = itemView.findViewById(R.id.txtProductCountBasket);
            //-------------
            VectorDrawablePreLollipopHelper.SetVectors(itemView.getResources(), new VectorView(R.drawable.ic_delete_product, btnDeleteProduct, VectorDrawablePreLollipopHelper.MyDirType.top));
            VectorDrawablePreLollipopHelper.SetVectors(itemView.getResources(), new VectorView(R.drawable.ic_description, btnAddDescription, VectorDrawablePreLollipopHelper.MyDirType.top));
        }
    }
}
