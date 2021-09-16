package com.auroraworld.toypic.list_code;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.auroraworld.toypic.GoodsInfoActivity;
import com.auroraworld.toypic.R;
import com.auroraworld.toypic.db.DBRequestType;
import com.auroraworld.toypic.db.DatabaseRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListAdptMainRankingToy extends RecyclerView.Adapter<ListAdptMainRankingToy.ViewHolder> {
    private Context context;
    private final ArrayList<ListItemMainRankingToy> mData;
    private final String TAG = getClass().getSimpleName();

    public ListAdptMainRankingToy(ArrayList<ListItemMainRankingToy> list) {
        mData = list;
    }

    @NonNull
    @NotNull
    @Override
    public ListAdptMainRankingToy.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_mrankingtoy, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        ListItemMainRankingToy item = mData.get(position);

        Glide.with(context).
                load(item.getGoodsImgUrl()).
                placeholder(R.drawable.tp_icon_brand01_on).
                diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).
                into(holder.goodsImg);
        holder.goodsImg.setOnClickListener(view -> {
            JSONObject goodsData = new JSONObject();
            try {
                goodsData.put("goodsNo", item.getGoodsId());
                new DatabaseRequest(context, result -> {
                    if (result[0].equals("null")) {
                        Toast.makeText(context, "상품데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent = new Intent(context, GoodsInfoActivity.class);
                    intent.putExtra("goodsInfo", result[0]);
                    context.startActivity(intent);
                }).execute(DBRequestType.GET_GOODS_INFO.name(), goodsData.toString());
                Log.i(TAG, goodsData.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        holder.goodsName.setText(item.getGoodsName());
        holder.goodsName.setOnClickListener(view -> {
            JSONObject goodsData = new JSONObject();
            try {
                goodsData.put("goodsNo", item.getGoodsId());
                new DatabaseRequest(context, result -> {
                    if (result[0].equals("null")) {
                        Toast.makeText(context, "상품데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent = new Intent(context, GoodsInfoActivity.class);
                    intent.putExtra("goodsInfo", result[0]);
                    context.startActivity(intent);
                }).execute(DBRequestType.GET_GOODS_INFO.name(), goodsData.toString());
                Log.i(TAG, goodsData.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
//        holder.ranking.setText(item.getRanking());
        holder.ranking.setText(String.valueOf(position + 1));
        holder.goodsRate.setText(item.getGoodsRate());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView goodsImg;
        TextView ranking;
        TextView goodsRate;
        TextView goodsName;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            goodsImg = itemView.findViewById(R.id.iVwRankProductImg);
            ranking = itemView.findViewById(R.id.tVwRankofProduct);
            goodsRate = itemView.findViewById(R.id.tVwRatingPoint);
            goodsName = itemView.findViewById(R.id.tVwRankingListItemName);
        }
    }


}
