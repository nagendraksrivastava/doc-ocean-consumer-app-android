package customer.dococean.com.patient.interfaces;

/**
 * Created by nagendrasrivastava on 30/07/16.
 */
public interface ProfessionChangeInformer<T> {

    void giveMeExperts(T data);

    /**
     *  This will display the experts on map which are willing to go to patient place
     */
    void showPatientPlaceExperts();

    /**
     *  This will diplay experts on map which are not willing to go to patient place and expecting patient will come to
     *  his place only
     */
    void showExpertPlaceExperts();
}
