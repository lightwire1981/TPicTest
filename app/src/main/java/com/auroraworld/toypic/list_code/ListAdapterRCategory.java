package com.auroraworld.toypic.list_code;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.auroraworld.toypic.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ListAdapterRCategory extends RecyclerView.Adapter<ListAdapterRCategory.ViewHolder>{
    private final ArrayList<ListItemRCategory> mData;
    private static final ArrayList<CheckBox> indexBoxList = new ArrayList<>();

    public interface CategorySelectListener {
        void onSelectCategory(String categoryName);
    }
    private final CategorySelectListener categorySelectListener;

    public ListAdapterRCategory(ArrayList<ListItemRCategory> List, CategorySelectListener categorySelectListener) {
        mData = List;
        this.categorySelectListener = categorySelectListener;
    }

    @NonNull
    @NotNull
    @Override
    public ListAdapterRCategory.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.index_item_radio_button, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        ListItemRCategory item = mData.get(position);

        indexBoxList.add(position, holder.ckbCategory);
        holder.ckbCategory.setText(item.getRankCategory());
        holder.ckbCategory.setOnCheckedChangeListener((checkbox, isChecked) -> {
            if (isChecked) {
                String label = checkbox.getText().toString();
                for (CheckBox ckb : indexBoxList) {
                    String temp = ckb.getText().toString();
                    if (!temp.equals(label)) {
                        ckb.setChecked(false);
                    }
                }
                categorySelectListener.onSelectCategory(checkbox.getText().toString());
            }
        });
        if (position==0) {
            holder.ckbCategory.performClick();
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox ckbCategory;

        public ViewHolder(View itemView) {
            super(itemView);
            ckbCategory = itemView.findViewById(R.id.cKbxIndexItem);
        }
    }
}
