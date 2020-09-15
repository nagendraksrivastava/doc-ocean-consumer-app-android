package customer.dococean.com.patient.views.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import customer.dococean.com.patient.R;
import customer.dococean.com.patient.adapters.CategoryListRecyclerViewAdapter;
import customer.dococean.com.patient.adapters.NothingSelectedSpinnerAdapter;
import customer.dococean.com.patient.adapters.ProfessionSpinnerArrayAdapter;
import customer.dococean.com.patient.interfaces.SendDataFromAdapter;
import customer.dococean.com.patient.interfaces.UIUpdateListener;
import customer.dococean.com.patient.network.ResponseCodes;
import customer.dococean.com.patient.network.response.profession_response.Category;
import customer.dococean.com.patient.network.response.profession_response.Profession;
import customer.dococean.com.patient.network.response.profession_response.ProfessionResponse;
import customer.dococean.com.patient.presenters.ProfessionPresenter;
import customer.dococean.com.patient.utils.ActivityManager;
import customer.dococean.com.patient.utils.DocOceanConstants;
import customer.dococean.com.patient.views.custom.DocOceanButton;

public class SelectCategoryActivity extends NavigationBarActivity implements UIUpdateListener, View.OnClickListener, AdapterView.OnItemSelectedListener, SendDataFromAdapter {

    private Spinner mProfessionSpinner;
    private RecyclerView mCategoryRecyclerView;
    private DocOceanButton mContinueButton;
    private ProfessionPresenter mProfessionPresenter;
    private List<Category> mCategoryList = new ArrayList<>();
    private CategoryListRecyclerViewAdapter mCategoryListRecyclerViewAdapter;
    private Profession mProfession;
    private Category mCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category);
        init();
        setToolbarTitle("Select Experts");
        setListeners();
        mCategoryRecyclerView.setHasFixedSize(true);
        mCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProfessionPresenter = new ProfessionPresenter();
        mProfessionPresenter.bindView(this);
        mCategoryListRecyclerViewAdapter = new CategoryListRecyclerViewAdapter(mCategoryList, this);
        mCategoryRecyclerView.setAdapter(mCategoryListRecyclerViewAdapter);
        mProfessionPresenter.getProfessions(this);
        showProgressDialog(getString(R.string.text_please_wait));
    }

    private void init() {
        mProfessionSpinner = (Spinner) findViewById(R.id.profession_spinner);
        mCategoryRecyclerView = (RecyclerView) findViewById(R.id.category_list);
        mContinueButton = (DocOceanButton) findViewById(R.id.continue_button);
    }

    private void setListeners() {
        mContinueButton.setOnClickListener(this);
        mProfessionSpinner.setOnItemSelectedListener(this);
    }


    @Override
    protected void onDestroy() {
        mProfessionPresenter.unbindView(this);
        mProfessionPresenter = null;
        super.onDestroy();
    }

    private void addDataToSpinner(List<Profession> professionList) {
        ProfessionSpinnerArrayAdapter professionSpinnerArrayAdapter = new ProfessionSpinnerArrayAdapter(this, professionList);
        professionSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mProfessionSpinner.setAdapter(new NothingSelectedSpinnerAdapter(professionSpinnerArrayAdapter, android.R.layout.simple_spinner_dropdown_item, this, getResources().getString(R.string.select_profession)));
    }

    @Override
    public void showLoading(boolean isLoading) {
        super.showLoading(isLoading);
    }

    @Override
    public void showContent(Object items) {
        if (items != null) {
            if (items instanceof ProfessionResponse) {
                cancelProgressDialog();
                ProfessionResponse professionResponse = (ProfessionResponse) items;
                if (professionResponse.getStatus().getCode() == ResponseCodes.SUCCESS) {
                    addDataToSpinner(professionResponse.getProfessions());
                } else {
                    showShortToast(professionResponse.getStatus().getMessage());
                }
            }
        }
        super.showContent(items);
    }

    @Override
    public void showError(Throwable error) {
        cancelProgressDialog();
        super.showError(error);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.continue_button:
                validateAndStartSelectServiceActivity();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if (adapterView.getItemAtPosition(position) != null) {
            mCategoryList.clear();
            mProfession = (Profession) adapterView.getItemAtPosition(position);
            mCategoryList.addAll(mProfession.getCategories());
            mCategoryListRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onDataReceived(Object data) {
        mCategory = (Category) data;
    }

    private void validateAndStartSelectServiceActivity() {

        if (mProfession == null) {
            showShortToast("Please select expert");
            return;
        }
        if (mCategory == null) {
            showShortToast("Please select expert category");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt(DocOceanConstants.IntentConstants.CATEGORY_ID, mCategory.getId());
        bundle.putParcelable(DocOceanConstants.IntentConstants.PROFESSION_DATA, mProfession);
        ActivityManager.startSelectServiceActivity(this, bundle);
        //this.finish();
    }

}
