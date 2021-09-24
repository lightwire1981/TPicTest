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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListAdptLikeGoods extends RecyclerView.Adapter<ListAdptLikeGoods.ViewHolder> {
    private Context context;
    private final ArrayList<ListItemLikeGoods> mData;

    private final String TAG = getClass().getName();

    public ListAdptLikeGoods(ArrayList<ListItemLikeGoods> list) { mData = list;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_like_goods, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItemLikeGoods item = mData.get(position);

        Glide.with(context).
                load(item.getGoodsImgUrl()).
                placeholder(R.drawable.tp_icon_brand01_on).
                diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).
                into(holder.goodsImg);
        holder.goodsImg.setOnClickListener(view -> {
            try {
                new DatabaseRequest(context, result -> {
                    if (result[0].equals("null")) {
                        Toast.makeText(context, "상품데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent = new Intent(context, GoodsInfoActivity.class);
                    intent.putExtra("goodsInfo", result[0]);
                    context.startActivity(intent);
                }).execute(DBRequestType.GET_GOODS_INFO.name(),
                        new JSONObject().put("goodsNo", holder.goodsName.getTag().toString()).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        holder.goodsName.setText(item.getGoodsName());
        holder.goodsName.setTag(item.getGoodsId());
        holder.goodsName.setOnClickListener(view -> {
            try {
                new DatabaseRequest(context, result -> {
                    if (result[0].equals("null")) {
                        Toast.makeText(context, "상품데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent = new Intent(context, GoodsInfoActivity.class);
                    intent.putExtra("goodsInfo", result[0]);
                    context.startActivity(intent);
                }).execute(DBRequestType.GET_GOODS_INFO.name(),
                        new JSONObject().put("goodsNo", holder.goodsName.getTag().toString()).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        holder.goodsPrice.setText(item.getGoodsPrice());
        holder.goodsRate.setText(item.getGoodsRate());
        holder.goodsLike.setChecked(item.getGoodsLike().equals("1"));
        holder.goodsLike.setOnCheckedChangeListener((checkBox, isChecked) -> {
            String userId = PreferenceSetting.loadPreference(context, PreferenceSetting.PREFERENCE_KEY.USER_ID);
            JSONObject likeData = new JSONObject();
            try {
                likeData.put("id", userId);
                likeData.put("goodsNo", holder.goodsName.getTag().toString());
                likeData.put("like", isChecked ? "1":"0");
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
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView goodsImg;
        TextView goodsName, goodsPrice, goodsRate;
        CheckBox goodsLike;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            goodsImg = itemView.findViewById(R.id.iVwLikeGoods);
            goodsName = itemView.findViewById(R.id.tVwLikeGoodsName);
            goodsPrice = itemView.findViewById(R.id.tVwLikeGoodsPrice);
            goodsRate = itemView.findViewById(R.id.tVwLikeGoodsEval);
            goodsLike = itemView.findViewById(R.id.cKbLikeGoods);
        }
    }
}
