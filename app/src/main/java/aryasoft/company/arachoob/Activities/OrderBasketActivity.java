package aryasoft.company.arachoob.Activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import aryasoft.company.arachoob.Adapters.OrderBasketAdapter;
import aryasoft.company.arachoob.ApiConnection.ApiInterfaceListeners.OnGetOrderBasketInfoListener;
import aryasoft.company.arachoob.ApiConnection.ApiInterfaceListeners.OnSubmitOrderListener;
import aryasoft.company.arachoob.ApiConnection.ApiModels.ProductDataModel;
import aryasoft.company.arachoob.ApiConnection.ApiModels.SubmitOrderApiModel;
import aryasoft.company.arachoob.ApiConnection.ApiModules.OrderModule;
import aryasoft.company.arachoob.Models.OrderBasketModel;
import aryasoft.company.arachoob.Models.SavedShoppingCartModel;
import aryasoft.company.arachoob.Models.SubmitOrderDetailModel;
import aryasoft.company.arachoob.R;
import aryasoft.company.arachoob.Utils.CuteToast;
import aryasoft.company.arachoob.Utils.Listeners.OnCartChangedListener;
import aryasoft.company.arachoob.Utils.Listeners.OnDataReceiveStateListener;
import aryasoft.company.arachoob.Utils.ShoppingCartManger;
import aryasoft.company.arachoob.Utils.SweetLoading;
import aryasoft.company.arachoob.Utils.UserPreference;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class OrderBasketActivity extends AppCompatActivity implements OnDataReceiveStateListener
{
    private SubmitOrderApiModel submitOrderModel;
    private OrderModule orderModule;
    private int UserId;
    //-----------
    private RelativeLayout relEmptyCart;
    //------bottomSheet
    private ImageButton btnCloseInvoicePanel;
    private TextView txtProductCountInvoicePanel;
    private TextView txtProductPriceInvoicePanel;
    private RecyclerView recyclerOrderBasket;
    private OrderBasketAdapter recyclerOrderBasketAdapter;
    private BottomSheetBehavior bottomSheetBehavior;
    private Button btnOpenInvoice;
    private SweetLoading sweetLoading;
    private Button btnSubmitOrder;
    private AlertDialog chooseImageDialog;
    private ImageView imgInvoiceImage;
    private AlertDialog alertOrderInvoiceDialog;
    private SweetLoading Loading;
    //===============================================================================================
    private ArrayList<OrderBasketModel> orderBasketList;


    @Override
    public void OnDataReceiveState(Throwable ex)
    {
        Loading.hide();
        new CuteToast.Builder(this).setText(getString(R.string.noInternetText)).setDuration(Toast.LENGTH_LONG).show();
    }

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 1)
        {
            //gallery
            if (resultCode == RESULT_OK)
            {
                submitOrderModel.OrderImageName = Base64.encodeToString(ConvertImageToByteArray(data, this), Base64.DEFAULT);
                imgInvoiceImage.setVisibility(View.VISIBLE);
                Glide.with(this).load(data.getData()).into(imgInvoiceImage);
            }
            else
            {
                submitOrderModel.OrderImageName = "";
            }
        }
        else if (requestCode == 2)
        {
            //camera
            if (resultCode == RESULT_OK)
            {
                submitOrderModel.OrderImageName = Base64.encodeToString(ConvertImageToByteArray(data, this), android.util.Base64.DEFAULT);
                imgInvoiceImage.setVisibility(View.VISIBLE);
                Glide.with(this).load(data.getData()).into(imgInvoiceImage);
            }
            else
            {

                submitOrderModel.OrderImageName = "";
            }
        }

        chooseImageDialog.dismiss();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == 1)
        {
            openGallery();

        }
        else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == 2)
        {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, 2);
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        //----------------
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
        {
            closeInvoicePanel();
            btnOpenInvoice.setVisibility(View.VISIBLE);
        }
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED)
        {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_basket);
        initViews();
        initEvents();
    }

    private void initViews()
    {
        orderBasketList = new ArrayList<>();
        UserId = UserPreference.getUserId();
        Loading = new SweetLoading.Builder().build(this);
        orderModule = new OrderModule();
        orderModule.setOnDataReceiveStateListener(this);
        btnOpenInvoice = findViewById(R.id.btnOpenInvoice);
        relEmptyCart = findViewById(R.id.relEmptyCart);
        btnSubmitOrder = findViewById(R.id.btnSubmitOrder);
        btnCloseInvoicePanel = findViewById(R.id.btnCloseInvoicePanel);
        txtProductCountInvoicePanel = findViewById(R.id.txtProductCountInvoicePanel);
        txtProductPriceInvoicePanel = findViewById(R.id.txtProductPriceInvoicePanel);
        recyclerOrderBasket = findViewById(R.id.recyclerOrderBasket);
        final LinearLayout bottomSheet = findViewById(R.id.bottom_sheet2);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        sweetLoading = new SweetLoading.Builder().build(this);
        //==============================
    }

    private void getOrderBasketInfo()
    {
        sweetLoading.show();
        ArrayList<Integer> productIds = new ArrayList<>(ShoppingCartManger.GetCartProductsId());
        final ArrayList<SavedShoppingCartModel> savedCart = ShoppingCartManger.GetShoppingCart();
        orderModule.setOnGetOrderBasketInfoListener(new OnGetOrderBasketInfoListener()
        {
            @Override
            public void OnGetOrderBasketInfo(ArrayList<ProductDataModel> productBasketList)
            {
                if (productBasketList.size() == 0)
                {
                    sweetLoading.hide();
                    relEmptyCart.setVisibility(View.VISIBLE);
                    return;
                }
                recyclerOrderBasket.setVisibility(View.VISIBLE);
                ArrayList<OrderBasketModel> orderBasketList = new ArrayList<>();
                for (int i = 0; i < savedCart.size(); i++)
                {
                    for (int j = 0; j < productBasketList.size(); j++)
                    {
                        if (savedCart.get(i).ProductId == productBasketList.get(j).ProductId)
                        {
                            OrderBasketModel orderBasketModel = new OrderBasketModel();
                            orderBasketModel.ProductId = productBasketList.get(j).ProductId;
                            orderBasketModel.ProductTitle = productBasketList.get(j).ProductTitle;
                            orderBasketModel.ProductImageName = productBasketList.get(j).ImageName;
                            orderBasketModel.ProductCount = savedCart.get(i).ProductCount;
                            orderBasketModel.AdditionalDescription = savedCart.get(i).AdditionalDescription;
                            orderBasketModel.DiscountPercent = productBasketList.get(j).DiscountPercent;
                            orderBasketModel.ProductFinalPrice = calculateDiscount(productBasketList.get(j).CoverPrice, productBasketList.get(j).DiscountPercent);
                            orderBasketList.add(orderBasketModel);
                        }
                    }
                }
                //----------------------------------------------------------------
                recyclerOrderBasketAdapter = new OrderBasketAdapter(OrderBasketActivity.this, orderBasketList);
                recyclerOrderBasket.setLayoutManager(new LinearLayoutManager(OrderBasketActivity.this, LinearLayoutManager.VERTICAL, false));
                recyclerOrderBasket.setAdapter(recyclerOrderBasketAdapter);
                calculateBill(recyclerOrderBasketAdapter.getOrderBasketList());
                recyclerOrderBasketAdapter.setOnCartChangedListener(new OnCartChangedListener()
                {
                    @Override
                    public void OnCartChanged(ArrayList<OrderBasketModel> orderBasketList)
                    {
                        OrderBasketActivity.this.orderBasketList.clear();
                        OrderBasketActivity.this.orderBasketList.addAll(orderBasketList);
                        if (orderBasketList.size() == 0)
                        {
                            closeInvoicePanel();
                            recyclerOrderBasket.setVisibility(View.GONE);
                            relEmptyCart.setVisibility(View.VISIBLE);
                            btnOpenInvoice.setVisibility(View.GONE);
                            //---------------------
                        }
                        else
                        {
                            calculateBill(orderBasketList);
                        }
                    }
                });

                sweetLoading.hide();
            }
        });
        orderModule.getOrderBasketInfo(productIds);

    }

    private void initEvents()
    {
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback()
        {
            @Override
            public void onStateChanged(@NonNull View view, int i)
            {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED)
                {
                    btnOpenInvoice.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v)
            {

            }
        });
        btnSubmitOrder.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                submitOrderModel = new SubmitOrderApiModel();
                showOrderInvoiceDialog();
            }
        });
        btnOpenInvoice.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
                {
                    return;
                }
                openInvoicePanel();
            }
        });
        btnCloseInvoicePanel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                closeInvoicePanel();
                btnOpenInvoice.setVisibility(View.VISIBLE);
            }
        });

        if (ShoppingCartManger.GetShoppingCart().size() != 0)
        {
            getOrderBasketInfo();
            return;
        }
        btnOpenInvoice.setVisibility(View.GONE);
        recyclerOrderBasket.setVisibility(View.GONE);
        relEmptyCart.setVisibility(View.VISIBLE);

    }

    private void openInvoicePanel()
    {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        btnOpenInvoice.setVisibility(View.GONE);
    }

    private void closeInvoicePanel()
    {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    private int calculateDiscount(int salesPrice, int discountPercent)
    {
        int finalPrice;
        finalPrice = (salesPrice * discountPercent) / 100;
        return salesPrice - finalPrice;
    }

    private void calculateBill(ArrayList<OrderBasketModel> orderBasketList)
    {
        txtProductCountInvoicePanel.setText(orderBasketList.size() + " " + "قلم کالا");
        txtProductPriceInvoicePanel.setText("هزینه ی تقریبی سفارش شما : " + calculateInvoice(orderBasketList) + " تومان ");
    }

    private int calculateInvoice(ArrayList<OrderBasketModel> orderBasketList)
    {
        int price = 0;
        for (int i = 0; i < orderBasketList.size(); i++)
        {
            price = orderBasketList.get(i).ProductFinalPrice * orderBasketList.get(i).ProductCount;
        }
        return price;
    }

    private void showOrderInvoiceDialog()
    {
        android.support.v7.app.AlertDialog.Builder alertOrderInvoice = new android.support.v7.app.AlertDialog.Builder(this);
        View alertView = View.inflate(this, R.layout.order_invoice_dialog, null);
        Button btnAttachImageToOrder = alertView.findViewById(R.id.btnAttachImageToOrder);
        Button btnFinalizeOrder = alertView.findViewById(R.id.btnFinalizeOrder);
        imgInvoiceImage = alertView.findViewById(R.id.imgInvoiceImage);
        btnAttachImageToOrder.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showChoosePictureDialog();
            }
        });
        btnFinalizeOrder.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (UserId == -5)
                {
                    new CuteToast.Builder(OrderBasketActivity.this).setText("کاربر گرامی لطفا از منوی ثبت نام / ورود در صفحه ی اصلی وارد حساب کاربری شوید.").setDuration(Toast.LENGTH_LONG).show();
                    return;
                }
                submitOrderModel.UserId = UserId;
                submitOrderModel.TotalPrice = calculateInvoice(orderBasketList);
                submitOrderModel.OrderDetailList = new ArrayList<>();
                for (int i = 0; i < orderBasketList.size(); i++)
                {
                    SubmitOrderDetailModel submitOrderDetailModel = new SubmitOrderDetailModel();
                    submitOrderDetailModel.ProductId = orderBasketList.get(i).ProductId;
                    submitOrderDetailModel.ProductCount = orderBasketList.get(i).ProductCount;
                    submitOrderDetailModel.DiscountPercent = orderBasketList.get(i).DiscountPercent;
                    submitOrderDetailModel.Description = orderBasketList.get(i).AdditionalDescription;
                    submitOrderDetailModel.ProductPrice = orderBasketList.get(i).ProductFinalPrice;
                    //----------
                    submitOrderModel.OrderDetailList.add(submitOrderDetailModel);
                }
                Loading.show();
                //call api
                orderModule.setOnSubmitOrderListener(new OnSubmitOrderListener()
                {
                    @Override
                    public void OnSubmitOrder(boolean submitOrderState)
                    {
                        alertOrderInvoiceDialog.dismiss();
                        Loading.hide();
                        if (submitOrderState)
                        {
                            new CuteToast.Builder(OrderBasketActivity.this).setText(getString(R.string.success_order_message)).setDuration(Toast.LENGTH_LONG).show();
                            recyclerOrderBasketAdapter.clearShoppingCart();
                        }
                        else
                        {
                            new CuteToast.Builder(OrderBasketActivity.this).setText(getString(R.string.failed_order_message)).setDuration(Toast.LENGTH_LONG).show();
                        }
                    }
                });
                orderModule.SubmitOrder(submitOrderModel);

            }
        });
        alertOrderInvoice.setView(alertView);
        alertOrderInvoiceDialog = alertOrderInvoice.create();
        alertOrderInvoiceDialog.show();
    }

    private void showChoosePictureDialog()
    {
        String[] listItem = new String[]{"انتخاب از گالری", "بادوربین"};
        android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(this, R.style.AlertDialogCustom);
        alert.setItems(listItem, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                switch (which)
                {
                    case 0:
                        if (checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE))
                        {
                            openGallery();
                        }
                        else
                        {
                            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 1);
                        }

                        break;
                    case 1:
                        if (checkPermission(Manifest.permission.CAMERA))
                        {
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, 2);
                        }
                        else
                        {
                            requestPermission(Manifest.permission.CAMERA, 2);
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

    private void openGallery()
    {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(galleryIntent, "انتخاب عکس"), 1);
    }

    private boolean checkPermission(String permissionName)
    {
        return ContextCompat.checkSelfPermission(this, permissionName) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(String permissionName, int requestCode)
    {
        ActivityCompat.requestPermissions(this, new String[]{permissionName}, requestCode);
    }

    public static byte[] ConvertImageToByteArray(Intent YourData, Context context)
    {
        final long KiB = 1024;
        String Uri_Path = YourData.getDataString();
        android.database.Cursor cursor = context.getContentResolver().query(android.net.Uri.parse(Uri_Path), null, null, null, null);
        int idx = -1;
        if (cursor != null)
        {
            cursor.moveToFirst();
            idx = cursor.getColumnIndex(android.provider.MediaStore.Images.ImageColumns.DATA);
            String path = cursor.getString(idx);
            File f = new File(path);
            Bitmap bm = BitmapFactory.decodeFile(path);
            long len = (f.length() / KiB);
            cursor.close();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            if (len < 100)
            {
                bm.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            }
            else if (len > 100 && len < 500)
            {
                bm.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
            }
            else if (len > 500)
            {
                bm.compress(Bitmap.CompressFormat.JPEG, 15, byteArrayOutputStream);
            }
            return byteArrayOutputStream.toByteArray();
        }
        return new byte[1];
    }


}
