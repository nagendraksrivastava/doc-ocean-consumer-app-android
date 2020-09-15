package customer.dococean.com.patient.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import java.util.List;

import customer.dococean.com.patient.R;
import customer.dococean.com.patient.network.response.profession_response.Category;
import customer.dococean.com.patient.views.activities.SelectCategoryActivity;

/**
 * Created by nagendrasrivastava on 27/09/16.
 */

public class CategoryListRecyclerViewAdapter extends RecyclerView.Adapter<CategoryListRecyclerViewAdapter.CategoryViewHolder> {

    private static final String TAG = "CategoryListRecyclerVie";
    private List<Category> mCategoryList;
    private SelectCategoryActivity mSelectCategoryActivity;
    private int mSelectedPosition = -1;

    public CategoryListRecyclerViewAdapter(List<Category> categories, SelectCategoryActivity activity) {
        this.mCategoryList = categories;
        this.mSelectCategoryActivity = activity;
    }


    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, " Category list is = " + mCategoryList.size());
        return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        Category category = mCategoryList.get(position);
        holder.mCheckedTextview.setText(category.getName());
        holder.mCheckedTextview.setTag(position);
        if (mSelectedPosition == position) {
            holder.mCheckedTextview.setChecked(true);
        } else {
            holder.mCheckedTextview.setChecked(false);
        }
        Log.d(TAG, " Bind view called ");
    }


    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CheckedTextView mCheckedTextview;

        CategoryViewHolder(View itemView) {
            super(itemView);
            mCheckedTextview = (CheckedTextView) itemView.findViewById(R.id.category_checked_text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mSelectedPosition = getAdapterPosition();
            notifyDataSetChanged();
            int position = (int) mCheckedTextview.getTag();
            /**
             *  Below mCheckedTextview is checked then its value is coming false so below logic is based on that
             */
            if (!mCheckedTextview.isChecked()) {
                mSelectCategoryActivity.onDataReceived(mCategoryList.get(position));
            } else {
                mSelectCategoryActivity.onDataReceived(null);
            }
        }
    }
}
