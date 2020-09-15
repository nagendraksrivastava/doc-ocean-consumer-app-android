package customer.dococean.com.patient.interfaces;

/**
 * Created by nagendrasrivastava on 23/07/16.
 */
public interface FromActivityToFragment<T> {

    void sendData(T data, T anotherData);

    void sendError(T data);
}
