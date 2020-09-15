package customer.dococean.com.patient.utils;

import android.content.Context;
import android.content.Intent;

import customer.dococean.com.patient.services.DocOceanService;

/**
 * Created by nagendrasrivastava on 25/08/16.
 */
public class ServiceManager {

    public static void startService(Context context) {
        Intent intent = new Intent(context, DocOceanService.class);
        context.startService(intent);
    }
}
