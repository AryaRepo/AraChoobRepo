package aryasoft.company.arachoob.Adapters;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.widget.Toast;
import java.util.ArrayList;
import aryasoft.company.arachoob.Models.OrderBasketModel;
import aryasoft.company.arachoob.R;


public class OrderBasketAdapter extends RecyclerView.Adapter<OrderBasketAdapter.OrderBasketViewHolder>
{
    private Dialog DescriptionDialog;
    private Dialog chooseImageDialog;
    private Context context;
    private ArrayList<OrderBasketModel> orderBasketList;
    private BounceInterpolator interpolator;
    public OrderBasketAdapter(Context context)
    {
        this.context = context;
        this.orderBasketList = new ArrayList<>();
        interpolator = new BounceInterpolator();
    }

    @NonNull
    @Override
    public OrderBasketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new OrderBasketViewHolder(LayoutInflater.from(context).inflate(R.layout.order_basket_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderBasketViewHolder holder, int position)
    {

      /*  if (orderBasketList.size() == 0)
        {
            return;
        }
        Glide.with(context).load(context.getString(R.string.BaseUrl) + context.getString(R.string.ProductImageFolder) + orderBasketList.get(position).ProductImageName).into(holder.imgProductImageBasket);
        holder.txtProductTitle.setText(orderBasketList.get(position).ProductTitle);*/
        holder.btnAttachImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showChoosePictureDialog();
                //add image to model and update list
            }
        });
        holder.btnAddDescription.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //open dialog to add description
                showAddDescriptionDialog();

            }
        });
        holder.btnIncreaseProductBasket.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.scale_anim);
                myAnim.setInterpolator(interpolator);
                v.startAnimation(myAnim);
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

            }
        });
        holder.itemView.setAnimation( AnimationUtils.loadAnimation(context,R.anim.scale_anim));
    }

    @Override
    public int getItemCount()
    {
        //return  orderBasketList.size();
        return 10;
    }

    private void showChoosePictureDialog()
    {
        String[] listItem = new String[]{"انتخاب از گالری", "بادوربین"};
        android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(context,R.style.AlertDialogCustom);
        alert.setItems(listItem, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                switch (which)
                {
                    case 0:
                        openGallery();
                        break;
                    case 1:
                        if (checkPermission(Manifest.permission.CAMERA))
                        {
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            ((Activity) context).startActivityForResult(cameraIntent, 1);
                        }
                        else
                        {
                            requestPermission(Manifest.permission.CAMERA, 1);
                        }

                        break;
                    default:
                        //do noting :)
                        break;
                }
            }
        });
        chooseImageDialog = alert.create();
        chooseImageDialog.show();
    }

    private void showReplaceImageDialog()
    {
        String[] listItem = new String[]{"انتخاب عکس جدید", "لغو"};
        final android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(context,R.style.AlertDialogCustom);
        alert.setTitle("کاربر گرامی شما یک عکس را قبلا ضمیمه کردید آیا قصد جایگزین کردن آن را دارید؟");
        alert.setItems(listItem, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                switch (which)
                {
                    case 0:
                        dialog.dismiss();
                        showChoosePictureDialog();
                        break;
                    case 1:
                        dialog.dismiss();
                        break;
                    default:

                        break;
                }
            }
        });
        alert.show();
    }

    private void openGallery()
    {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        ((Activity) context).startActivityForResult(Intent.createChooser(galleryIntent, "انتخاب عکس"), 0);
    }

    private boolean checkPermission(String permissionName)
    {
        return ContextCompat.checkSelfPermission(context, permissionName) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(String permissionName, int requestCode)
    {
        ActivityCompat.requestPermissions((Activity) context, new String[]{permissionName}, requestCode);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 0)
        {
            //gallery image
            Toast.makeText(context, "image chosen", Toast.LENGTH_SHORT).show();

        }
        else if (requestCode == 1)
        {
            //camera
            Toast.makeText(context, "camera", Toast.LENGTH_SHORT).show();
        }
        chooseImageDialog.dismiss();
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == 1)
        {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            ((Activity) context).startActivityForResult(cameraIntent, 1);
        }
    }


    private void showAddDescriptionDialog()
    {
        android.support.v7.app.AlertDialog.Builder alertDescriptionDialog = new android.support.v7.app.AlertDialog.Builder(context);
        View alertView = View.inflate(context, R.layout.add_description_dialog_layout, null);
        Button btnAddDescriptionDialog = alertView.findViewById(R.id.btnAddDescriptionDialog);
        EditText edtDescriptionDialog = alertView.findViewById(R.id.edtDescriptionDialog);
        btnAddDescriptionDialog.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //add text to model
                //or update the model

                DescriptionDialog.dismiss();
            }
        });
        alertDescriptionDialog.setView(alertView);
        DescriptionDialog = alertDescriptionDialog.create();
        DescriptionDialog.show();
    }

    class OrderBasketViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imgProductImageBasket;
        TextView txtProductTitle;
        TextView txtProductPrice;
        Button btnAttachImage;
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
            btnAttachImage = itemView.findViewById(R.id.btnAttachImage);
            btnAddDescription = itemView.findViewById(R.id.btnAddDescription);
            btnIncreaseProductBasket = itemView.findViewById(R.id.btnIncreaseProductBasket);
            btnDecreaseProductBasket = itemView.findViewById(R.id.btnDecreaseProductBasket);
            txtProductCountBasket = itemView.findViewById(R.id.txtProductCountBasket);
        }
    }
}
