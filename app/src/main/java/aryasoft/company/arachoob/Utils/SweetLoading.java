package aryasoft.company.arachoob.Utils;

import android.content.Context;

import aryasoft.company.arachoob.R;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class SweetLoading
{

    private SweetAlertDialog SweetDialog = null;

    private SweetLoading()
    {
    }

    public void show()
    {
        if (SweetDialog.isShowing())
        {
            SweetDialog.dismiss();
        }
        SweetDialog.show();
    }

    public boolean isShowing()
    {
        return SweetDialog.isShowing();
    }

    public void hide()
    {
        SweetDialog.dismiss();
    }

    public static class Builder
    {
        private SweetLoading Loading;

        public SweetLoading build(Context context)
        {
            Loading = new SweetLoading();
            Loading.SweetDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            Loading.SweetDialog.setTitleText("لطفا کمی صبر کنید...");
            Loading.SweetDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.colorPrimaryDark));
            Loading.SweetDialog.setCancelable(false);

            return Loading;
        }
    }

}
