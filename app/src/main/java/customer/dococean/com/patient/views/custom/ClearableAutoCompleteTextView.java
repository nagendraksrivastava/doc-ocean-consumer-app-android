package customer.dococean.com.patient.views.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import customer.dococean.com.patient.R;

/**
 * Created by nagendrasrivastava on 23/07/16.
 */
public class ClearableAutoCompleteTextView extends DocOceanAutoCompleteTextView {
    // was the text just cleared?
    boolean justCleared = false;
    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    private OnClearListener defaultClearListener = new OnClearListener() {

        @Override
        public void onClear() {
            ClearableAutoCompleteTextView et = ClearableAutoCompleteTextView.this;
            et.setText("");
        }
    };

    private OnClearListener onClearListener = defaultClearListener;

    public Drawable imgClearButton = getResources().getDrawable(
            R.drawable.ic_clear_text);

    public interface OnClearListener {
        void onClear();
    }

    public ClearableAutoCompleteTextView(Context context) {
        super(context);
        init(context, null);
    }

    public ClearableAutoCompleteTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public ClearableAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    void init(Context cxt, AttributeSet attrs) {
        int[] attrsArray = new int[]{
                android.R.attr.drawableLeft,
        };
        TypedArray ta = cxt.obtainStyledAttributes(attrs, attrsArray);
        int id = ta.getResourceId(0 /* index of attribute in attrsArray */, View.NO_ID);
        Drawable drawableLeft = ta.getDrawable(0);
        this.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null,
                imgClearButton, null);
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                ClearableAutoCompleteTextView et = ClearableAutoCompleteTextView.this;

                if (et.getCompoundDrawables()[2] == null)
                    return false;

                if (event.getAction() != MotionEvent.ACTION_UP)
                    return false;

                if (event.getX() > et.getWidth() - et.getPaddingRight() - imgClearButton.getIntrinsicWidth()) {
                    onClearListener.onClear();
                    justCleared = true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && isPopupShowing()) {
            InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager.hideSoftInputFromWindow(findFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS)) {
                return true;
            }
        }
        return super.onKeyPreIme(keyCode, event);
    }

    public void setImgClearButton(Drawable imgClearButton) {
        this.imgClearButton = imgClearButton;
    }

    public void setOnClearListener(final OnClearListener clearListener) {
        this.onClearListener = clearListener;
    }

    public void hideClearButton() {
        this.setCompoundDrawables(null, null, null, null);
    }

    public void showClearButton() {
        this.setCompoundDrawablesWithIntrinsicBounds(null, null, imgClearButton, null);
    }

    @Override
    public boolean enoughToFilter() {
        return true;
    }

}
