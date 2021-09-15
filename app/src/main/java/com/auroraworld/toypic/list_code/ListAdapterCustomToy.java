package com.auroraworld.toypic.list_code;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.auroraworld.toypic.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListAdapterCustomToy extends RecyclerView.Adapter<ListAdapterCustomToy.ViewHolder> {
    private Context context;
    private final ArrayList<ListItemCustomToy> mData;

    public ListAdapterCustomToy(ArrayList<ListItemCustomToy> list) {
        mData = list;
    }

    @NonNull
    @NotNull
    @Override
    public ListAdapterCustomToy.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_customtoy, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ListAdapterCustomToy.ViewHolder holder, int position) {
        ListItemCustomToy item = mData.get(position);

//        holder.productImg.setImageDrawable(item.getImgDrawable());
        Glide.with(context).
                load(item.getGoodsImgUrl()).
                placeholder(R.drawable.tp_icon_brand01_on).
                diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).
                into(holder.goodsImg);
        holder.goodsImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.goodsLike.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });
        holder.goodsName.setText(item.getGoodsName());
        holder.goodsRate.setText(item.getGoodsRate());
//        if (item.isFavorite()) {
//
//        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView goodsImg;
        CheckBox goodsLike;
        TextView goodsRate;
        TextView goodsName;

        ViewHolder(View itemView) {
            super(itemView);
            goodsImg = itemView.findViewById(R.id.iVwCustomListItem);
            goodsLike = itemView.findViewById(R.id.cKbCustomLike);
            goodsRate = itemView.findViewById(R.id.tVwCustomPredictNum);
            goodsName = itemView.findViewById(R.id.tVwCustomListItem);
        }
    }
}
