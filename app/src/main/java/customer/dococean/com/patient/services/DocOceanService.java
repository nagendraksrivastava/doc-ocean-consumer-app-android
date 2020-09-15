package customer.dococean.com.patient.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DocOceanService extends Service {
    public DocOceanService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
