package customer.dococean.com.patient.interfaces;

/**
 * Created by nagendrasrivastava on 20/08/16.
 */
public interface BookingConfirm<T> {

    /**
     *  This method will be called once booking confirmation is successfull from server
     */
    void onBookingConfirmResponse(Object data);
}
