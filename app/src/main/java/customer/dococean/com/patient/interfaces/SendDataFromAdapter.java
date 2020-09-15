package customer.dococean.com.patient.interfaces;

/**
 * Created by nagendrasrivastava on 28/09/16.
 */

public interface SendDataFromAdapter<T> {
    void onDataReceived(T data);
}
