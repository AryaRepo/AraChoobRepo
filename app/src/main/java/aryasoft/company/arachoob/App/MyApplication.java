package aryasoft.company.arachoob.App;

import android.app.Application;
import android.content.Context;

import aryasoft.company.arachoob.R;
import aryasoft.company.arachoob.Utils.UserPreference;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MyApplication extends Application {

    private static Context ContextInstance;

    public static Context getContextInstance()
    {
        return ContextInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ContextInstance = this;
        initializeCalligraphy();
        UserPreference.initialize(this);
    }

    private void initializeCalligraphy()
    {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/iran_yekan_mobile_regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }
}
