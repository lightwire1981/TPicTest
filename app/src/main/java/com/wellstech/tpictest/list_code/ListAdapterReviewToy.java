package com.wellstech.tpictest.list_code;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wellstech.tpictest.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ListAdapterReviewToy extends RecyclerView.Adapter<ListAdapterReviewToy.ViewHolder>{
    private final ArrayList<ListItemReviewToy> mData;

    public ListAdapterReviewToy(ArrayList<ListItemReviewToy> list) {
        mData = list;
    }

    @NonNull
    @NotNull
    @Override
    public ListAdapterReviewToy.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_reviewtoy, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        ListItemReviewToy item = mData.get(position);

        holder.imgProduct.setImageDrawable(item.getImgProduct());
        holder.ratingBar.setRating(Float.parseFloat(item.getNumberRate()));
        holder.rateProduct.setText(item.getNumberRate());
        holder.likeProduct.setText(item.getNumberLike());
        holder.commentProduct.setText(item.getCommentReview());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        RatingBar ratingBar;
        TextView rateProduct, likeProduct;
        TextView commentProduct;

        public ViewHolder(View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.iVwMainRevToyItem);
            ratingBar = itemView.findViewById(R.id.rtnbRatingPoint);
            rateProduct = itemView.findViewById(R.id.tVwMainRevToyRatingNumber);
            likeProduct = itemView.findViewById(R.id.tVwMainRevToyLikeTotal);
            commentProduct = itemView.findViewById(R.id.tVwMainRevToyComment);
        }
    }
}
