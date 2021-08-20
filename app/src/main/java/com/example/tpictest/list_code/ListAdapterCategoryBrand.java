package com.example.tpictest.list_code;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tpictest.R;

import java.util.ArrayList;

public class ListAdapterCategoryBrand extends RecyclerView.Adapter<ListAdapterCategoryBrand.ViewHolder> {

    private final ArrayList<ListItemCategoryBrand> mData;

    public ListAdapterCategoryBrand(ArrayList<ListItemCategoryBrand> list) { mData = list; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_category_brand, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItemCategoryBrand item = mData.get(position);

        switch (item.getItemCount()) {
            case 1:
                holder.brandImg1.setImageDrawable(item.getBrandDrawable1());
                break;
            case 2:
                holder.brandImg2.setVisibility(View.VISIBLE);
                holder.brandImg1.setImageDrawable(item.getBrandDrawable1());
                holder.brandImg2.setImageDrawable(item.getBrandDrawable2());
                break;
            case 3:
                holder.brandImg2.setVisibility(View.VISIBLE);
                holder.brandImg3.setVisibility(View.VISIBLE);
                holder.brandImg1.setImageDrawable(item.getBrandDrawable1());
                holder.brandImg2.setImageDrawable(item.getBrandDrawable2());
                holder.brandImg3.setImageDrawable(item.getBrandDrawable3());
                break;
            case 4:
                holder.brandImg2.setVisibility(View.VISIBLE);
                holder.brandImg3.setVisibility(View.VISIBLE);
                holder.brandImg4.setVisibility(View.VISIBLE);
                holder.brandImg1.setImageDrawable(item.getBrandDrawable1());
                holder.brandImg2.setImageDrawable(item.getBrandDrawable2());
                holder.brandImg3.setImageDrawable(item.getBrandDrawable3());
                holder.brandImg4.setImageDrawable(item.getBrandDrawable4());
                break;
            default:
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView brandImg1, brandImg2, brandImg3, brandImg4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            brandImg1 = itemView.findViewById(R.id.iBtnBrandImage1);
            brandImg1.setOnClickListener(listener);
            brandImg2 = itemView.findViewById(R.id.iBtnBrandImage2);
            brandImg2.setOnClickListener(listener);
            brandImg3 = itemView.findViewById(R.id.iBtnBrandImage3);
            brandImg3.setOnClickListener(listener);
            brandImg4 = itemView.findViewById(R.id.iBtnBrandImage4);
            brandImg4.setOnClickListener(listener);
        }

        private final View.OnClickListener listener = view -> {

        };
    }


}
