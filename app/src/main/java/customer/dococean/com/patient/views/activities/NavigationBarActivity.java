package customer.dococean.com.patient.views.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;

import customer.dococean.com.patient.R;
import customer.dococean.com.patient.db.DBHelper;
import customer.dococean.com.patient.interfaces.UIUpdateListener;
import customer.dococean.com.patient.network.ResponseCodes;
import customer.dococean.com.patient.network.response.logoutresponse.LogoutResponse;
import customer.dococean.com.patient.presenters.AuthPresenter;
import customer.dococean.com.patient.utils.ActivityManager;
import customer.dococean.com.patient.utils.AppUtils;
import customer.dococean.com.patient.views.custom.DococeanTextView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nagendrasrivastava on 24/08/16.
 */
public class NavigationBarActivity extends ToolbarActivity implements NavigationView.OnNavigationItemSelectedListener, UIUpdateListener {


    private static final String TAG = "NavigationBarActivity";
    private AuthPresenter mAuthPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuthPresenter = new AuthPresenter();
        mAuthPresenter.bindView(this);
    }


    @Override
    protected void onDestroy() {
        mAuthPresenter.unbindView(this);
        mAuthPresenter = null;
        super.onDestroy();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setupNavigationBar();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        setupNavigationBar();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        setupNavigationBar();
    }

    private void setupNavigationBar() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        View header = navigationView.getHeaderView(0);
        DococeanTextView userNameTV = (DococeanTextView) header.findViewById(R.id.nav_user_name_TV);
        DococeanTextView userEmailTV = (DococeanTextView) header.findViewById(R.id.nav_profile_user_email_TV);
        CircleImageView userImageView = (CircleImageView) header.findViewById(R.id.nav_profile_image);
        String userName = DBHelper.getUserName(this);
        String userEmail = DBHelper.getUserEmail(this);
        String userImageUrl = DBHelper.getUserImageUrl(this);
        if (userName != null)
            userNameTV.setText(userName);
        if (userEmail != null)
            userEmailTV.setText(userEmail);
        if (userImageUrl != null)
            Glide.with(this).load(userImageUrl).into(userImageView);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.my_bookings) {
            ActivityManager.startMyBookingActivity(this, null);
        } else if (id == R.id.nav_payment) {
            ActivityManager.startPaymentActivity(this, null);
        } else if (id == R.id.nav_profile) {
            ActivityManager.startProfileActivity(this, null);
        }
        //else if (id == R.id.nav_refer_friend) {
        //  ActivityManager.startReferFriendActivity(this, null);
        //}
        else if (id == R.id.nav_share) {
            AppUtils.shareApp(this, " Hey use this ", " Hey use this ", " Hey use this ");
        } else if (id == R.id.nav_rate_us) {
            showRatingDialog();
        } else if (id == R.id.nav_about_us) {
            ActivityManager.startAboutUsActivity(this, null);
        } else if (id == R.id.nav_logout) {
            mAuthPresenter.logout(this);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void showRatingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tell us what you think");
        builder.setMessage("Please rate us and provide feedback so that we can continue providing you the best service.");
        View ratingView = LayoutInflater.from(this).inflate(R.layout.rating_dialog_layout, null);
        builder.setView(ratingView);
        builder.create();
        final AlertDialog ad = builder.show();
        RatingBar ratingBar = (RatingBar) ratingView.findViewById(R.id.user_rating);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
                if (rating < 4) {
                    ad.dismiss();
                    //showBadRatingDialog();
                } else {
                    ad.dismiss();
                    showRateUsOnPlayStoreDialog();
                }
            }
        });
    }

    private void showRateUsOnPlayStoreDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Enjoying dococean")
                .setMessage("Please rate us on Google Play Store ")
                .setPositiveButton("Rate Us", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        AppUtils.showAppInPlaystore(NavigationBarActivity.this);
                    }
                })
                .setNegativeButton("Not now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
    }

    @Override
    public void showLoading(boolean isLoading) {

    }

    @Override
    public void showContent(Object items) {
        if (items != null) {
            Log.d(TAG, " inside show content logout ");
            cancelProgressDialog();
            if (items instanceof LogoutResponse) {
                Log.d(TAG, " inside logout response  ");
                LogoutResponse logoutResponse = (LogoutResponse) items;
                if (logoutResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                    DBHelper.clearCompleteDB(this);
                    ActivityManager.startLoginActivity(this, null);
                    this.finish();
                } else {
                    showShortToast(logoutResponse.getStatus().getMessage());
                }
            }
        }
    }

    @Override
    public void showError(Throwable error) {
        Log.d(TAG, " error is = " + error);
        cancelProgressDialog();
        if (error.getMessage() != null)
            showShortToast(error.getMessage());
    }
}
