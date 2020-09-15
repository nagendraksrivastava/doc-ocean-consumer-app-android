package customer.dococean.com.patient.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


/**
 * Created by nagendrasrivastava on 30/06/16.
 */
public class PreferenceGeneral {
    private static final String TAG = PreferenceGeneral.class.getSimpleName();

    Context mContext;
    private static PreferenceGeneral mInstance;


    private PreferenceGeneral(Context context) {
        mContext = context;
    }

    public static PreferenceGeneral getInstance(Context context) {
        if (mInstance == null)
            mInstance = new PreferenceGeneral(context.getApplicationContext());
        return mInstance;
    }

    public void clearPreference(Context context) {
        context.getContentResolver().delete(Tables.Registry.CONTENT_URI, null, null);
    }

    public synchronized void addPreference(String key, Object value) {
        ContentValues cv = new ContentValues();
        cv.put(Tables.Registry.KEY, key);
        if (value instanceof Boolean)
            cv.put(Tables.Registry.VALUE, (Boolean) value ? 1 : 0);
        else if (value != null)
            cv.put(Tables.Registry.VALUE, String.valueOf(value));
        else if (value == null)
            cv.putNull(Tables.Registry.VALUE);

        if (mContext.getContentResolver().update(Tables.Registry.CONTENT_URI, cv, Tables.Registry.KEY + " = ? ", new String[]{key}) == 0) {
            mContext.getContentResolver().insert(Tables.Registry.CONTENT_URI, cv);
        }
    }


    public void removePreference(String key) {
        mContext.getContentResolver().delete(Tables.Registry.CONTENT_URI, Tables.Registry.KEY + "=?", new String[]{key});
    }

    public int getInt(String key, int def) {
        int value = def;
        Cursor c = mContext.getContentResolver().query(Tables.Registry.CONTENT_URI, null, Tables.Registry.KEY + "=?", new String[]{key}, null);
        if (c != null && c.moveToFirst()) {
            value = c.getInt(c.getColumnIndex(Tables.Registry.VALUE));
        }
        if (c != null)
            c.close();
        return value;
    }

    public long getLong(String key, long def) {
        long value = def;
        Cursor c = mContext.getContentResolver().query(Tables.Registry.CONTENT_URI, null, Tables.Registry.KEY + "=?", new String[]{key}, null);
        if (c != null && c.moveToFirst()) {
            value = c.getLong(c.getColumnIndex(Tables.Registry.VALUE));
        }
        if (c != null)
            c.close();
        return value;
    }

    public float getFloat(String key, float def) {
        float value = def;
        Cursor c = mContext.getContentResolver().query(Tables.Registry.CONTENT_URI, null, Tables.Registry.KEY + "=?", new String[]{key}, null);
        if (c != null && c.moveToFirst()) {
            value = c.getFloat(c.getColumnIndex(Tables.Registry.VALUE));
        }
        if (c != null)
            c.close();
        return value;
    }

    public double getDouble(String key, double def) {
        double value = def;
        Cursor c = mContext.getContentResolver().query(Tables.Registry.CONTENT_URI, null, Tables.Registry.KEY + "=?", new String[]{key}, null);
        if (c != null && c.moveToFirst()) {
            value = c.getDouble(c.getColumnIndex(Tables.Registry.VALUE));
        }
        if (c != null)
            c.close();
        return value;
    }

    public String getString(String key, String def) {
        String value = def;
        Cursor c = mContext.getContentResolver().query(Tables.Registry.CONTENT_URI, null, Tables.Registry.KEY + "=?", new String[]{key}, null);
        if (c != null && c.moveToFirst()) {
            value = c.getString(c.getColumnIndex(Tables.Registry.VALUE));
        }
        if (c != null)
            c.close();
        return value;
    }

    public boolean getBoolean(String key, boolean def) {
        boolean value = def;
        Cursor c = mContext.getContentResolver().query(Tables.Registry.CONTENT_URI, null, Tables.Registry.KEY + "=?", new String[]{key}, null);
        if (c != null && c.moveToFirst()) {
            value = c.getLong(c.getColumnIndex(Tables.Registry.VALUE)) == 1 ? true : false;
        }
        if (c != null)
            c.close();
        return value;
    }
}
