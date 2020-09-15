package customer.dococean.com.patient.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import customer.dococean.com.patient.views.activities.AboutUsActivity;
import customer.dococean.com.patient.views.activities.AddPatientInformationActivity;
import customer.dococean.com.patient.views.activities.AddressActivity;
import customer.dococean.com.patient.views.activities.BookingActivity;
import customer.dococean.com.patient.views.activities.BookingConfirmedActivity;
import customer.dococean.com.patient.views.activities.FeedbackActivity;
import customer.dococean.com.patient.views.activities.ForgotPasswordActivity;
import customer.dococean.com.patient.views.activities.HomeActivity;
import customer.dococean.com.patient.views.activities.LoginActivity;
import customer.dococean.com.patient.views.activities.MyBookingsActivity;
import customer.dococean.com.patient.views.activities.PatientListActivity;
import customer.dococean.com.patient.views.activities.PaymentActivity;
import customer.dococean.com.patient.views.activities.ProfileActivity;
import customer.dococean.com.patient.views.activities.ReferFriendActivity;
import customer.dococean.com.patient.views.activities.SelectCategoryActivity;
import customer.dococean.com.patient.views.activities.SelectServiceActivity;
import customer.dococean.com.patient.views.activities.SignupActivity;
import customer.dococean.com.patient.views.activities.TermsAndConditionsActivity;

/**
 * Created by nagendrasrivastava on 10/07/16.
 */
public class ActivityManager {

    public static void startHomeActivity(Context context, Bundle extras) {
        Intent homeIntent = new Intent(context, HomeActivity.class);
        if (extras != null) {
            homeIntent.putExtra(DocOceanConstants.IntentConstants.EXTRAS, extras);
        }
        //startActivity(context, homeIntent);
        startActivityWithNewTask(context, homeIntent);
    }


    public static void startBookingActivity(Context context, Bundle extras) {
        Intent homeIntent = new Intent(context, BookingActivity.class);
        if (extras != null) {
            homeIntent.putExtras(extras);
        }
        startActivity(context, homeIntent);
    }

    public static void startPaymentActivity(Context context, Bundle extras) {
        Intent homeIntent = new Intent(context, PaymentActivity.class);
        if (extras != null) {
            homeIntent.putExtras(extras);
        }
        startActivity(context, homeIntent);
    }

    public static void startMyBookingActivity(Context context, Bundle extras) {
        Intent homeIntent = new Intent(context, MyBookingsActivity.class);
        if (extras != null) {
            homeIntent.putExtras(extras);
        }
        startActivity(context, homeIntent);
    }

    public static void startLoginActivity(Context context, Bundle extras) {
        Intent loginIntent = new Intent(context, LoginActivity.class);
        if (extras != null) {
            loginIntent.putExtra(DocOceanConstants.IntentConstants.EXTRAS, extras);
        }
        startActivity(context, loginIntent);
    }

    public static void startSelectCategoryActivity(Context context, Bundle extras) {
        Intent selectCategoryIntent = new Intent(context, SelectCategoryActivity.class);
        if (extras != null) {
            selectCategoryIntent.putExtra(DocOceanConstants.IntentConstants.EXTRAS, extras);
        }
        startActivity(context, selectCategoryIntent);
    }

    public static void startSelectServiceActivity(Context context, Bundle extras) {
        Intent selectServiceIntent = new Intent(context, SelectServiceActivity.class);
        if (extras != null) {
            selectServiceIntent.putExtra(DocOceanConstants.IntentConstants.EXTRAS, extras);
        }
        startActivity(context, selectServiceIntent);
    }


    public static void startForgotPasswordActivity(Context context, Bundle extras) {
        Intent forgotpasswordIntent = new Intent(context, ForgotPasswordActivity.class);
        if (extras != null) {
            forgotpasswordIntent.putExtra(DocOceanConstants.IntentConstants.EXTRAS, extras);
        }
        startActivity(context, forgotpasswordIntent);
    }


    public static void startFeedbackActivity(Context context, Bundle extras) {
        Intent feedbackIntent = new Intent(context, FeedbackActivity.class);
        if (extras != null) {
            feedbackIntent.putExtra(DocOceanConstants.IntentConstants.EXTRAS, extras);
        }
        startActivity(context, feedbackIntent);
    }


    public static void startSignupActivity(Context context, Bundle extras) {
        Intent signupActivityIntent = new Intent(context, SignupActivity.class);
        if (extras != null) {
            signupActivityIntent.putExtra(DocOceanConstants.IntentConstants.EXTRAS, extras);
        }
        startActivity(context, signupActivityIntent);
    }


    public static void startProfileActivity(Context context, Bundle extras) {
        Intent profileActivityIntent = new Intent(context, ProfileActivity.class);
        if (extras != null) {
            profileActivityIntent.putExtra(DocOceanConstants.IntentConstants.EXTRAS, extras);
        }
        startActivity(context, profileActivityIntent);
    }


    public static void startAddPatientActivity(Context context, Bundle extras) {
        Intent addPatientActivityIntent = new Intent(context, AddPatientInformationActivity.class);
        if (extras != null) {
            addPatientActivityIntent.putExtra(DocOceanConstants.IntentConstants.EXTRAS, extras);
        }
        startActivity(context, addPatientActivityIntent);
    }


    public static void startPatientListActivity(Context context, Bundle extras) {
        Intent profileActivityIntent = new Intent(context, PatientListActivity.class);
        if (extras != null) {
            profileActivityIntent.putExtra(DocOceanConstants.IntentConstants.EXTRAS, extras);
        }
        startActivity(context, profileActivityIntent);
    }

    public static void startReferFriendActivity(Context context, Bundle extras) {
        Intent profileActivityIntent = new Intent(context, ReferFriendActivity.class);
        if (extras != null) {
            profileActivityIntent.putExtra(DocOceanConstants.IntentConstants.EXTRAS, extras);
        }
        startActivity(context, profileActivityIntent);
    }

    public static void startAboutUsActivity(Context context, Bundle extras) {
        Intent aboutUsActivityIntent = new Intent(context, AboutUsActivity.class);
        if (extras != null) {
            aboutUsActivityIntent.putExtra(DocOceanConstants.IntentConstants.EXTRAS, extras);
        }
        startActivity(context, aboutUsActivityIntent);
    }


    public static void startAddressActivity(Context context, Bundle extras) {
        Intent addressActivityIntent = new Intent(context, AddressActivity.class);
        if (extras != null) {
            addressActivityIntent.putExtra(DocOceanConstants.IntentConstants.EXTRAS, extras);
        }
        startActivity(context, addressActivityIntent);
    }


    private static void startActivity(Context context, Intent intent) {
        context.startActivity(intent);
    }

    public static void startBookingConfirmedActivityWithNewTask(Context context, Bundle extras) {
        Intent bookingConfirmedActivityIntent = new Intent(context, BookingConfirmedActivity.class);
        if (extras != null) {
            bookingConfirmedActivityIntent.putExtras(extras);
        }
        startActivityWithNewTask(context, bookingConfirmedActivityIntent);
    }


    public static void startBookingConfirmedActivity(Context context, Bundle extras) {
        Intent bookingConfirmedActivityIntent = new Intent(context, BookingConfirmedActivity.class);
        if (extras != null) {
            bookingConfirmedActivityIntent.putExtras(extras);
        }
        startActivity(context, bookingConfirmedActivityIntent);
    }


    public static void startActivityWithNewTask(Context context, Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    public static void startTermsAndConditionsActivity(Context context, Bundle extras) {
        Intent aboutUsActivityIntent = new Intent(context, TermsAndConditionsActivity.class);
        if (extras != null) {
            aboutUsActivityIntent.putExtra(DocOceanConstants.IntentConstants.EXTRAS, extras);
        }
        startActivity(context, aboutUsActivityIntent);
    }

}
