package com.wellstech.tpictest.list_code;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wellstech.tpictest.R;

import java.util.ArrayList;

public class ListAdapterEvalGoods extends RecyclerView.Adapter<ListAdapterEvalGoods.ViewHolder> {
    private final ArrayList<ListItemEvalGoods> goodsList;
    private Context context;

    /**
     * ListAdapterEvalGoods Constructor Method by Multiple Arguments
     * @param params 0 : ArrayList<ListItemEvalGoods>
     *               1 : Argument value
     *               2 : Callback Listener
     *               3 : etc ...
     */
    public ListAdapterEvalGoods(Object... params) {
        this.goodsList = (ArrayList<ListItemEvalGoods>)params[0];
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_evaluate_good, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItemEvalGoods item = goodsList.get(position);

        Glide.with(context).load(item.getImgUrl()).into(holder.goodsImg);
        if (item.getCategory()!=null) holder.categoryTag.setText(item.getCategory());
        holder.goodsName.setText(item.getGoodsName());
        if (item.getRatingPoint()!=null) holder.goodsRating.setRating(Float.parseFloat(item.getRatingPoint()));
    }

    @Override
    public int getItemCount() {
        return goodsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageButton goodsImg;
        TextView categoryTag;
        TextView goodsName;
        RatingBar goodsRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            goodsImg = itemView.findViewById(R.id.iBtnEvaluateGood);
            categoryTag = itemView.findViewById(R.id.tVwEvalCategoryTag);
            goodsName = itemView.findViewById(R.id.tVwEvalGoodName);
            goodsRating = itemView.findViewById(R.id.rtngBrEvalGoodPoint);
        }
    }
}
