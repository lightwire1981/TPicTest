package com.wellstech.tpictest.list_code;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wellstech.tpictest.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListAdapterCustomToy extends RecyclerView.Adapter<ListAdapterCustomToy.ViewHolder> {
    private final ArrayList<ListItemCustomToy> mData;

    public ListAdapterCustomToy(ArrayList<ListItemCustomToy> list) {
        mData = list;
    }

    @NonNull
    @NotNull
    @Override
    public ListAdapterCustomToy.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_customtoy, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ListAdapterCustomToy.ViewHolder holder, int position) {
        ListItemCustomToy item = mData.get(position);

        holder.productImg.setImageDrawable(item.getImgDrawable());
        holder.productName.setText(item.getProductName());
        holder.predictNumber.setText(item.getPredictNumber());
//        if (item.isFavorite()) {
//
//        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImg;
        ImageView favorite;
        TextView predictNumber;
        TextView productName;

        ViewHolder(View itemView) {
            super(itemView);
            productImg = itemView.findViewById(R.id.iVwCustomListItem);
            productImg.setOnClickListener(onClickListener);
            favorite = itemView.findViewById(R.id.iVwCustomFavorite);
            favorite.setOnClickListener(onClickListener);
            predictNumber = itemView.findViewById(R.id.tVwCustomPredictNum);
            productName = itemView.findViewById(R.id.tVwCustomListItem);
        }

        @SuppressLint("NonConstantResourceId")
        View.OnClickListener onClickListener = v ->  {
            switch (v.getId()) {
                case R.id.iVwCustomFavorite:
                    Toast.makeText(v.getContext(), v.getContext().getString(R.string.txt_test_message), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.iVwCustomListItem:
                    Toast.makeText(v.getContext(), v.getContext().getString(R.string.txt_test_message2), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        };
    }
}
