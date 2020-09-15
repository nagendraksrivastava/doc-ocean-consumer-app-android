package customer.dococean.com.patient.interfaces;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by nagendrasrivastava on 22/10/16.
 */

public interface CameraMapChange {

    /**
     *  Method will position camera when user changes its address
     * @param latLng
     */
    void changeCameraWhenAddressChanges(LatLng latLng);
}
