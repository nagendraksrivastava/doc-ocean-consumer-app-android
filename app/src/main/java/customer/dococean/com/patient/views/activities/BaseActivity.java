package customer.dococean.com.patient.views.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.Toast;

import rx.Subscription;

/**
 * Created by nagendrasrivastava on 24/06/16.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initProgressDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void showShortToast(String message) {
        Toast.makeText(BaseActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    protected void showLongToast(String message) {
        Toast.makeText(BaseActivity.this, message, Toast.LENGTH_LONG).show();
    }

    protected void showLongSnackbar(String msg) {
        try {
            ViewGroup vg = (ViewGroup) this.findViewById(android.R.id.content);
            if (vg != null) {
                Snackbar.make(vg.getChildAt(0), msg, Snackbar.LENGTH_LONG).show();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    protected void showShortSnackbar(String msg) {
        try {
            ViewGroup vg = (ViewGroup) this.findViewById(android.R.id.content);
            if (vg != null) {
                Snackbar.make(vg.getChildAt(0), msg, Snackbar.LENGTH_SHORT).show();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    protected void changeFragmentWithOutBackStack(int id, Fragment fragment, String fragmentTag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(id, fragment, fragmentTag)
                .commit();
    }

    protected void changeFragmentWithBackStack(int id, Fragment fragment,String fragmentTag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(id, fragment)
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }

    protected void showProgressDialog(String message) {
        initProgressDialog();
        mProgressDialog.setMessage(message);
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    protected void cancelProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.cancel();
        }
    }

    private void initProgressDialog() {
        if (mProgressDialog == null)
            mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
    }


}
