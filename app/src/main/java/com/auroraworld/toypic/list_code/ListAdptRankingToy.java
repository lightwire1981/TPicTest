package com.auroraworld.toypic.list_code;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.auroraworld.toypic.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ListAdptRankingToy extends RecyclerView.Adapter<ListAdptRankingToy.ViewHolder>{
    private final ArrayList<ListItemRankingToy> mData;

    public ListAdptRankingToy(ArrayList<ListItemRankingToy> list) {
        mData = list;
    }

    @NonNull
    @NotNull
    @Override
    public ListAdptRankingToy.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_rrankingtoy, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        ListItemRankingToy item = mData.get(position);

        holder.imgProduct.setImageDrawable(item.getImgProduct());
        holder.ranking.setText(item.getNumberRank());
        holder.rankType.setText(item.getRankCategory());
        holder.nameProduct.setText(item.getNameProduct());
        holder.priceProduct.setText(item.getPriceProduct());
        holder.rateNumber.setText(item.getNumberRate());
        holder.countReview.setText(item.getReviewCount());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView ranking, rankType, nameProduct, priceProduct, rateNumber, countReview;

        public ViewHolder(View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.iVwRankListProductImg);
            ranking = itemView.findViewById(R.id.tVwRankingNumber);
            rankType = itemView.findViewById(R.id.tVwRankListCategoryType);
            nameProduct = itemView.findViewById(R.id.tVwRankListProductName);
            priceProduct = itemView.findViewById(R.id.tVwRankListProductPrice);
            rateNumber = itemView.findViewById(R.id.tVwRankListRate);
            countReview = itemView.findViewById(R.id.tVwRankListReviewCount);
        }
    }
}
