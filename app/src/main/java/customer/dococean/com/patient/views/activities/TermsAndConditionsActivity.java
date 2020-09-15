package customer.dococean.com.patient.views.activities;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import customer.dococean.com.patient.R;

public class TermsAndConditionsActivity extends ToolbarActivity {

    private WebView mTermsAndConditionWebView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);
        setToolbarTitle(getString(R.string.action_bar_terms_and_conditions));
        intializeValues();
        setHomeAsUpEnabled(true);
        mTermsAndConditionWebView.loadUrl("http://dococean.com");

    }

    private void intializeValues() {
        mTermsAndConditionWebView = (WebView) findViewById(R.id.terms_and_condition_web_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setMax(100);
        mTermsAndConditionWebView.setWebChromeClient(new MyBrowser());
        mTermsAndConditionWebView.getSettings().setJavaScriptEnabled(true);
    }


    private class MyBrowser extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mProgressBar.setProgress(newProgress);
            if (newProgress >= 100) {
                mProgressBar.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }
}
