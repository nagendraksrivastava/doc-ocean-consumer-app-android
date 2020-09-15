package customer.dococean.com.patient;

import android.app.Application;
import android.content.Context;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Created by nagendrasrivastava on 24/06/16.
 */
public final class DococeanApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public Context getBaseContext() {
        return super.getBaseContext();
    }
}
