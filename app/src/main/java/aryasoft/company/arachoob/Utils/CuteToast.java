package aryasoft.company.arachoob.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import aryasoft.company.arachoob.R;

public class CuteToast {

    private String ToastText = "";
    private int ToastDuration = Toast.LENGTH_SHORT;
    private Activity ActivityInstance;
    private View ContentView;

    private CuteToast() {
    }

    public static class Builder
    {
        private CuteToast CuteToastInstance;

        public Builder(Activity activity) {
            CuteToastInstance = new CuteToast();
            CuteToastInstance.ActivityInstance = activity;
        }

        public Builder setText(String text)
        {
            CuteToastInstance.ToastText = text;
            return  this;
        }

        public Builder setDuration(int duration)
        {
            CuteToastInstance.ToastDuration = duration;
            return this;
        }

        public Builder setContentView(View contentView)
        {
            return this;
        }

        public CuteToast show()
        {
            Toast toast = new Toast(CuteToastInstance.ActivityInstance);
            toast.setDuration(CuteToastInstance.ToastDuration);
            if (CuteToastInstance.ContentView == null)
            {
                LayoutInflater inflater = CuteToastInstance.ActivityInstance.getLayoutInflater();
                CuteToastInstance.ContentView = inflater.inflate(R.layout.custom_toast_layout, null);
                TextView txtMessage = CuteToastInstance.ContentView.findViewById(R.id.txt_toast_message);
                txtMessage.setText(CuteToastInstance.ToastText);
                toast.setView(CuteToastInstance.ContentView);
            }
            else if (CuteToastInstance.ContentView != null)
            {
                toast.setView(CuteToastInstance.ContentView);
            }
            toast.show();
            return CuteToastInstance;
        }

    }
}
