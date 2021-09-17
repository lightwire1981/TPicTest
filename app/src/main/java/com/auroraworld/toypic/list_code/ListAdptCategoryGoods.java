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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class ListAdptCategoryGoods extends RecyclerView.Adapter<ListAdptCategoryGoods.ViewHolder>{
    private Context context;
    private final ArrayList<ListItemCategoryGoods> mData;

    private final String TAG = getClass().getSimpleName();

    public interface GoodsListSelectListener {
        void onSelectGoods(String goodsNo);
    }
    private final GoodsListSelectListener goodsListSelectListener;

    public ListAdptCategoryGoods(ArrayList<ListItemCategoryGoods> list, GoodsListSelectListener goodsListSelectListener) {
        mData = list;
        this.goodsListSelectListener = goodsListSelectListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_category_goods, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItemCategoryGoods item = mData.get(position);

        Glide.with(context).
                load(item.getGoodsImgUrl()).
                placeholder(R.drawable.tp_icon_brand01_on).
                diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).
                into(holder.goodsImg);
        holder.goodsImg.setOnClickListener(view -> goodsListSelectListener.onSelectGoods(item.getGoodsId()));
        holder.goodsName.setText(item.getGoodsName());
        holder.goodsName.setOnClickListener(view -> goodsListSelectListener.onSelectGoods(item.getGoodsId()));
        holder.goodsEvaluate.setText(context.getString(R.string.txt_category_eval_template, item.getGoodsEvaluate()));
        holder.goodsReviewCount.setText(item.getGoodsReviewCount());
        holder.goodsPrice.setText(item.getGoodsPrice());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView goodsImg;
        private final TextView goodsName;
        private final TextView goodsEvaluate;
        private final TextView goodsReviewCount;
        private final TextView goodsPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            goodsImg = itemView.findViewById(R.id.iVwCategoryGood);
            goodsName = itemView.findViewById(R.id.tVwCategoryGoodsNm);
            goodsEvaluate = itemView.findViewById(R.id.tVwCategoryGoodsRate);
            goodsReviewCount = itemView.findViewById(R.id.tVwCategoryGoodsReview);
            goodsPrice = itemView.findViewById(R.id.tVwCategoryGoodsPrice);
        }
    }
}
