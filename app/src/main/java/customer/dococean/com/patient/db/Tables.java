package customer.dococean.com.patient.db;

import android.net.Uri;

/**
 * Created by nagendrasrivastava on 21/10/15.
 */
public class Tables {

    public static final String AUTHORITY = "customer.dococean.com.patient";
    private static final String _ID = "_id";

    interface Features {
        String TABLE_NAME = "features";
        String FEATURE_NAME = "name";
        String FEATURE_VALUE = "value";
        String FEATURE_TIME = "time";

        /**
         * Table which will be used to store key value pair, this table is replacement for shared preferences,
         */
        Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" +
                TABLE_NAME +
                "?" +
                DocOceanContentProvider.PARAMETER_NOTIFY +
                "=true");

        /**
         * URL which will notify if any changes happened into the database
         */
        Uri CONTENT_URI_NO_NOTIFICATION = Uri.parse("content://" + AUTHORITY +
                "/" +
                TABLE_NAME +
                "?" +
                DocOceanContentProvider
                        .PARAMETER_NOTIFY +
                "=false");
    }

    public static final class NormalUser {
        public static final String TABLE_NAME = "user";
        public static final String USER_NAME = "user_name";
        public static final String USER_EMAIL = "email";
        public static final String CONTACT_NO = "contact_no";
        public static final String AUTH_KEY = "auth";
        public static final String USER_IMAGE_URL = "usr_img_url";


        /**
         * Table which will be used to store key value pair, this table is replacement for shared preferences,
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" +
                TABLE_NAME +
                "?" +
                DocOceanContentProvider.PARAMETER_NOTIFY +
                "=true");

        /**
         * URL which will notify if any changes happened into the database
         */
        public static final Uri CONTENT_URI_NO_NOTIFICATION = Uri.parse("content://" + AUTHORITY +
                "/" +
                TABLE_NAME +
                "?" +
                DocOceanContentProvider
                        .PARAMETER_NOTIFY +
                "=false");
    }


    public static final class AddressHistory {
        public static final String TABLE_NAME = "address_history";
        public static final String LOCALITY = "locality";
        public static final String ADDRESS = "address";
        public static final String LAT = "lat";
        public static final String LON = "lon";
        public static final String ADDRESS_TYPE = "address_type";
        public static final String TIME_STAMP = "timestamp";
        public static final String PLACE_ID = "place_id";

        /**
         * Table which will be used to store key value pair, this table is replacement for shared preferences,
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" +
                TABLE_NAME +
                "?" +
                DocOceanContentProvider.PARAMETER_NOTIFY +
                "=true");

        /**
         * URL which will notify if any changes happened into the database
         */
        public static final Uri CONTENT_URI_NO_NOTIFICATION = Uri.parse("content://" + AUTHORITY +
                "/" +
                TABLE_NAME +
                "?" +
                DocOceanContentProvider
                        .PARAMETER_NOTIFY +
                "=false");
    }


    /**
     * Table which will be used to store key value pair, this table is replacement for shared preferences,
     */
    public static final class Registry {
        public static final String TABLENAME = "registry";
        public static final String KEY = "key";
        public static final String VALUE = "value";

        /**
         * URL which will notify if any changes happened into the database
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" +
                TABLENAME +
                "?" +
                DocOceanContentProvider.PARAMETER_NOTIFY +
                "=true");

        /**
         * URl which can be used to communicate with the database but it will not notify for database changes automatically
         */
        public static final Uri CONTENT_URI_NO_NOTIFICATION = Uri.parse("content://" + AUTHORITY +
                "/" +
                TABLENAME +
                "?" +
                DocOceanContentProvider
                        .PARAMETER_NOTIFY +
                "=false");

    }


    /**
     * Table which will store trip details , it will be cleared once table is completed ,
     */
    public static final class Trip {
        public static final String TABLENAME = "trip";
        public static final String TRIP_STATUS = "status";
        // TRACKING LATITUDE AND LONGITUDE
        public static final String LATITUDE = "lat";
        public static final String LONGITUDE = "lon";

        // LATITUDE AND LONGITUDE FOR PICK UP AND DROP
        public static final String PICK_LATITUDE = "pick_lat";
        public static final String PICK_LONGITUDE = "pick_lon";

        public static final String DROP_LATITUDE = "drop_lat";
        public static final String DROP_LONGITUDE = "drop_lon";

        public static final String DRIVER_NAME = "name";
        public static final String DRIVER_PHONE = "phone";
        public static final String DRIVER_VEHICLE_NAME = "vehicle_name";
        public static final String DRIVER_RATING = "driver_rating";
        public static final String VEHICLE_NO = "vehicle_no";
        public static final String DRIVER_IMAGE_URL = "img_url";
        public static final String PICKUP_ADDRESS = "pick_up";
        public static final String DESTINATION_ADDRESS = "dest_address";
        public static final String TRAKING_LINK = "track_link";
        public static final String ORDER_ID = "order_id";

        /**
         * Customer rated driver once he rated driver the clear the table
         */
        public static final String RATED = "rated";
        public static final String ETA_VALUE = "value";
        public static final String ETA_UNIT = "unit";


        /**
         * URL which will notify if any changes happened into the database
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" +
                TABLENAME +
                "?" +
                DocOceanContentProvider.PARAMETER_NOTIFY +
                "=true");

        /**
         * URl which can be used to communicate with the database but it will not notify for database changes automatically
         */
        public static final Uri CONTENT_URI_NO_NOTIFICATION = Uri.parse("content://" + AUTHORITY +
                "/" +
                TABLENAME +
                "?" +
                DocOceanContentProvider
                        .PARAMETER_NOTIFY +
                "=false");

    }


    /**
     * Table which will be used to store key value pair, this table is replacement for shared preferences,
     */
    public static final class IDS {
        public static final String TABLENAME = "rr_id_table";
        public static final String SERVICE_ID = "service_id";
        public static final String CATEGORY_ID = "category_id";

        /**
         * URL which will notify if any changes happened into the database
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" +
                TABLENAME +
                "?" +
                DocOceanContentProvider.PARAMETER_NOTIFY +
                "=true");

        /**
         * URl which can be used to communicate with the database but it will not notify for database changes automatically
         */
        public static final Uri CONTENT_URI_NO_NOTIFICATION = Uri.parse("content://" + AUTHORITY +
                "/" +
                TABLENAME +
                "?" +
                DocOceanContentProvider
                        .PARAMETER_NOTIFY +
                "=false");

    }


    public static final String CREATE_REGISTRY_TABLE =
            " CREATE TABLE " + Registry.TABLENAME
                    + " ( "
                    + Registry.KEY
                    + " TEXT  PRIMARY KEY , "
                    + Registry.VALUE
                    + " TEXT );";

    public static final String CREATE_NORMAL_USER_TABLE =
            " CREATE TABLE " + NormalUser.TABLE_NAME
                    + " ( "
                    + _ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + NormalUser.USER_NAME
                    + " TEXT, "
                    + NormalUser.USER_EMAIL
                    + " TEXT, "
                    + NormalUser.CONTACT_NO
                    + " INTEGER, "
                    + NormalUser.AUTH_KEY
                    + " TEXT UNIQUE, "
                    + NormalUser.USER_IMAGE_URL
                    + " TEXT);";

    public static final String CREATE_FEATURE_TABLE =
            " CREATE TABLE " + Features.TABLE_NAME
                    + " ( "
                    + _ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + Features.FEATURE_NAME
                    + " TEXT UNIQUE, "
                    + Features.FEATURE_TIME
                    + " INTEGER, "
                    + Features.FEATURE_VALUE
                    + " INTEGER );";


    public static final String CREATE_TRIP_TABLE =
            " CREATE TABLE " + Trip.TABLENAME
                    + " ( "
                    + _ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + Trip.TRIP_STATUS
                    + " TEXT, "

                    + Trip.LATITUDE
                    + " TEXT, "
                    + Trip.LONGITUDE
                    + " TEXT, "

                    + Trip.PICK_LATITUDE
                    + " TEXT, "
                    + Trip.PICK_LONGITUDE
                    + " TEXT, "

                    + Trip.DROP_LATITUDE
                    + " TEXT, "
                    + Trip.DROP_LONGITUDE
                    + " TEXT, "

                    + Trip.DRIVER_NAME
                    + " TEXT, "
                    + Trip.DRIVER_PHONE
                    + " TEXT, "
                    + Trip.VEHICLE_NO
                    + " TEXT, "
                    + Trip.DRIVER_IMAGE_URL
                    + " TEXT, "
                    + Trip.DRIVER_VEHICLE_NAME
                    + " TEXT, "
                    + Trip.DRIVER_RATING
                    + " REAL, "
                    + Trip.TRAKING_LINK
                    + " TEXT, "
                    + Trip.DESTINATION_ADDRESS
                    + " TEXT, "
                    + Trip.PICKUP_ADDRESS
                    + " TEXT, "
                    + Trip.ORDER_ID
                    + " TEXT UNIQUE, "
                    + Trip.RATED
                    + " INTEGER DEFAULT 1, "
                    + Trip.ETA_VALUE
                    + " TEXT, "
                    + Trip.ETA_UNIT
                    + " TEXT );";


    public static final String CREATE_IDS_TABLE =
            " CREATE TABLE " + IDS.TABLENAME
                    + " ( "
                    + _ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + IDS.SERVICE_ID
                    + " TEXT UNIQUE, "
                    + IDS.CATEGORY_ID
                    + " TEXT UNIQUE);";


    public static final String CREATE_ADDRESS_TABLE =
            " CREATE TABLE " + AddressHistory.TABLE_NAME
                    + " ( "
                    + _ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + AddressHistory.LOCALITY
                    + " TEXT, "
                    + AddressHistory.PLACE_ID
                    + " TEXT UNIQUE, "
                    + AddressHistory.ADDRESS
                    + " TEXT, "
                    + AddressHistory.LAT
                    + " REAL, "
                    + AddressHistory.LON
                    + " REAL, "
                    + AddressHistory.TIME_STAMP
                    + " INTEGER, "
                    + AddressHistory.ADDRESS_TYPE
                    + " TEXT );";


}
