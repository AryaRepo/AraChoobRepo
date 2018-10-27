package aryasoft.company.arachoob.App;

import android.app.Application;

import aryasoft.company.arachoob.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initializeCalligraphy();
    }

    private void initializeCalligraphy()
    {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/iran_yekan_mobile_regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }
}
