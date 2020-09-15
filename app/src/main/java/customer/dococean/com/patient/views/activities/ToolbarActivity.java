package customer.dococean.com.patient.views.activities;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import customer.dococean.com.patient.R;

/**
 * Created by nagendrasrivastava on 24/06/16.
 */
public abstract class ToolbarActivity extends BaseActivity {
    private Toolbar toolbar;
    private TextView mToolbarTitle, mToolbarSubTitle;


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setUpToolBar();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        setUpToolBar();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        setUpToolBar();
    }

    protected void setUpToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mToolbarSubTitle = (TextView) toolbar.findViewById(R.id.toolbar_subtitle);
        setSupportActionBar(toolbar);
        //setWhiteHomeAsUpIndicator();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    protected void setHomeAsUpEnabled(boolean homeAsUpEnabled) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
        }
    }

    protected void setCrossHomeAsUpIcon() {
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_window_close_grey600_24dp);
    }

//    private void setWhiteHomeAsUpIndicator() {
//        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
//        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
//        getSupportActionBar().setHomeAsUpIndicator(upArrow);
//    }

    public void setToolbarTitle(String title) {
        if (toolbar != null && title != null && title.length() > 0) {
            mToolbarTitle.setText(title);
        }
    }

    public void setToolbarSubTitle(String subtitle) {
        if (toolbar != null && subtitle != null && subtitle.length() > 0) {
            if (mToolbarSubTitle != null && mToolbarSubTitle.getVisibility() == View.GONE)
                mToolbarSubTitle.setVisibility(View.VISIBLE);
            mToolbarSubTitle.setText(subtitle);
        }
    }

    protected void setToobarTextColor(int color) {
        mToolbarTitle.setTextColor(color);
    }

    protected Toolbar getToolbar() {
        return toolbar;
    }

    protected void changeTitle(String title) {
        setToolbarTitle(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void changeFragmentNoBackstack(int id, Fragment fragment) {
        if (!isFinishing()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(id, fragment)
                    .commit();
        }
    }
}
