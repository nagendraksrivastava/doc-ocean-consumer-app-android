package customer.dococean.com.patient.utils;

/**
 * Created by nagendrasrivastava on 07/07/16.
 */
public class DocOceanUtils {

    public static boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }
}
