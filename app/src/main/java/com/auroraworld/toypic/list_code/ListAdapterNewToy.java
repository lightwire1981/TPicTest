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

public class ListAdapterNewToy extends RecyclerView.Adapter<ListAdapterNewToy.ViewHolder>{
    private Context context;
    private final ArrayList<ListItemNewToy> mData;
    private final String TAG = getClass().getSimpleName();

    public ListAdapterNewToy(ArrayList<ListItemNewToy> list) {
        mData = list;
    }

    @NonNull
    @NotNull
    @Override
    public ListAdapterNewToy.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_newtoy, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        ListItemNewToy item = mData.get(position);
        Glide.with(context).
                load(item.getGoodsImgUrl1()).
                placeholder(R.drawable.tp_icon_brand01_on).
                diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).
                into(holder.goodsImg1);
        Glide.with(context).
                load(item.getGoodsImgUrl2()).
                placeholder(R.drawable.tp_icon_brand01_on).
                diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).
                into(holder.goodsImg2);
        holder.goodsImg1.setOnClickListener(v -> callGoodsInfo(item.getGoodsId1()));
        holder.goodsImg2.setOnClickListener(v -> callGoodsInfo(item.getGoodsId2()));
        holder.goodsName1.setText(item.getGoodsName1());
        holder.goodsName1.setOnClickListener(v -> callGoodsInfo(item.getGoodsId1()));
        holder.goodsName2.setText(item.getGoodsName2());
        holder.goodsName2.setOnClickListener(v -> callGoodsInfo(item.getGoodsId2()));
    }

    private void callGoodsInfo(String goodsId) {
        JSONObject goodsData = new JSONObject();
        try {
            goodsData.put("goodsNo", goodsId);
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
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView goodsImg1, goodsImg2;
        TextView goodsName1, goodsName2;

        public ViewHolder(View itemView) {
            super(itemView);
            goodsImg1 = itemView.findViewById(R.id.iVwMainNewToyItem1);
            goodsImg2 = itemView.findViewById(R.id.iVwMainNewToyItem2);
            goodsName1 = itemView.findViewById(R.id.tVwMainNewToyItem1);
            goodsName2 = itemView.findViewById(R.id.tVwMainNewToyItem2);
        }
    }
}
