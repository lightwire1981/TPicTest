package com.auroraworld.toypic.list_code;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.auroraworld.toypic.GoodsInfoActivity;
import com.auroraworld.toypic.R;
import com.auroraworld.toypic.db.DBRequestType;
import com.auroraworld.toypic.db.DatabaseRequest;
import com.auroraworld.toypic.utils.PreferenceSetting;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListAdapterCustomToy extends RecyclerView.Adapter<ListAdapterCustomToy.ViewHolder> {
    private Context context;
    private final ArrayList<ListItemCustomToy> mData;
    private final String TAG = getClass().getSimpleName();

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

        holder.goodsLike.setOnCheckedChangeListener((checkBox, isChecked) -> {
            try {
                JSONObject userInfo = new JSONObject(PreferenceSetting.loadPreference(context, PreferenceSetting.PREFERENCE_KEY.USER_INFO));
                String userId = userInfo.getString("id");
                String goodsNo = item.getGoodsId();
                JSONObject likeData = new JSONObject();
                likeData.put("id", userId);
                likeData.put("goodsNo", goodsNo);
                if (isChecked) {
                    likeData.put("like", "1");
                } else {
                    likeData.put("like", "0");
                }
                new DatabaseRequest(context, result -> {
                    switch (result[0]) {
                        case "INSERT_FAIL":
                        case "UPDATE_FAIL":
                            Toast.makeText(context, "정보 처리 실패", Toast.LENGTH_SHORT).show();
                            break;
                        case "INSERT_OK":
                        case "UPDATE_OK":
                            if (isChecked) {
                                Log.i(TAG, "좋아요 등록");
                            } else {
                                Log.i(TAG, "좋아요 해제");
                            }
                            break;
                    }
                }).execute(DBRequestType.LIKE.name(), likeData.toString());
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
