package customer.dococean.com.patient.views.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by nagendrasrivastava on 24/06/16.
 */
public class BaseFragment extends Fragment {


    private ProgressDialog mProgressDialog;

    public BaseFragment() {
        // Required empty public constructor
    }

    protected boolean isFragmentAlive() {
        return getActivity() != null && isAdded() && !isDetached() && getView() != null && !isRemoving();
    }

    protected void showShortToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    protected void showLongToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    protected void showLongSnackBar(View view, String message) {
        if (view != null)
            Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    protected void showShortSnackBar(View view, String message) {
        if (view != null) {
            Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT).setAction("DISMISS", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            TextView tv = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
            tv.setTypeface(Typeface.createFromAsset(
                    view.getContext().getAssets(),
                    "fonts/Roboto-Bold.ttf"));
            snackbar.show();
        }
    }

    protected void showProgressDialog(String message, Activity activity) {
        initProgressDialog(activity);
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

    private void initProgressDialog(Activity activity) {
        if (mProgressDialog == null)
            mProgressDialog = new ProgressDialog(activity);
        mProgressDialog.setCancelable(false);
    }


}
