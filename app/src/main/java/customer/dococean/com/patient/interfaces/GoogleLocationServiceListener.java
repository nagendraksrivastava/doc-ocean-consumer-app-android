package customer.dococean.com.patient.interfaces;

import android.location.Location;

/**
 * Created by nagendrasrivastava on 11/07/16.
 */
public interface GoogleLocationServiceListener {
    void onPlayServiceConnected();
    void onLocationChanged(Location location);

}
