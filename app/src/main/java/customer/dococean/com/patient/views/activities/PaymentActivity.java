package customer.dococean.com.patient.views.activities;

import android.os.Bundle;

import customer.dococean.com.patient.R;

public class PaymentActivity extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        setToolbarTitle("Payment");
        setHomeAsUpEnabled(true);
    }
}
