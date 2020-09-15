package customer.dococean.com.patient.utils;

import android.Manifest;
import android.hardware.camera2.params.StreamConfigurationMap;

/**
 * Created by nagendrasrivastava on 09/07/16.
 */
public class DocOceanConstants {

    public static final String RUPPIE_UNICODE = "\u20B9 ";
    public static final String USER_TYPE = "NORMAL";
    public static final int DEFAULT_ZOOM_VALUE = 15;
    public static final String PATIENT_PLACE = "PATIENT_PLACE";
    public static final String EXPERT_PLACE = "EXPERT_PLACE";
    public static final String ALL_PLACE = "ALL";
    public static final String APPOINTMENT_CANCEL_REQUEST = "cancel";
    public static final String EDIT_ADDRESS = "edit_address";


    public class IntentConstants {
        public static final String EXTRAS = "extras";
        public static final String FRAGMENT_NAME = "fragment_name";
        public static final String LOCATION = "location";
        public static final String PARCELABLE_DATA = "parcelable_data";
        public static final String CATEGORY_ID = "category_id";
        public static final String PROFESSION_DATA = "profession_data";
        public static final String SELECTED_SERVICES = "selected_services";
        public static final String WILL_GO_TO_PATIENT_PLACE_OR_NOT = "place_decider";
        public static final String USER_ADDRESS = "user_address";
        public static final String SELECTED_ADDRESS_ID = "address_id";
        public static final String TOTAL_COST = "total_cost";
        public static final String OPTED_SERVICE_LIST = "opted_service_list";
        public static final String SELECTED_ADDRESS = "selected_address";
        public static final String ADDRESS_PIPELINE = "address_pipeline";
        public static final String SCHEDULED_TIME = "scheduled_time";

    }

    public class IntegerConstants {
        public static final int PERIOIDIC_TRACK = 5;
    }

    public class CrashlyticsConstants {
        public static final int SUCCESS = 3;
        public static final int FAILURE = 4;
        public static final int PERMISSION_DENIED = 5;

    }


    public class BookingStatus {
        public static final String CONFIRMED = "confirmed";
        public static final String COMPLETED = "completed";
        public static final String CANCELLED = "cancelled";
        public static final String ASSIGN_FAILED = "assign_failed";
        public static final String ENROUTE = "en_route";
    }

    public interface REQUIRED_PERMISSION {
        String[] PERMISSION = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CALL_PHONE,
        };
    }

    public interface RUNTIME_PERMISSION {
        int PERMISSION = 101;
    }

    public static final String PICK_UP = "pick_up";
    public static final String DROP = "drop";
    public static final String HOME_ADDRESS = "Set Home Address";
    public static final String WORK_ADDRESS = "Set Work Address";

    public static final String OTHER_PATIENT = "others";

    public static class AddressTag {
        public static final String HOME = "Home";
        public static final String WORK = "Work";
    }

}


