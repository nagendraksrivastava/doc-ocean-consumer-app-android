package customer.dococean.com.patient.views.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by nagendrasrivastava on 26/06/16.
 */
public class AnchoredImageView extends ImageView {
    public AnchoredImageView(Context context) {
        super(context);
    }

    public AnchoredImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnchoredImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AnchoredImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        setTranslationY(-h / 2);
    }
}
