package com.auroraworld.toypic.list_code;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.auroraworld.toypic.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ListAdapterReviewToy extends RecyclerView.Adapter<ListAdapterReviewToy.ViewHolder>{
    private Context context;
    private final ArrayList<ListItemReviewToy> mData;

    public interface ReviewClickListener {
        void onSelectReview(ListItemReviewToy item);
    }
    private final ReviewClickListener reviewClickListener;

    public ListAdapterReviewToy(ArrayList<ListItemReviewToy> list, ReviewClickListener reviewClickListener) {
        mData = list;
        this.reviewClickListener = reviewClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public ListAdapterReviewToy.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_reviewtoy, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        ListItemReviewToy item = mData.get(position);

        Glide.with(context).
                load(item.getGoodsImgUrl()).
                placeholder(R.drawable.tp_icon_brand01_on).
                diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).
                into(holder.goodsImg);
        holder.goodsImg.setOnClickListener(v -> reviewClickListener.onSelectReview(item));
        holder.evaluateBar.setRating(Float.parseFloat(item.getNumberRate()));
        holder.evaluate.setText(item.getNumberRate());
        holder.likeCount.setText(context.getString(R.string.txt_goods_info_review_like_template, item.getNumberLike()));
        holder.review.setText(item.getReview());
        holder.review.setOnClickListener(v -> reviewClickListener.onSelectReview(item));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView goodsImg;
        RatingBar evaluateBar;
        TextView evaluate, likeCount;
        TextView review;

        public ViewHolder(View itemView) {
            super(itemView);
            goodsImg = itemView.findViewById(R.id.iVwMainRevToyItem);
            evaluateBar = itemView.findViewById(R.id.rtnbRatingPoint);
            evaluate = itemView.findViewById(R.id.tVwMainRevToyRatingNumber);
            likeCount = itemView.findViewById(R.id.tVwMainRevToyLikeTotal);
            review = itemView.findViewById(R.id.tVwMainRevToyComment);
        }
    }
}
