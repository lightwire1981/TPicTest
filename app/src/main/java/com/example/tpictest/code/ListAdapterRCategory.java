package com.example.tpictest.code;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tpictest.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ListAdapterRCategory extends RecyclerView.Adapter<ListAdapterRCategory.ViewHolder>{
    private final ArrayList<ListItemRCategory> mData;

    public ListAdapterRCategory(ArrayList<ListItemRCategory> List) {
        mData = List;
    }

    @NonNull
    @NotNull
    @Override
    public ListAdapterRCategory.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_ranking_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        ListItemRCategory item = mData.get(position);

        holder.rankCategory.setText(item.getRankCategory());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView rankCategory;

        public ViewHolder(View itemView) {
            super(itemView);
            rankCategory = itemView.findViewById(R.id.tVwRankCategory);
        }
    }
}
