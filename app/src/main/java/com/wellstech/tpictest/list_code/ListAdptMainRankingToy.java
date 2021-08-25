package com.wellstech.tpictest.list_code;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wellstech.tpictest.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ListAdptMainRankingToy extends RecyclerView.Adapter<ListAdptMainRankingToy.ViewHolder> {
    private final ArrayList<ListItemMainRankingToy> mData;

    public ListAdptMainRankingToy(ArrayList<ListItemMainRankingToy> list) {
        mData = list;
    }

    @NonNull
    @NotNull
    @Override
    public ListAdptMainRankingToy.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_mrankingtoy, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        ListItemMainRankingToy item = mData.get(position);

        holder.productImg.setImageDrawable(item.getImgDrawable());
        holder.productName.setText(item.getProductName());
        holder.ranking.setText(item.getRanking());
        holder.ratingNumber.setText(item.getRatingNumber());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImg;
        TextView ranking;
        TextView ratingNumber;
        TextView productName;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            productImg = itemView.findViewById(R.id.iVwRankProductImg);
            productImg.setOnClickListener(onClickListener);
            ranking = itemView.findViewById(R.id.tVwRankofProduct);
            ratingNumber = itemView.findViewById(R.id.tVwRatingPoint);
            productName = itemView.findViewById(R.id.tVwRankingListItemName);
            productName.setOnClickListener(onClickListener);
        }

        @SuppressLint("NonConstantResourceId")
        View.OnClickListener onClickListener = v -> {
            switch (v.getId()) {
                case R.id.iVwRankProductImg:
                    Toast.makeText(v.getContext(), v.getContext().getString(R.string.txt_test_message), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.tVwRankingListItemName:
                    Toast.makeText(v.getContext(), v.getContext().getString(R.string.txt_test_message2), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        };
    }


}
