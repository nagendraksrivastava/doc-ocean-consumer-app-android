package customer.dococean.com.patient.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

import customer.dococean.com.patient.utils.DocOceanLogger;

/**
 * Created by nagendrasrivastava on 30/06/16.
 */
public class DBHelper {

    private static final String TAG = DBHelper.class.getSimpleName();
    private static final String[] PROJECTION = new String[]{Tables.NormalUser.AUTH_KEY};


    /**
     * It will insert data into Account table of DB
     */
    public static void saveToNormalUserTable(Context context, String userName, String userEmail, String contactNumber, String authKey) {
        ContentValues cv = new ContentValues();
        cv.put(Tables.NormalUser.USER_NAME, userName);
        cv.put(Tables.NormalUser.USER_EMAIL, userEmail);
        cv.put(Tables.NormalUser.CONTACT_NO, contactNumber);
        cv.put(Tables.NormalUser.AUTH_KEY, authKey);
        context.getContentResolver().insert(Tables.NormalUser.CONTENT_URI, cv);
        DocOceanLogger.d(TAG, " data inserted into Normal User table ");
    }


    /**
     * It will insert data into Account table of DB
     */
    public static void insertToAddress(Context context,
                                       String locality,
                                       String address,
                                       String placeId,
                                       double lat,
                                       double lon,
                                       String addressType,
                                       long timeStamp) {
        ContentValues cv = new ContentValues();
        cv.put(Tables.AddressHistory.LOCALITY, locality);
        cv.put(Tables.AddressHistory.ADDRESS, address);
        cv.put(Tables.AddressHistory.PLACE_ID, placeId);
        cv.put(Tables.AddressHistory.LAT, lat);
        cv.put(Tables.AddressHistory.LON, lon);
        cv.put(Tables.AddressHistory.ADDRESS_TYPE, addressType);
        cv.put(Tables.AddressHistory.TIME_STAMP, timeStamp);
        context.getContentResolver().insert(Tables.AddressHistory.CONTENT_URI, cv);
    }


    public static void saveDataToTrip(Context context,
                                      String status,
                                      String lat,
                                      String lon,
                                      String driverName,
                                      String driverPhone,
                                      String vehicleNo,
                                      String driverVehicleName,
                                      Double driverRating,
                                      String trackingLink,
                                      String driverImageUrl,
                                      String etaValue,
                                      String etaUnit,
                                      String orderId,
                                      boolean rated,
                                      String pickLat,
                                      String pickLon,
                                      String dropLat,
                                      String dropLon,
                                      String pickupAddress,
                                      String dropAddress) {

        ContentValues cv = new ContentValues();
        cv.put(Tables.Trip.TRIP_STATUS, status);
        cv.put(Tables.Trip.LATITUDE, lat);
        cv.put(Tables.Trip.LONGITUDE, lon);
        cv.put(Tables.Trip.DRIVER_NAME, driverName);
        cv.put(Tables.Trip.DRIVER_PHONE, driverPhone);
        cv.put(Tables.Trip.VEHICLE_NO, vehicleNo);
        cv.put(Tables.Trip.DRIVER_VEHICLE_NAME, driverVehicleName);
        cv.put(Tables.Trip.DRIVER_RATING, driverRating);
        cv.put(Tables.Trip.TRAKING_LINK, trackingLink);
        cv.put(Tables.Trip.DRIVER_IMAGE_URL, driverImageUrl);
        cv.put(Tables.Trip.ETA_VALUE, etaValue);
        cv.put(Tables.Trip.ETA_UNIT, etaUnit);
        cv.put(Tables.Trip.ORDER_ID, orderId);
        cv.put(Tables.Trip.RATED, rated);
        cv.put(Tables.Trip.PICK_LATITUDE, pickLat);
        cv.put(Tables.Trip.PICK_LONGITUDE, pickLon);
        cv.put(Tables.Trip.DROP_LATITUDE, dropLat);
        cv.put(Tables.Trip.DROP_LONGITUDE, dropLon);
        cv.put(Tables.Trip.PICKUP_ADDRESS, pickupAddress);
        cv.put(Tables.Trip.DESTINATION_ADDRESS, dropAddress);
        context.getContentResolver().insert(Tables.Trip.CONTENT_URI, cv);
    }

    public static void saveToFeature(Context context, String featureName, boolean featureValue) {
        ContentValues cv = new ContentValues();
        cv.put(Tables.Features.FEATURE_NAME, featureName);
        cv.put(Tables.Features.FEATURE_VALUE, featureValue);
        cv.put(Tables.Features.FEATURE_TIME, new Date().getTime());
        context.getContentResolver().insert(Tables.Features.CONTENT_URI, cv);
    }


    public static void saveToIDTable(Context context, String serviceId, String categoryId) {
        ContentValues cv = new ContentValues();
        cv.put(Tables.IDS.SERVICE_ID, serviceId);
        cv.put(Tables.IDS.CATEGORY_ID, categoryId);
        context.getContentResolver().insert(Tables.IDS.CONTENT_URI, cv);
    }


    public static void saveToRegistry(Context context, String key, boolean value) {
        ContentValues cv = new ContentValues();
        cv.put(Tables.Registry.KEY, key);
        cv.put(Tables.Registry.VALUE, value);
        context.getContentResolver().insert(Tables.Registry.CONTENT_URI, cv);
    }


    public static void clearDb(Context context) {
        context.getContentResolver().delete(Tables.NormalUser.CONTENT_URI, null, null);
        context.getContentResolver().delete(Tables.Features.CONTENT_URI, null, null);
        //context.getContentResolver().delete(Tables.Registry.CONTENT_URI, null, null);
    }

    public static void clearCompleteDB(Context context) {
        context.getContentResolver().delete(Tables.NormalUser.CONTENT_URI, null, null);
        context.getContentResolver().delete(Tables.Registry.CONTENT_URI, null, null);

    }

    public static void clearTripTable(Context context) {
        context.getContentResolver().delete(Tables.Trip.CONTENT_URI, null, null);
    }

    public static String getUserAuthKey(Context context) {
        String authKey = null;
        try {
            Cursor cursor = context.getContentResolver().query(Tables.NormalUser.CONTENT_URI, PROJECTION, null, null, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    if (cursor.isFirst()) {
                        authKey = cursor.getString(0);
                        cursor.close();
                    }
                }
            }
            return authKey;
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
            return authKey;
        } catch (NullPointerException nullEx) {
            nullEx.printStackTrace();
            return authKey;
        }
    }


    public static String getUserName(Context context) {
        String userName = null;
        String[] PROJECTION = new String[]{Tables.NormalUser.USER_NAME};
        try {
            Cursor cursor = context.getContentResolver().query(Tables.NormalUser.CONTENT_URI, PROJECTION, null, null, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    if (cursor.isFirst()) {
                        userName = cursor.getString(0);
                        cursor.close();
                    }
                }
            }
            return userName;
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
            return userName;
        } catch (NullPointerException nullEx) {
            nullEx.printStackTrace();
            return userName;
        }
    }


    public static String getUserEmail(Context context) {
        String userEmail = null;
        String[] PROJECTION = new String[]{Tables.NormalUser.USER_EMAIL};
        try {
            Cursor cursor = context.getContentResolver().query(Tables.NormalUser.CONTENT_URI, PROJECTION, null, null, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    if (cursor.isFirst()) {
                        userEmail = cursor.getString(0);
                        cursor.close();
                    }
                }
            }
            return userEmail;
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
            return userEmail;
        } catch (NullPointerException nullEx) {
            nullEx.printStackTrace();
            return userEmail;
        }
    }

    public static String getUserImageUrl(Context context) {
        String userImageUrl = null;
        String[] PROJECTION = new String[]{Tables.NormalUser.USER_IMAGE_URL};
        try {
            Cursor cursor = context.getContentResolver().query(Tables.NormalUser.CONTENT_URI, PROJECTION, null, null, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    if (cursor.isFirst()) {
                        userImageUrl = cursor.getString(0);
                        cursor.close();
                    }
                }
            }
            return userImageUrl;
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
            return userImageUrl;
        } catch (NullPointerException nullEx) {
            nullEx.printStackTrace();
            return userImageUrl;
        }
    }


    public static int getTripRating(Context context) {
        String[] PROJECTION = new String[]{Tables.Trip.RATED};
        int rated = 1;
        try {
            Cursor cursor = context.getContentResolver().query(Tables.Trip.CONTENT_URI, PROJECTION, null, null, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    if (cursor.isFirst()) {
                        rated = cursor.getInt(0);
                        cursor.close();
                    }

                }
            }
            return rated;
        } catch (IndexOutOfBoundsException ioEx) {
            ioEx.printStackTrace();
            return rated;
        }
    }


    public static String getDriverImageUrl(Context context) {
        String[] PROJECTION = new String[]{Tables.Trip.DRIVER_IMAGE_URL};
        String driverImageUrl = "";
        try {
            Cursor cursor = context.getContentResolver().query(Tables.Trip.CONTENT_URI, PROJECTION, null, null, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    if (cursor.isFirst()) {
                        driverImageUrl = cursor.getString(0);
                        cursor.close();
                    }

                }
            }
            return driverImageUrl;
        } catch (IndexOutOfBoundsException ioEx) {
            ioEx.printStackTrace();
            return driverImageUrl;
        }
    }


    public static float getDriverRating(Context context) {
        String[] PROJECTION = new String[]{Tables.Trip.DRIVER_RATING};
        float rating = 5;
        try {
            Cursor cursor = context.getContentResolver().query(Tables.Trip.CONTENT_URI, PROJECTION, null, null, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    if (cursor.isFirst()) {
                        rating = cursor.getInt(0);
                        cursor.close();
                    }

                }
            }
            return rating;
        } catch (IndexOutOfBoundsException ioEx) {
            ioEx.printStackTrace();
            return rating;
        }
    }


    public static String getDriverName(Context context) {
        String[] PROJECTION = new String[]{Tables.Trip.DRIVER_NAME};
        String driverName = "";
        try {
            Cursor cursor = context.getContentResolver().query(Tables.Trip.CONTENT_URI, PROJECTION, null, null, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    if (cursor.isFirst()) {
                        driverName = cursor.getString(0);
                        cursor.close();
                    }

                }
            }
            return driverName;
        } catch (IndexOutOfBoundsException ioEx) {
            ioEx.printStackTrace();
            return driverName;
        }
    }


    public static String getDriverVehicleNumber(Context context) {
        String[] PROJECTION = new String[]{Tables.Trip.VEHICLE_NO};
        String driverVehicleNo = "";
        try {
            Cursor cursor = context.getContentResolver().query(Tables.Trip.CONTENT_URI, PROJECTION, null, null, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    if (cursor.isFirst()) {
                        driverVehicleNo = cursor.getString(0);
                        cursor.close();
                    }

                }
            }
            return driverVehicleNo;
        } catch (IndexOutOfBoundsException ioEx) {
            ioEx.printStackTrace();
            return driverVehicleNo;
        }
    }


    public static String getDriverVehicleName(Context context) {
        String[] PROJECTION = new String[]{Tables.Trip.DRIVER_VEHICLE_NAME};
        String driverVehicleName = "";
        try {
            Cursor cursor = context.getContentResolver().query(Tables.Trip.CONTENT_URI, PROJECTION, null, null, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    if (cursor.isFirst()) {
                        driverVehicleName = cursor.getString(0);
                        cursor.close();
                    }

                }
            }
            return driverVehicleName;
        } catch (IndexOutOfBoundsException ioEx) {
            ioEx.printStackTrace();
            return driverVehicleName;
        }
    }


    public static String getServiceId(Context context) {
        String[] PROJECTION = new String[]{Tables.IDS.SERVICE_ID};
        String serviceId = null;
        try {
            Cursor cursor = context.getContentResolver().query(Tables.IDS.CONTENT_URI, PROJECTION, null, null, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    if (cursor.isFirst()) {
                        serviceId = cursor.getString(0);
                        cursor.close();
                    }
                }
            }
            return serviceId;
        } catch (IndexOutOfBoundsException ioEx) {
            ioEx.printStackTrace();
            return serviceId;
        }


    }

    public static String getCategoryId(Context context) {
        String[] PROJECTION = new String[]{Tables.IDS.CATEGORY_ID};
        String categoryId = null;
        try {
            Cursor cursor = context.getContentResolver().query(Tables.IDS.CONTENT_URI, PROJECTION, null, null, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    if (cursor.isFirst()) {
                        categoryId = cursor.getString(0);
                        cursor.close();
                    }
                }
            }
            return categoryId;
        } catch (IndexOutOfBoundsException ioEx) {
            ioEx.printStackTrace();
            return categoryId;
        }

    }

    public static LatLng getPickUpLatLng(Context context) {
        String[] PROJECTION = new String[]{Tables.Trip.PICK_LATITUDE, Tables.Trip.PICK_LONGITUDE};
        String latitude = null, longitude = null;
        try {
            Cursor cursor = context.getContentResolver().query(Tables.Trip.CONTENT_URI, PROJECTION, null, null, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    if (cursor.isFirst()) {
                        latitude = cursor.getString(0);
                        longitude = cursor.getString(1);
                        cursor.close();
                    }
                }
            }
            return new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        } catch (IndexOutOfBoundsException ioEx) {
            ioEx.printStackTrace();
            return null;
        }
    }

    public static LatLng getDropLatLng(Context context) {
        String[] PROJECTION = new String[]{Tables.Trip.DROP_LATITUDE, Tables.Trip.DROP_LONGITUDE};
        String latitude = null, longitude = null;
        try {
            Cursor cursor = context.getContentResolver().query(Tables.Trip.CONTENT_URI, PROJECTION, null, null, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    if (cursor.isFirst()) {
                        latitude = cursor.getString(0);
                        longitude = cursor.getString(1);
                        cursor.close();
                    }
                }
            }
            return new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        } catch (IndexOutOfBoundsException ioEx) {
            ioEx.printStackTrace();
            return null;
        }
    }

    public static String getDropLocation(Context context) {
        String[] PROJECTION = new String[]{Tables.Trip.DESTINATION_ADDRESS};
        String destination = null;
        try {
            Cursor cursor = context.getContentResolver().query(Tables.Trip.CONTENT_URI, PROJECTION, null, null, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    if (cursor.isFirst()) {
                        destination = cursor.getString(0);
                        cursor.close();
                    }
                }
            }
            return destination;
        } catch (IndexOutOfBoundsException ioEx) {
            ioEx.printStackTrace();
            return destination;
        }

    }


}
